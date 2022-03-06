package servlet.converter.request;

import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.doctor.PatientLastNameDto;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class PatientLastNameConverter implements RequestConverter<HttpServletRequest, PatientLastNameDto>{
    private final ObjectMapper objectMapper;


    @Override
    public PatientLastNameDto convert(HttpServletRequest jsonRequest) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        try (var reader = jsonRequest.getReader()){
            while ((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }
        }
        return objectMapper.readValue(stringBuffer.toString(), PatientLastNameDto.class);
    }
}
