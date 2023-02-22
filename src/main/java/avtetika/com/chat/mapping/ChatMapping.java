package avtetika.com.chat.mapping;

import avtetika.com.chat.dto.ChatMessageResponseDto;
import avtetika.com.entity.Message;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMapping {

    List<ChatMessageResponseDto> map(List<Message> messages, @Context UUID userId);

    default ChatMessageResponseDto map(Message message, @Context UUID userId) {
        ChatMessageResponseDto response = new ChatMessageResponseDto();
        response.setDateTime(message.getDateTime());
        response.setIsEdit(message.getIsEdit());
        response.setText(message.getText());
        response.setMessageId(message.getMessageId());
        response.setIsUserOwner(userId.equals(message.getUser().getUserId()));
        return response;
    }
}
