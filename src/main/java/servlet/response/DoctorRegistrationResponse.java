package servlet.response;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class DoctorRegistrationResponse {

    public void send(PrintWriter writer, HttpServletResponse response, Long doctorId, int code) throws IOException {
        createResponse(writer, response, doctorId, code);
    }

    private void createResponse (PrintWriter writer, HttpServletResponse response, Long doctorId, int code) {
        response.resetBuffer();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(code);
        response.setHeader("Location", "doctor/" + doctorId);
        writer.flush();
    }
}
