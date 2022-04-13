package servlet.performer.patient;

import exception.AlreadyBookedException;
import exception.DtoValidationException;
import exception.MethodNotAllowedException;
import exception.NotAuthenticatedException;
import exception.PageNotFoundException;
import exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.patient.DocAppForPatientDto;
import service.patient.BookingDoctorsAppointmentService;
import servlet.performer.Performer;
import servlet.response.ExceptionResponse;
import servlet.response.JsonResponse;
import util.ExceptionMessage;
import util.HttpMethod;
import util.UrlPath;

import javax.persistence.OptimisticLockException;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_CONFLICT;
import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * /patient/{id}/doctors/{id}/appointments/{id}
 */

@RequiredArgsConstructor
public class BookingDoctorsAppointmentPerformer implements Performer {
    private static final String path = UrlPath.PATIENT_PATH;
    private static final String doctorsSubPath = UrlPath.PATIENT_SUBPATH_DOCTORS;
    private static final String appointmentsSubPath = UrlPath.PATIENT_SUBPATH_DOCTORS_APPOINTMENTS;
    private static final Set<String> performableMethods = Set.of(HttpMethod.GET, HttpMethod.PUT);

    private final BookingDoctorsAppointmentService service;

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals(HttpMethod.GET)) {
            performGET(writer, request, response);
        }
        if (request.getMethod().equals(HttpMethod.PUT)) {
            performPUT(writer, request, response);
        } else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performGET(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        try {
            DocAppForPatientDto dto = service.getDoctorsAppointment(request);
            String docAppDtoAsJson = objectMapper.writeValueAsString(dto);
            new JsonResponse().send(writer, response, docAppDtoAsJson, SC_OK);
        } catch (UserAlreadyExistsException
                | DtoValidationException
                | PageNotFoundException exception) {
            new ExceptionResponse().send(response.getWriter(), response, exception, SC_BAD_REQUEST);
        } catch (NotAuthenticatedException exception) {
            new ExceptionResponse().send(response.getWriter(), response, exception, SC_UNAUTHORIZED);
        }
    }

    @SneakyThrows
    private void performPUT(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        try {
            service.bookDoctorsAppointment(request);
        } catch (OptimisticLockException exception) {
            new ExceptionResponse().send(response.getWriter(), response, new AlreadyBookedException(ExceptionMessage.ALREADY_BOOKED), SC_CONFLICT);
        } catch (UserAlreadyExistsException
                | ConstraintViolationException
                | DtoValidationException exception) {
            new ExceptionResponse().send(response.getWriter(), response, exception, SC_BAD_REQUEST);
        } catch (NotAuthenticatedException exception) {
            new ExceptionResponse().send(response.getWriter(), response, exception, SC_UNAUTHORIZED);
        } catch (AlreadyBookedException exception) {
            new ExceptionResponse().send(response.getWriter(), response, exception, SC_CONFLICT);
        }
        String json = objectMapper.writeValueAsString("Вы успешно забронировали это время приема");
        new JsonResponse().send(writer, response, json, SC_CREATED);
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
        if (requestPath.startsWith(path)) {
            String[] requestPathParts = request.getPathInfo().split("/");
            return requestPathParts.length == 7
                    && requestPathParts[2].matches("[1-90]+")
                    && requestPathParts[3].matches(doctorsSubPath)
                    && requestPathParts[4].matches("[1-90]+")
                    && requestPathParts[5].matches(appointmentsSubPath)
                    && requestPathParts[6].matches("[1-90]+");      // 0-""/ 1-"patient"/ 2-{id}/ 3-"doctors"/ 4-{id}/ 5-"appointments"/ 6-{id}
        } else return false;
    }
}
