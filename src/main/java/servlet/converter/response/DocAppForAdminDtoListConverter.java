package servlet.converter.response;

import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.admin.DoctorsAppointmentForAdminDto;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class DocAppForAdminDtoListConverter implements ResponseConverter<List<DoctorsAppointmentForAdminDto>, String>{
    private final ObjectMapper objectMapper;

    @Override
    public String convert(List<DoctorsAppointmentForAdminDto> docAppForAdminList) throws IOException {
        return objectMapper.writeValueAsString(docAppForAdminList);
    }
}
