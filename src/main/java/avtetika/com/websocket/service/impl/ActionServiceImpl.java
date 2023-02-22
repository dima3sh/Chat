package avtetika.com.websocket.service.impl;

import avtetika.com.websocket.dto.AbstractAction;
import avtetika.com.websocket.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ActionServiceImpl implements ActionService {

    private final String ACTION_DESTINATION = "/async/api/action";
    private final SimpMessagingTemplate messagingTemplate;
    private final Map<UUID, List<String>> users;

    @Autowired
    public ActionServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.users = new HashMap<>();
    }

    @Async
    @Override
    public void notifyUser(AbstractAction action, Collection<UUID> usersId) {
        try {
        Map<UUID, List<String>> userSessions = usersId.stream()
                .filter(users::containsKey)
                .collect(Collectors.toMap(k -> k, users::get));

        userSessions.forEach((key, value) -> value.forEach(session -> {
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
            headerAccessor.setLeaveMutable(true);
            headerAccessor.setSessionId(session);
            messagingTemplate.convertAndSendToUser(session, ACTION_DESTINATION, action, headerAccessor.getMessageHeaders());
        }));
        } catch (Exception e) {
            System.out.println("Caught exception " + action.toString() + " ");
            for (UUID id : usersId) {
                System.out.print(id + ", ");
            }
        }
    }

    @Async
    @Override
    public void addSession(UUID userId, String session) {
        List<String> list = new ArrayList<>();
        list.add(session);
        users.put(userId, list);
    }

    @Async
    @Override
    public void deleteSession(String session) {
        users.forEach((key, value) -> {
            value.remove(session);
            System.out.println("User: " + key + " unsubscribe");
        });
    }
}
