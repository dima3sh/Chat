package avtetika.com.chat.controller;

import avtetika.com.chat.dto.AddingMessageDto;
import avtetika.com.chat.dto.ChatMessageResponseDto;
import avtetika.com.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<ChatMessageResponseDto>> getChatHistory(@RequestAttribute UUID userId,
                                                                       @RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(chatService.findChatHistory(userId, page, size));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Boolean> sendMessage(@RequestAttribute UUID userId,
                                                                    @RequestBody AddingMessageDto message) {
        return ResponseEntity.ok(chatService.sendMessage(userId, message));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{messageId}")
    public ResponseEntity<Boolean> updateMessage(@RequestAttribute UUID userId,
                                                                      @PathVariable UUID messageId,
                                                                      @RequestBody AddingMessageDto message) {
        return ResponseEntity.ok(chatService.updateMessage(userId, messageId, message));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Boolean> deleteMessage(@RequestAttribute UUID userId, @PathVariable UUID messageId) {
        return ResponseEntity.ok(chatService.deleteMessage(userId, messageId));
    }
}
