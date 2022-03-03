package servlet.converter.request;

import jakarta.servlet.http.HttpServletRequest;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.patient.PatientLoginDto;

import java.io.IOException;

public class PatientLoginConverter implements RequestConverter<HttpServletRequest, PatientLoginDto>{
    ObjectMapper objectMapper = new ObjectMapper();
    StringBuffer stringBuffer = new StringBuffer();

    @Override
    public PatientLoginDto convert(HttpServletRequest jsonRequest) throws IOException {
        String line = null;

        try (var reader = jsonRequest.getReader()){
            while ((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }
        }
        return objectMapper.readValue(stringBuffer.toString(), PatientLoginDto.class);
    }
}
