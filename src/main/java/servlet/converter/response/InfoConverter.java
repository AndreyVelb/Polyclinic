package servlet.converter.response;

import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.patient.DocAppForPatientDto;

import java.io.IOException;

@RequiredArgsConstructor
public class InfoConverter implements ResponseConverter<String, String>{
    private final ObjectMapper objectMapper;

    @Override
    public String convert(String info) throws IOException {
        return objectMapper.writeValueAsString(info);
    }
}
