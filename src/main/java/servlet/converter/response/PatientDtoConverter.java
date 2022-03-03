package servlet.converter.response;

import org.codehaus.jackson.map.ObjectMapper;
import service.dto.patient.PatientDto;

import java.io.IOException;

public class PatientDtoConverter implements ResponseConverter<PatientDto, String>{
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(PatientDto patientDto) throws IOException {
        return objectMapper.writeValueAsString(patientDto);
    }
}
