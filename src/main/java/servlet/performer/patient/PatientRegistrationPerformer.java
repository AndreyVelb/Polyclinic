package servlet.performer.patient;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.dto.patient.PatientDto;
import service.patient.PatientRegistrationService;
import servlet.performer.Performer;
import servlet.response.PatientRegistrationResponse;
import util.HttpMethod;
import util.UrlPath;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;

@RequiredArgsConstructor
public class PatientRegistrationPerformer implements Performer {
    private static final String path = UrlPath.PATIENT_REGISTRATION;
    private static final Set<String> performableMethods = Set.of(HttpMethod.POST);

    private final PatientRegistrationService service;

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        PatientDto patientDto = service.registration(writer, request, response);
        new PatientRegistrationResponse().send(response.getWriter(), response, patientDto, SC_CREATED);
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
