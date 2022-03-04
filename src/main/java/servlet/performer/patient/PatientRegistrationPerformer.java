package servlet.performer.patient;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import service.patient.PatientRegistrationService;
import servlet.performer.Performer;
import service.mapper.util.HttpMethod;
import service.mapper.util.UrlPath;

import java.io.PrintWriter;
import java.util.ArrayList;

public class PatientRegistrationPerformer implements Performer {
    private static final String path = UrlPath.PATIENT_REGISTRATION;
    private static final ArrayList<String> performableMethods = new ArrayList<>();

    private PatientRegistrationService service = new PatientRegistrationService();

    public PatientRegistrationPerformer(){
        performableMethods.add(HttpMethod.POST);
    }

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        service.registration(writer, request, response);
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
