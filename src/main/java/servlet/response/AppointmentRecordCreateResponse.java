package servlet.response;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class AppointmentRecordCreateResponse {

    public void send(PrintWriter writer, HttpServletResponse response, Long patientId, Long appRecordId, int code) throws IOException {
        createResponse(writer, response, patientId, appRecordId, code);
    }

    private void createResponse (PrintWriter writer, HttpServletResponse response, Long patientId, Long appRecordId, int code) {
        response.resetBuffer();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(code);
        response.setHeader("Location", "/doctor/patients/" + patientId + "/records/" + appRecordId);
        writer.flush();
    }
}
