package servlet.performer.doctor;

import exception.UserAlreadyExistsException;
import exception.MethodNotAllowedException;
import exception.NotAuthenticatedException;
import exception.ServerTechnicalProblemsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.doctor.MedicCardService;
import service.dto.patient.PatientDto;
import servlet.converter.response.PatientDtoConverter;
import servlet.performer.Performer;
import servlet.response.JsonResponse;
import util.HttpMethod;
import util.UrlPath;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

/**
 *      /doctor/{id}/patients/{id}
 */

@RequiredArgsConstructor
public class MedicCardPerformer implements Performer {
    private static final String path = UrlPath.DOCTOR_PATH;
    private static final String patientsSubPath = UrlPath.DOCTOR_SUBPATH_PATIENTS;
    private static final Set<String> performableMethods = Set.of(HttpMethod.GET);

    private final MedicCardService service;

    private final PatientDtoConverter patientDtoConverter;

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) throws IOException, UserAlreadyExistsException, ServerTechnicalProblemsException, NotAuthenticatedException {
        if (request.getMethod().equals(HttpMethod.GET)){
            performGET(writer, request, response);
        }else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performGET(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        String[] requestPathParts = request.getPathInfo().split("/");
        Long patientId =  Long.parseLong(String.valueOf(requestPathParts[4]));
        PatientDto patientDto = service.getPatientMedicCard(patientId);
        String json = patientDtoConverter.convert(patientDto);
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
            return requestPathParts.length == 5
                    && requestPathParts[2].matches("[1-90]+")
                    && requestPathParts[3].matches(patientsSubPath)
                    && requestPathParts[4].matches("[1-90]+");  // 0-""/ 1-"doctor"/ 2-{id}/ 3-"patients"/ 4-{id}
        }else return false;
    }
}
