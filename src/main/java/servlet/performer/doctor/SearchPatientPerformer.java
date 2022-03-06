package servlet.performer.doctor;

import exception.MethodNotAllowedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

public class SearchPatientPerformer implements Performer {
    private static final String path = UrlPath.DOCTOR_PATH_WITHOUT_INFO;
    private static final ArrayList<String> performableMethods = new ArrayList<>();

    private final SearchPatientService service = new SearchPatientService();

    private final PatientDtoListConverter patientListConverter = new PatientDtoListConverter();

    public SearchPatientPerformer() {
        performableMethods.add(HttpMethod.GET);
    }

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
            if(request.getPathInfo().split("/").length == 3){    // 0-""/ 1-"doctor"/ 2-"patients"
                return true;
            }else return false;
        }else return false;
    }
}
