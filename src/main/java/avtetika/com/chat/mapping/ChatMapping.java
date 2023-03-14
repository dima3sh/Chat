package avtetika.com.chat.mapping;

import avtetika.com.chat.dto.ChatMessageResponseDto;
import avtetika.com.entity.Message;
import avtetika.com.websocket.dto.MessageAddingDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMapping {

    List<ChatMessageResponseDto> map(List<Message> messages, @Context UUID userId);

    @Mapping(target="login", source="message.user.login")
    @Mapping(target="userId", source="message.user.userId")
    @Mapping(target="isUserOwner", expression = "java( false )")
    MessageAddingDto mapToMessage(Message message);

    @Mapping(target="login", source="message.user.login")
    @Mapping(target="isUserOwner", expression = "java( userId.equals(message.getUser().getUserId()) )")
    ChatMessageResponseDto map(Message message, @Context UUID userId);



}
