package servlet.performer.patient;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.patient.PatientRegistrationService;
import servlet.performer.Performer;
import util.HttpMethod;
import util.UrlPath;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

@RequiredArgsConstructor
public class PatientRegistrationPerformer implements Performer {
    private static final String path = UrlPath.PATIENT_REGISTRATION;
    private static final Set<String> performableMethods = Set.of(HttpMethod.POST);

    private final PatientRegistrationService service;

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
