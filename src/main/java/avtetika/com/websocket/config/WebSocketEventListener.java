package avtetika.com.websocket.config;

import avtetika.com.authorization.service.JwtProvider;
import avtetika.com.exception.ApiException;
import avtetika.com.exception.user.UserNotFoundApiException;
import avtetika.com.websocket.service.ActionService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Component
public class WebSocketEventListener {

    private final ActionService actionService;
    private final JwtProvider jwtProvider;

    public WebSocketEventListener(ActionService actionService, JwtProvider jwtProvider) {
        this.actionService = actionService;
        this.jwtProvider = jwtProvider;
    }

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();

        String token;
        if (event.getMessage().getHeaders().get("nativeHeaders") != null) {
            try {
                MessageHeaders headers = event.getMessage().getHeaders();
                token = getToken(headers);
                if (token != null && jwtProvider.validateAccessToken(token)) {
                    String userId = jwtProvider.getAccessClaims(token).getSubject();
                    System.out.println("User with id:" + userId + " subscribe");
                    actionService.addSession(UUID.fromString(userId), sessionId);
                } else {
                    throw new UserNotFoundApiException();
                }
            } catch (ApiException e) {
                throw e;
            } catch (Exception e) {
                System.out.println("Cast exception");
            }
        }
    }

    @EventListener
    public void handleWebSocketUnsubscribedListener(SessionUnsubscribeEvent event) {
        actionService.deleteSession(event.getMessage().getHeaders().get("simpSessionId").toString());
    }

    private String getTokenFromRequest(String request) {
        if (StringUtils.hasText(request) && request.startsWith("Bearer ")) {
            return request.substring(7);
        }
        return null;
    }

    private String getToken(MessageHeaders headers) {
        Map<String, Object> map = (Map<String, Object>) headers.get("nativeHeaders");
        if (map != null) {
            return getTokenFromRequest(((ArrayList) map.get("Authorization")).get(0).toString());
        }
        return null;
    }
}
