package servlet.converter.response;

import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.doctor.DoctorDto;
import service.dto.patient.PatientDto;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class DoctorDtoListConverter implements ResponseConverter<List<DoctorDto>, String>{
    private final ObjectMapper objectMapper;

    @Override
    public String convert(List<DoctorDto> doctorDtoList) throws IOException {
        return objectMapper.writeValueAsString(doctorDtoList);
    }
}
