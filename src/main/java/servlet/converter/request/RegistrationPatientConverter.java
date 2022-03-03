package servlet.converter.request;

import service.dto.patient.PatientRegistrationDto;
import jakarta.servlet.http.HttpServletRequest;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class RegistrationPatientConverter implements RequestConverter<HttpServletRequest, PatientRegistrationDto> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final StringBuffer stringBuffer = new StringBuffer();

    @Override
    public PatientRegistrationDto convert(HttpServletRequest request) throws IOException {
        String line = null;

        try (var reader = request.getReader()){
            while ((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }
        }
        return objectMapper.readValue(stringBuffer.toString(), PatientRegistrationDto.class);
    }
}


