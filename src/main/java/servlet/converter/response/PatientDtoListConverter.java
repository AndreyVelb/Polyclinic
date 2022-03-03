package servlet.converter.response;

import org.codehaus.jackson.map.ObjectMapper;
import service.dto.patient.PatientDto;

import java.io.IOException;
import java.util.List;

public class PatientDtoListConverter implements ResponseConverter<List<PatientDto>, String>{
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(List<PatientDto> dtoPatientsList) throws IOException {
        return objectMapper.writeValueAsString(dtoPatientsList);
    }
}
