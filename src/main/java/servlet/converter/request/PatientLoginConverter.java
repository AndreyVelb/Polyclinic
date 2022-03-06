package servlet.converter.request;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.patient.PatientLoginDto;

import java.io.IOException;

@RequiredArgsConstructor
public class PatientLoginConverter implements RequestConverter<HttpServletRequest, PatientLoginDto>{
    private final ObjectMapper objectMapper;


    @Override
    public PatientLoginDto convert(HttpServletRequest jsonRequest) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        try (var reader = jsonRequest.getReader()){
            while ((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }
        }
        return objectMapper.readValue(stringBuffer.toString(), PatientLoginDto.class);
    }
}
