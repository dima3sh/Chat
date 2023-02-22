package avtetika.com.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ChatMessageResponseDto {

    private UUID messageId;
    private Boolean isUserOwner;
    private String text;
    private Boolean isEdit;
    private LocalDateTime dateTime;
}
