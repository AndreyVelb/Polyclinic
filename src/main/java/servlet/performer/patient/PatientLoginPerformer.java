package servlet.performer.patient;

import exception.NotAuthenticatedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.patient.PatientLoginService;
import service.dto.patient.PatientDto;
import servlet.performer.Performer;
import util.HttpMethod;
import util.UrlPath;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class PatientLoginPerformer implements Performer {
    private static final String path = UrlPath.PATIENT_LOGIN;
    private static final Set<String> performableMethods = Set.of(HttpMethod.POST);

    private final PatientLoginService service;

    @Override
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getMethod().equals(HttpMethod.POST)){
            performPOST(writer, request, response);
        }
    }

    @SneakyThrows
    private void performPOST(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        Optional<PatientDto> mayBePatientDto = service.authenticate(request);
        if(mayBePatientDto.isPresent()){
            var session = request.getSession();
            PatientDto sessionPatientDto = (PatientDto) session.getAttribute("PATIENT");
            if (sessionPatientDto == null){
                PatientDto patientDto = mayBePatientDto.get();
                request.getSession().setAttribute("PATIENT", patientDto);
            }
            response.sendRedirect(UrlPath.PATIENT_CHOOSE_DOCTOR);
        }else {
            throw new NotAuthenticatedException();
        }
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
