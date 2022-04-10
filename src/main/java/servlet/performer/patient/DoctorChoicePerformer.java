package servlet.performer.patient;

import exception.DtoValidationException;
import exception.MethodNotAllowedException;
import exception.NotAuthenticatedException;
import exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.doctor.DoctorDto;
import service.patient.DoctorChoiceService;
import servlet.performer.Performer;
import servlet.response.ExceptionResponse;
import servlet.response.JsonResponse;
import util.HttpMethod;
import util.UrlPath;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 *      /patient/{id}/doctors
 */

@RequiredArgsConstructor
public class DoctorChoicePerformer implements Performer {
    private static final String path = UrlPath.PATIENT_PATH;
    private static final String doctorsSubPath = UrlPath.PATIENT_SUBPATH_DOCTORS;
    private static final Set<String> performableMethods = Set.of(HttpMethod.GET);

    private final DoctorChoiceService service;

    private final ObjectMapper objectMapper;


    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals(HttpMethod.GET)){
            performGET(writer, request, response);
        }else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performGET(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        try {
            ArrayList<DoctorDto> doctorDtoList = service.getAllDoctors();
            String json = objectMapper.writeValueAsString(doctorDtoList);
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
            if (method.equals(request.getMethod())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAppropriatePath(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        if(requestPath.startsWith(path)){
            String[] requestPathParts = request.getPathInfo().split("/");
            return requestPathParts.length == 4
                    && requestPathParts[2].matches("[1-90]+")
                    && requestPathParts[3].matches(doctorsSubPath);  // 0-""/ 1-"patient"/ 2-{id}/ 3-"doctors"
        }else return false;
    }
}
