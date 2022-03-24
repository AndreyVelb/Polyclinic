package servlet.converter.response;

import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.admin.DocAppForAdminDto;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class DocAppDtoListForAdminConverter implements ResponseConverter<List<DocAppForAdminDto>, String>{
    private final ObjectMapper objectMapper;

    @Override
    public String convert(List<DocAppForAdminDto> docAppForAdminList) throws IOException {
        return objectMapper.writeValueAsString(docAppForAdminList);
    }
}
