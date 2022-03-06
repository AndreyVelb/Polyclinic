package servlet.converter.response;

import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.doctor.AppointmentRecordDto;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class AppointmentRecordDtoListConverter implements ResponseConverter<List<AppointmentRecordDto>, String>{
    private final ObjectMapper objectMapper;

    @Override
    public String convert(List<AppointmentRecordDto> dtoRecordsList) throws IOException {
        return objectMapper.writeValueAsString(dtoRecordsList);
    }
}
