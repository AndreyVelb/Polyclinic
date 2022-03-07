package servlet.performer.doctor;

import exception.MethodNotAllowedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.doctor.SearchPatientService;
import servlet.converter.response.PatientDtoListConverter;
import servlet.performer.Performer;
import servlet.response.JsonResponse;
import service.dto.patient.PatientDto;
import util.HttpMethod;
import util.UrlPath;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

/**
 *      /doctor/{id}/patients
 */

@RequiredArgsConstructor
public class SearchPatientPerformer implements Performer {
    private static final String path = UrlPath.DOCTOR_PATH;
    private static final String patientsSubPath = UrlPath.DOCTOR_SUBPATH_PATIENTS;
    private static final Set<String> performableMethods = Set.of(HttpMethod.GET);

    private final SearchPatientService service;

    private final PatientDtoListConverter patientListConverter;

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals(HttpMethod.GET)){
            performGET(writer, request, response);
        }else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performGET(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        List<PatientDto> dtoPatientsList = service.findPatientsByLastName(request);
        String json = patientListConverter.convert(dtoPatientsList);
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.send(writer, response, json, SC_OK);
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
            if(requestPathParts.length == 4
                    && requestPathParts[2].matches("[1-90]+")
                    && requestPathParts[3].matches(patientsSubPath)){  // 0-""/ 1-"doctor"/ 2-"{some id}"/ 3-"patients"
                return true;
            }else return false;
        }else return false;
    }
}
