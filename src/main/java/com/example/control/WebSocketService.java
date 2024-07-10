package com.example.control;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.Session;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Data
@Slf4j
public class WebSocketService {

    private final Map<UUID, Session> sessions = new ConcurrentHashMap<>();

    public void addSession(UUID id, Session session) {
        sessions.put(id, session);
        session.getAsyncRemote().sendText(String.format("Connected for %s", id));
    }

    public void removeSession(UUID id) {
        sessions.remove(id);
    }

    public void sendMessage(UUID id, String message) {
        var session = Optional.ofNullable(sessions.get(id));

        if (session.isEmpty()) return;

        var openConnection = session.get();
        openConnection.getAsyncRemote().sendText(message, sendResult -> {
            log.debug("Sent message: {}", sendResult.isOK() ? "OK" : "ERROR");
        });
    }
}
