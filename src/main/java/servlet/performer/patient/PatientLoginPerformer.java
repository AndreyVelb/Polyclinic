package servlet.performer.patient;

import exception.AlreadyBookedException;
import exception.DtoValidationException;
import exception.NotAuthenticatedException;
import exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.dto.patient.PatientDto;
import service.patient.PatientLoginService;
import servlet.performer.Performer;
import servlet.response.ExceptionResponse;
import util.HttpMethod;
import util.UrlPath;

import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_CONFLICT;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 *      /patient/login
 */

@RequiredArgsConstructor
public class PatientLoginPerformer implements Performer {
    private static final String path = UrlPath.PATIENT_LOGIN;
    private static final Set<String> performableMethods = Set.of(HttpMethod.POST);

    private final PatientLoginService service;

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals(HttpMethod.POST)){
            performPOST(writer, request, response);
        }
    }

    @SneakyThrows
    private void performPOST(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        try {
            PatientDto patientDto = service.authenticate(request);
            var session = request.getSession();
            PatientDto sessionPatientDto = (PatientDto) session.getAttribute("PATIENT");
            if (sessionPatientDto == null){
                request.getSession().setAttribute("PATIENT", patientDto);
            }
            response.sendRedirect(buildPatientsPathForChooseDoctor(patientDto));
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

    @Override
    public boolean isMethodCanBePerformed(HttpServletRequest request) {
        for (String method : performableMethods) {
            if (method.equals(request.getMethod())){
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

    private String buildPatientsPathForChooseDoctor(PatientDto patientDto){
        return UrlPath.PATIENT_PATH + "/" + patientDto.getId() + "/" + UrlPath.PATIENT_SUBPATH_DOCTORS;
    }
}
