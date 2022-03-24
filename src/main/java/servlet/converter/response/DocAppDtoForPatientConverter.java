package servlet.converter.response;

import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.admin.DocAppForAdminDto;
import service.dto.patient.DocAppForPatientDto;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class DocAppDtoForPatientConverter implements ResponseConverter<DocAppForPatientDto, String>{
    private final ObjectMapper objectMapper;

    @Override
    public String convert(DocAppForPatientDto docAppDto) throws IOException {
        return objectMapper.writeValueAsString(docAppDto);
    }
}
