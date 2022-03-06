package servlet.converter.request;

import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.doctor.AppointmentRecordRequestDto;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class AppointmentRecordRequestConverter implements RequestConverter<HttpServletRequest, AppointmentRecordRequestDto>{
    private final ObjectMapper objectMapper;

    @Override
    public AppointmentRecordRequestDto convert(HttpServletRequest jsonRequest) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        try (var reader = jsonRequest.getReader()){
            while ((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }
        }
        return objectMapper.readValue(stringBuffer.toString(), AppointmentRecordRequestDto.class);
    }
}
