package servlet.converter.request;

import org.codehaus.jackson.map.ObjectMapper;
import service.dto.doctor.PatientLastNameDto;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class PatientLastNameConverter implements RequestConverter<HttpServletRequest, PatientLastNameDto>{
    ObjectMapper objectMapper = new ObjectMapper();
    StringBuffer stringBuffer = new StringBuffer();

    @Override
    public PatientLastNameDto convert(HttpServletRequest jsonRequest) throws IOException {
        String line = null;

        try (var reader = jsonRequest.getReader()){
            while ((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }
        }
        return objectMapper.readValue(stringBuffer.toString(), PatientLastNameDto.class);
    }
}
