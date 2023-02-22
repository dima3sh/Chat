package avtetika.com.chat.service;

import avtetika.com.chat.dto.AddingMessageDto;
import avtetika.com.chat.dto.ChatMessageResponseDto;
import avtetika.com.chat.repository.ChatRepository;
import avtetika.com.entity.Message;
import avtetika.com.websocket.service.ActionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChatServiceImpl implements ChatService {

    private final ActionService actionService;
    private final ChatRepository chatRepository;

    public ChatServiceImpl(ActionService actionService, ChatRepository chatRepository) {

        this.actionService = actionService;
        this.chatRepository = chatRepository;
    }

    @Override
    public List<ChatMessageResponseDto> findChatHistory(UUID userId, Integer page, Integer size) {
        List<Message> messages = chatRepository.findAll(PageRequest.of(page, size)).getContent();

        return null;
    }

    @Override
    public Boolean sendMessage(UUID userId, AddingMessageDto message) {
        return null;
    }

    @Override
    public Boolean updateMessage(UUID userId, UUID messageId, AddingMessageDto message) {
        return null;
    }

    @Override
    public Boolean deleteMessage(UUID userId, UUID messageId) {
        return null;
    }
}
