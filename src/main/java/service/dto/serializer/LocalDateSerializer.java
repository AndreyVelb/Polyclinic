package service.dto.serializer;

import lombok.SneakyThrows;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    @Override
    @SneakyThrows
    public void serialize(LocalDate value, JsonGenerator generator, org.codehaus.jackson.map.SerializerProvider provider) {
        generator.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
