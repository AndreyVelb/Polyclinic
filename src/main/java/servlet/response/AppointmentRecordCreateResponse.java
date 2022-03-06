package servlet.response;

import jakarta.servlet.http.HttpServletResponse;
import service.dto.doctor.AppointmentRecordDto;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class AppointmentRecordCreateResponse implements ResponseInterface<AppointmentRecordDto>{

    @Override
    public void send(PrintWriter writer, HttpServletResponse response, AppointmentRecordDto appointmentRecord, int code) throws IOException {
        createResponse(writer, response, appointmentRecord, code);
    }

    private void createResponse (PrintWriter writer, HttpServletResponse response, AppointmentRecordDto appointmentRecordDto, int code) {
        response.resetBuffer();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(code);
        response.setHeader("Location", "/doctor/patients/" + appointmentRecordDto.getPatientDto().getId() + "/records/" + appointmentRecordDto.getId());
        writer.flush();
    }
}
