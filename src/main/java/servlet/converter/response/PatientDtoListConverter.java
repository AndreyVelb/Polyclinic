package servlet.converter.response;

import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.patient.PatientDto;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class PatientDtoListConverter implements ResponseConverter<List<PatientDto>, String>{
    private final ObjectMapper objectMapper;

    @Override
    public String convert(List<PatientDto> dtoPatientsList) throws IOException {
        return objectMapper.writeValueAsString(dtoPatientsList);
    }
}
