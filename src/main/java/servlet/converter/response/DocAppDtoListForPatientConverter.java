package servlet.converter.response;

import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.admin.DocAppForAdminDto;
import service.dto.patient.DocAppForPatientDto;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class DocAppDtoListForPatientConverter implements ResponseConverter<List<DocAppForPatientDto>, String>{
    private final ObjectMapper objectMapper;

    @Override
    public String convert(List<DocAppForPatientDto> docAppForPatientList) throws IOException {
        return objectMapper.writeValueAsString(docAppForPatientList);
    }
}
