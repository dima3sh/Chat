package avtetika.com.chat.service;

import avtetika.com.chat.dto.AddingMessageDto;
import avtetika.com.chat.dto.ChatMessageResponseDto;
import avtetika.com.chat.mapping.ChatMapping;
import avtetika.com.chat.repository.ChatRepository;
import avtetika.com.entity.Message;
import avtetika.com.exception.ApiException;
import avtetika.com.user.service.UserService;
import avtetika.com.websocket.dto.AbstractAction;
import avtetika.com.websocket.dto.MessageAddingDto;
import avtetika.com.websocket.dto.MessageUpdatingDto;
import avtetika.com.websocket.enums.ActionType;
import avtetika.com.websocket.service.ActionService;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    private final ActionService actionService;
    private final ChatRepository chatRepository;
    private final UserService userService;

    public ChatServiceImpl(ActionService actionService,
                           ChatRepository chatRepository,
                           UserService userService) {

        this.actionService = actionService;
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    @Override
    public List<ChatMessageResponseDto> findChatHistory(UUID userId, Integer page, Integer size) {
        List<Message> messages = chatRepository.findAllByIsDelete(false, PageRequest.of(page, size, Sort.by("dateTime").descending())).getContent();
        List<ChatMessageResponseDto> response = Mappers.getMapper(ChatMapping.class).map(messages, userId);
        Collections.reverse(response);
        return response;
    }

    @Override
    public Boolean sendMessage(UUID userId, AddingMessageDto request) {

        Message message = new Message();
        message.setDateTime(LocalDateTime.now());
        message.setIsEdit(false);
        message.setText(request.getText());
        message.setUser(userService.findUser(userId));
        message.setIsDelete(false);
        chatRepository.save(message);

        MessageAddingDto addingMessage = new MessageAddingDto();
        addingMessage.setText(message.getText());
        addingMessage.setMessageId(message.getMessageId());
        addingMessage.setDateTime(message.getDateTime());
        addingMessage.setUserId(userId);
        addingMessage.setIsUserOwner(false);
        addingMessage.setLogin(message.getUser().getLogin());

        List<UUID> users =  userService.findAll().stream().filter(x -> !x.equals(userId)).collect(Collectors.toList());

        actionService.notifyUser(addingMessage, users);
        addingMessage.setIsUserOwner(true);
        actionService.notifyUser(addingMessage, List.of(userId));
        return true;
    }

    @Override
    public Boolean updateMessage(UUID userId, UUID messageId, AddingMessageDto request) {
        Message message = chatRepository.findById(messageId).orElseThrow(() -> new ApiException(400, "Message not Found"));
        if (!message.getUser().getUserId().equals(userId)) {
            throw new ApiException(400, "Hasn't access");
        }
        message.setText(request.getText());
        message.setIsEdit(true);
        chatRepository.save(message);
        MessageUpdatingDto messageUpdating = new MessageUpdatingDto();
        messageUpdating.setMessageId(messageId);
        messageUpdating.setText(request.getText());
        actionService.notifyUser(messageUpdating, userService.findAll());
        return true;
    }

    @Override
    public Boolean deleteMessage(UUID userId, UUID messageId) {
        Message message = chatRepository.findById(messageId).orElseThrow(() -> new ApiException(400, "Message not Found"));
        message.setIsDelete(true);
        chatRepository.save(message);
        AbstractAction messageDeleting = new AbstractAction() {
            @Override
            public ActionType getActionType() {
                return ActionType.DELETE_MESSAGE;
            }
        };
        messageDeleting.setMessageId(messageId);
        actionService.notifyUser(messageDeleting, userService.findAll());
        return true;
    }
}
