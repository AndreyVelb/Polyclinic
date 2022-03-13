package servlet.performer.admin;

import exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import repository.DoctorRepository;
import repository.PatientRepository;
import service.admin.HomePageService;
import service.dto.admin.AdminStatisticsDto;
import servlet.converter.response.AdminStatisticsDtoConverter;
import servlet.performer.Performer;
import servlet.response.JsonResponse;
import util.HttpMethod;
import util.UrlPath;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;

/**
 *      /admin/{id}
 */

@RequiredArgsConstructor
public class HomePagePerformer implements Performer {
    private static final String path = UrlPath.ADMIN_PATH;
    private static final Set<String> performableMethods = Set.of(HttpMethod.GET);

    private final HomePageService service;

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    private final AdminStatisticsDtoConverter adminStatisticsDtoConverter;


    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) throws IOException, AlreadyExistsException, ServerTechnicalProblemsException, NotAuthenticatedException, PageNotFoundException {
        if (request.getMethod().equals(HttpMethod.GET)){
            performGET(writer, request, response);
        }else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performGET(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        int patientCount = service.getNumbersOfSubjects(patientRepository);
        int doctorCount = service.getNumbersOfSubjects(doctorRepository);
        AdminStatisticsDto adminStatisticsDto = AdminStatisticsDto.builder()
                .patientCount(patientCount)
                .doctorCount(doctorCount)
                .build();
        String jsonStatistics = adminStatisticsDtoConverter.convert(adminStatisticsDto);
        new JsonResponse().send(writer, response, jsonStatistics, SC_CREATED);
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
