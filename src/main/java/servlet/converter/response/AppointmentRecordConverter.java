package servlet.converter.response;

import org.codehaus.jackson.map.ObjectMapper;
import service.dto.doctor.AppointmentRecordDto;

import java.io.IOException;

public class AppointmentRecordConverter implements ResponseConverter<AppointmentRecordDto, String>{
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(AppointmentRecordDto appRecordDto) throws IOException {
        return objectMapper.writeValueAsString(appRecordDto);
    }
}
