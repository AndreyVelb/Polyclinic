package servlet.performer.patient;

import exception.MethodNotAllowedException;
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

/**
 *      /join
 */

@RequiredArgsConstructor
public class PatientRegistrationPerformer implements Performer {
    private static final String path = UrlPath.PATIENT_REGISTRATION;
    private static final Set<String> performableMethods = Set.of(HttpMethod.POST);

    private final PatientRegistrationService service;

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals(HttpMethod.POST)){
            performPOST(writer, request, response);
        }else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performPOST(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        PatientDto patientDto = service.registration(request);
        new PatientRegistrationResponse().send(writer, response, patientDto, SC_CREATED);
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
