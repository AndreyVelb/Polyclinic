package servlet.performer.admin;

import exception.DtoValidationException;
import exception.MethodNotAllowedException;
import exception.NotAuthenticatedException;
import exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.codehaus.jackson.map.ObjectMapper;
import repository.DoctorRepository;
import repository.PatientRepository;
import service.admin.AdminHomePageService;
import service.dto.admin.AdminStatisticsDto;
import servlet.performer.Performer;
import servlet.response.ExceptionResponse;
import servlet.response.JsonResponse;
import util.HttpMethod;
import util.UrlPath;

import java.io.PrintWriter;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 *      /admin/{id}
 */

@RequiredArgsConstructor
public class AdminHomePagePerformer implements Performer {
    private static final String path = UrlPath.ADMIN_PATH;
    private static final Set<String> performableMethods = Set.of(HttpMethod.GET);

    private final AdminHomePageService service;

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    private final ObjectMapper objectMapper;


    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals(HttpMethod.GET)){
            performGET(writer, response);
        } else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performGET(PrintWriter writer, HttpServletResponse response){
        try {
            int patientCount = service.getNumbersOfSubjects(patientRepository);
            int doctorCount = service.getNumbersOfSubjects(doctorRepository);
            AdminStatisticsDto adminStatisticsDto = AdminStatisticsDto.builder()
                    .patientCount(patientCount)
                    .doctorCount(doctorCount)
                    .build();
            String jsonStatistics = objectMapper.writeValueAsString(adminStatisticsDto);
            new JsonResponse().send(writer, response, jsonStatistics, SC_OK);
        }
        catch (UserAlreadyExistsException
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
            return requestPathParts.length == 3 && requestPathParts[2].matches("[1-90]+");      // 0-""/ 1-"admin"/ 2-"{some id}"
        }else return false;
    }
}
