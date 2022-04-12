package servlet.performer.doctor;

import entity.DoctorSpeciality;
import exception.AlreadyBookedException;
import exception.DtoValidationException;
import exception.MethodNotAllowedException;
import exception.NotAuthenticatedException;
import exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.doctor.DoctorLoginService;
import service.dto.doctor.DoctorDto;
import servlet.performer.Performer;
import servlet.response.ExceptionResponse;
import util.HttpMethod;
import util.SessionRole;
import util.UrlPath;

import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_CONFLICT;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * /doctor/login
 */

@RequiredArgsConstructor
public class DoctorLoginPerformer implements Performer {
    private static final String path = UrlPath.DOCTOR_LOGIN;
    private static final Set<String> performableMethods = Set.of(HttpMethod.POST);

    private final DoctorLoginService service;

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals(HttpMethod.POST)) {
            performPOST(request, response);
        } else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performPOST(HttpServletRequest request, HttpServletResponse response) {
        try {
            DoctorDto doctorDto = service.authenticate(request);
            if (doctorDto.getSpeciality() == DoctorSpeciality.CHIEF_DOCTOR) {
                adminLogin(request, response, doctorDto);
                return;
            }
            doctorLogin(request, response, doctorDto);
        } catch (UserAlreadyExistsException
                | ConstraintViolationException
                | DtoValidationException exception) {
            new ExceptionResponse().send(response.getWriter(), response, exception, SC_BAD_REQUEST);
        } catch (NotAuthenticatedException exception) {
            new ExceptionResponse().send(response.getWriter(), response, exception, SC_UNAUTHORIZED);
        } catch (AlreadyBookedException exception) {
            new ExceptionResponse().send(response.getWriter(), response, exception, SC_CONFLICT);
        }

    }

    @SneakyThrows
    private void adminLogin(HttpServletRequest request, HttpServletResponse response, DoctorDto doctorDto) {
        var session = request.getSession();
        DoctorDto sessionDoctorDto = (DoctorDto) session.getAttribute(SessionRole.ADMIN);
        if (sessionDoctorDto == null) {
            session.setAttribute(SessionRole.ADMIN, doctorDto);
        }
        response.sendRedirect(UrlPath.ADMIN_PATH + "/" + doctorDto.getId());
    }

    @SneakyThrows
    private void doctorLogin(HttpServletRequest request, HttpServletResponse response, DoctorDto doctorDto) {
        var session = request.getSession();
        DoctorDto sessionDoctorDto = (DoctorDto) session.getAttribute(SessionRole.DOCTOR);
        if (sessionDoctorDto == null) {
            session.setAttribute(SessionRole.DOCTOR, doctorDto);
        }
        response.sendRedirect(UrlPath.DOCTOR_PATH + "/" + doctorDto.getId() + "/" + UrlPath.DOCTOR_SUBPATH_PATIENTS);
    }

    @Override
    public boolean isMethodCanBePerformed(HttpServletRequest request) {
        for (String method : performableMethods) {
            if (method.equals(request.getMethod())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAppropriatePath(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        return path.equals(requestPath);
    }
}
