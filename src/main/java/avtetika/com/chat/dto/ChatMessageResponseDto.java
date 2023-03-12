package avtetika.com.chat.dto;

import avtetika.com.chat.serialization.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ChatMessageResponseDto {

    private UUID messageId;
    private Boolean isUserOwner;
    private String text;
    private Boolean isEdit;
    private String login;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateTime;
}
