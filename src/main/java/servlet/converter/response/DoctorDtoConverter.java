package servlet.converter.response;

import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.doctor.DoctorDto;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class DoctorDtoConverter implements ResponseConverter<DoctorDto, String>{
    private final ObjectMapper objectMapper;

    @Override
    public String convert(DoctorDto doctorDto) throws IOException {
        return objectMapper.writeValueAsString(doctorDto);
    }
}
