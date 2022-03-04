package servlet.performer.doctor;

import exception.AlreadyExistsException;
import exception.MethodNotAllowedException;
import exception.NotAuthenticatedException;
import exception.ServerTechnicalProblemsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import service.doctor.PatientPageService;
import service.dto.patient.PatientDto;
import servlet.converter.response.PatientDtoConverter;
import servlet.performer.Performer;
import servlet.response.JsonResponse;
import service.mapper.util.HttpMethod;
import service.mapper.util.UrlPath;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

public class PatientPagePerformer implements Performer {
    private static final String path = UrlPath.DOCTOR_CHOOSE_PATIENT;
    private static final ArrayList<String> performableMethods = new ArrayList<>();

    private static final PatientPageService service = new PatientPageService();

    private static final PatientDtoConverter patientDtoConverter = new PatientDtoConverter();

    public PatientPagePerformer() {
        performableMethods.add(HttpMethod.GET);
    }

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
        Long patientId =  Long.parseLong(String.valueOf(requestPathParts[1]));
        PatientDto patientDto = service.getPatientPage(patientId);
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
        if(path.equals(requestPath)){
            String[] requestPathParts = request.getPathInfo().split("/");
            if(requestPathParts.length == 2 && requestPathParts[1].matches("[1-90]+")){
                return true;
            }else return false;
        }else return false;
    }
}
