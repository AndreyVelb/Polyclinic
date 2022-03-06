package servlet.converter.response;

import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.doctor.AppointmentRecordDto;

import java.io.IOException;

@RequiredArgsConstructor
public class AppointmentRecordConverter implements ResponseConverter<AppointmentRecordDto, String>{
    private final ObjectMapper objectMapper;

    @Override
    public String convert(AppointmentRecordDto appRecordDto) throws IOException {
        return objectMapper.writeValueAsString(appRecordDto);
    }
}
