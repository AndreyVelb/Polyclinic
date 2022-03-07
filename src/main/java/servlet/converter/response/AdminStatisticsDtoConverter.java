package servlet.converter.response;

import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import service.dto.admin.AdminStatisticsDto;

import java.io.IOException;

@RequiredArgsConstructor
public class AdminStatisticsDtoConverter implements ResponseConverter<AdminStatisticsDto, String>{
    private final ObjectMapper objectMapper;

    @Override
    public String convert(AdminStatisticsDto adminStatisticsDto) throws IOException {
        return objectMapper.writeValueAsString(adminStatisticsDto);
    }
}
