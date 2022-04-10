package service.dto.deserializer;

import lombok.SneakyThrows;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

import java.time.LocalDate;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

    protected LocalDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    @SneakyThrows
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt) {
        return LocalDate.parse(jp.readValueAs(String.class));
    }
}
