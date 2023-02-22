package avtetika.com.websocket.service;

import avtetika.com.websocket.dto.AbstractAction;

import java.util.Collection;
import java.util.UUID;

public interface ActionService {

    void notifyUser(AbstractAction action, Collection<UUID> usersId);

    void addSession(UUID userId, String session);

    void deleteSession(String session);
}
