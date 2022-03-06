package servlet.performer.patient;

import exception.NotAuthenticatedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

public class PatientLoginPerformer implements Performer {
    private static final String path = UrlPath.PATIENT_LOGIN;
    private static final ArrayList<String> performableMethods = new ArrayList<>();

    private final PatientLoginService service = new PatientLoginService();

    public PatientLoginPerformer(){
        performableMethods.add(HttpMethod.POST);
    }

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
