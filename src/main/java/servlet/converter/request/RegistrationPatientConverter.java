package servlet.converter.request;

import lombok.RequiredArgsConstructor;
import service.dto.patient.PatientRegistrationDto;
import jakarta.servlet.http.HttpServletRequest;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

@RequiredArgsConstructor
public class RegistrationPatientConverter implements RequestConverter<HttpServletRequest, PatientRegistrationDto> {
    private final ObjectMapper objectMapper;

    @Override
    public PatientRegistrationDto convert(HttpServletRequest request) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        try (var reader = request.getReader()){
            while ((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }
        }
        return objectMapper.readValue(stringBuffer.toString(), PatientRegistrationDto.class);
    }
}


