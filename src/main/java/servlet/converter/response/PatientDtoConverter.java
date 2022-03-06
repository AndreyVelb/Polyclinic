package servlet.converter.response;

import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.patient.PatientDto;

import java.io.IOException;

@RequiredArgsConstructor
public class PatientDtoConverter implements ResponseConverter<PatientDto, String>{
    private final ObjectMapper objectMapper;

    @Override
    public String convert(PatientDto patientDto) throws IOException {
        return objectMapper.writeValueAsString(patientDto);
    }
}
