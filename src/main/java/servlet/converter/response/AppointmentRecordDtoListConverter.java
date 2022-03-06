package servlet.converter.response;

import org.codehaus.jackson.map.ObjectMapper;
import service.dto.doctor.AppointmentRecordDto;

import java.io.IOException;
import java.util.List;

public class AppointmentRecordDtoListConverter implements ResponseConverter<List<AppointmentRecordDto>, String>{
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(List<AppointmentRecordDto> dtoRecordsList) throws IOException {
        return objectMapper.writeValueAsString(dtoRecordsList);
    }
}
