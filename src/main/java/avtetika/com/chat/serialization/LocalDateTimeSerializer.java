package avtetika.com.chat.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static java.util.Objects.isNull;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (isNull(localDateTime)) {
            return;
        }
        OffsetDateTime timeUtc = localDateTime.atOffset(ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now()));
        gen.writeString(timeUtc.toString());
    }
}
