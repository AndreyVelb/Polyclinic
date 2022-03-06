package servlet.performer.doctor;

import exception.AlreadyExistsException;
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
import java.util.ArrayList;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

@RequiredArgsConstructor
public class MedicCardPerformer implements Performer {
    private static final String path = UrlPath.DOCTOR_PATH_WITHOUT_INFO;
    private static final Set<String> performableMethods = Set.of(HttpMethod.GET);

    private final MedicCardService service;

    private final PatientDtoConverter patientDtoConverter;

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) throws IOException, AlreadyExistsException, ServerTechnicalProblemsException, NotAuthenticatedException {
        if (request.getMethod().equals(HttpMethod.GET)){
            performGET(writer, request, response);
        }else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performGET(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        String[] requestPathParts = request.getPathInfo().split("/");
        Long patientId =  Long.parseLong(String.valueOf(requestPathParts[3]));
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
            if(requestPathParts.length == 4 && requestPathParts[3].matches("[1-90]+")){  // 0-""/ 1-"doctor"/ 2-"patients"/ 3-"{some id}"
                return true;
            }else return false;
        }else return false;
    }
}
