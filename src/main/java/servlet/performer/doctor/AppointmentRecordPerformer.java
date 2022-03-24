package servlet.performer.doctor;

import exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.doctor.AppointmentRecordService;
import service.dto.doctor.AppointmentRecordDto;
import servlet.converter.response.AppointmentRecordConverter;
import servlet.performer.Performer;
import servlet.response.JsonResponse;
import util.HttpMethod;
import util.UrlPath;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

/**
 *      /doctor/{id}/patients/{id}/records/{id}
 */

@RequiredArgsConstructor
public class AppointmentRecordPerformer implements Performer {
    private static final String path = UrlPath.DOCTOR_PATH;
    private static final String patientsSubPath = UrlPath.DOCTOR_SUBPATH_PATIENTS;
    private static final String recordsSubPath = UrlPath.DOCTOR_SUBPATH_ALL_PATIENTS_RECORDS;
    private static final Set<String> performableMethods = Set.of(HttpMethod.GET);

    private final AppointmentRecordService service;
    private final AppointmentRecordConverter appointmentRecordConverter;

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) throws IOException, UserAlreadyExistsException, ServerTechnicalProblemsException, NotAuthenticatedException, PageNotFoundException {
        if (request.getMethod().equals(HttpMethod.GET)){
            performGET(writer, request, response);
        }else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performGET(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        AppointmentRecordDto appointmentRecordDto = service.getAppointmentRecordDto(request);
        String json = appointmentRecordConverter.convert(appointmentRecordDto);
        new JsonResponse().send(writer, response, json, SC_OK);
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

            return requestPathParts.length == 7
                    && requestPathParts[2].matches("[1-90]+")
                    && requestPathParts[3].matches(patientsSubPath)
                    && requestPathParts[4].matches("[1-90]+")
                    && requestPathParts[5].matches(recordsSubPath)
                    && requestPathParts[6].matches("[1-90]+");  // 0-""/ 1-"doctor"/ 2-{id}/ 3-"patients"/ 4-{id}/ 5-"records"/ 6-{id}
        }else return false;
    }
}
