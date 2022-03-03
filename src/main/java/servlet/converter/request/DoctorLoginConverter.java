package servlet.converter.request;

import org.codehaus.jackson.map.ObjectMapper;
import service.dto.doctor.DoctorLoginDto;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class DoctorLoginConverter implements RequestConverter<HttpServletRequest, DoctorLoginDto>{
    ObjectMapper objectMapper = new ObjectMapper();
    StringBuffer stringBuffer = new StringBuffer();

    @Override
    public DoctorLoginDto convert(HttpServletRequest jsonRequest) throws IOException {
        String line = null;

        try (var reader = jsonRequest.getReader()){
            while ((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }
        }
        return objectMapper.readValue(stringBuffer.toString(), DoctorLoginDto.class);
    }
}
