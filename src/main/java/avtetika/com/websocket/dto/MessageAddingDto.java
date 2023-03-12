package avtetika.com.websocket.dto;

import avtetika.com.websocket.enums.ActionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class MessageAddingDto extends AbstractAction {

    public MessageAddingDto() {
        this.actionType = ActionType.ADD_MESSAGE;
    }

    private String text;
    private String login;
    private UUID userId;
    private Boolean isUserOwner;
    private LocalDateTime dateTime;
}
