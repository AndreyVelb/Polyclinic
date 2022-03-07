package servlet.response;

import jakarta.servlet.http.HttpServletResponse;
import service.dto.patient.PatientDto;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class PatientRegistrationResponse implements ResponseInterface<PatientDto>{

    @Override
    public void send(PrintWriter writer, HttpServletResponse response, PatientDto patientDto, int code) {
        createResponse(writer, response, patientDto, code);
    }

    private void createResponse (PrintWriter writer, HttpServletResponse response, PatientDto patientDto, int code) {
        response.resetBuffer();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(code);
        response.setHeader("Location", "patient/login");
        writer.flush();
    }
}
