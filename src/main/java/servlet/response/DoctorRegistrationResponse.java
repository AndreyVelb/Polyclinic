package servlet.response;

import entity.Doctor;
import jakarta.servlet.http.HttpServletResponse;
import service.dto.doctor.DoctorDto;
import service.dto.patient.PatientDto;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class DoctorRegistrationResponse implements ResponseInterface<DoctorDto>{

    @Override
    public void send(PrintWriter writer, HttpServletResponse response, DoctorDto doctorDto, int code) throws IOException {
        createResponse(writer, response, doctorDto, code);
    }

    private void createResponse (PrintWriter writer, HttpServletResponse response, DoctorDto doctorDto, int code) {
        response.resetBuffer();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(code);
        response.setHeader("Location", "doctor/" + doctorDto.getId());
        writer.flush();
    }
}
