package servlet.converter.request;

import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.admin.DoctorRegistrationDto;
import service.dto.patient.PatientLoginDto;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class RegistrationDoctorConverter implements RequestConverter<HttpServletRequest, DoctorRegistrationDto>{
    private final ObjectMapper objectMapper;

    @Override
    public DoctorRegistrationDto convert(HttpServletRequest jsonRequest) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        String line;

        try (var reader = jsonRequest.getReader()){
            while ((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }
        }
        return objectMapper.readValue(stringBuffer.toString(), DoctorRegistrationDto.class);
    }
}
