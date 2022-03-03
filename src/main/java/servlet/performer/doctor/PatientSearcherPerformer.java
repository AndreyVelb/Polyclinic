package servlet.performer.doctor;

import exception.MethodNotAllowedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import service.doctor.PatientSearcherService;
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

public class PatientSearcherPerformer implements Performer {
    private static final String path = UrlPath.DOCTOR_SEARCH_PATIENT;
    private static final ArrayList<String> performableMethods = new ArrayList<>();

    private final PatientSearcherService service = new PatientSearcherService();

    private final PatientDtoListConverter patientListConverter = new PatientDtoListConverter();

    public PatientSearcherPerformer() {
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
        return path.equals(requestPath);
    }
}
