package avtetika.com.chat.service;

import avtetika.com.chat.dto.AddingMessageDto;
import avtetika.com.chat.dto.ChatMessageResponseDto;

import java.util.List;
import java.util.UUID;

public interface ChatService {

    List<ChatMessageResponseDto> findChatHistory(UUID userId, Integer page, Integer size);

    Boolean sendMessage(UUID userId, AddingMessageDto message);

    Boolean updateMessage(UUID userId, UUID messageId, AddingMessageDto message);

    Boolean deleteMessage(UUID userId, UUID messageId);
}
