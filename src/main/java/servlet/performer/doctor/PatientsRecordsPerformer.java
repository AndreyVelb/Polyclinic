package servlet.performer.doctor;

import exception.DtoValidationException;
import exception.MethodNotAllowedException;
import exception.NotAuthenticatedException;
import exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.codehaus.jackson.map.ObjectMapper;
import service.doctor.PatientsRecordsService;
import service.dto.doctor.AppointmentRecordDto;
import servlet.performer.Performer;
import servlet.response.ExceptionResponse;
import servlet.response.JsonResponse;
import util.HttpMethod;
import util.UrlPath;

import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * /doctor/{id}/patients/{id}/records
 */

@RequiredArgsConstructor
public class PatientsRecordsPerformer implements Performer {
    private static final String path = UrlPath.DOCTOR_PATH;
    private static final String patientsSubPath = UrlPath.DOCTOR_SUBPATH_PATIENTS;
    private static final String recordsSubPath = UrlPath.DOCTOR_SUBPATH_ALL_PATIENTS_RECORDS;
    private static final Set<String> performableMethods = Set.of(HttpMethod.GET);

    private final PatientsRecordsService service;

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals(HttpMethod.GET)) {
            performGET(writer, request, response);
        } else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performGET(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<AppointmentRecordDto> patientsRecordsDtoList = service.getPatientsRecordsDto(request);
            String json = objectMapper.writeValueAsString(patientsRecordsDtoList);
            new JsonResponse().send(writer, response, json, SC_OK);
        } catch (UserAlreadyExistsException
                | DtoValidationException exception) {
            new ExceptionResponse().send(response.getWriter(), response, exception, SC_BAD_REQUEST);
        } catch (NotAuthenticatedException exception) {
            new ExceptionResponse().send(response.getWriter(), response, exception, SC_UNAUTHORIZED);
        }
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
            return requestPathParts.length == 6
                    && requestPathParts[2].matches("[1-90]+")
                    && requestPathParts[3].matches(patientsSubPath)
                    && requestPathParts[4].matches("[1-90]+")
                    && requestPathParts[5].matches(recordsSubPath); // 0-""/ 1-"doctor"/ 2-{id}/ 3-"patients"/ 4-{id}/ 5-"records"
        } else return false;
    }
}
