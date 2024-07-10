package com.example.boundry;

import com.example.control.WebSocketService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@ServerEndpoint("/v1/ws/{id}")
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class WebSocketGateway {

    private final WebSocketService service;

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String idString) {
        var id = UUID.fromString(idString);
        log.info("opened for [{}]", id);
        service.addSession(id, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("id") String idString) {
        var id = UUID.fromString(idString);
        log.info("closed for [{}]", id);
        service.removeSession(id);
    }

    @OnError
    @SneakyThrows
    public void onError(Session session, @PathParam("id") String id, Throwable throwable) {
        log.warn("error for [{}]", id, throwable);
        session.close();
    }
}
