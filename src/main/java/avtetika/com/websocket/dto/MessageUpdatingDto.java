package avtetika.com.websocket.dto;

import avtetika.com.websocket.enums.ActionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class MessageUpdatingDto extends AbstractAction {

    public MessageUpdatingDto() {
        this.actionType = ActionType.UPDATE_MESSAGE;
    }

    private UUID messageId;
    private String text;
}
