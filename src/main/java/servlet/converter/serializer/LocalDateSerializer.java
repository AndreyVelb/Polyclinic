package servlet.converter.serializer;

import org.codehaus.jackson.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate value, JsonGenerator generator, org.codehaus.jackson.map.SerializerProvider provider) throws IOException, JsonProcessingException {
        generator.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
