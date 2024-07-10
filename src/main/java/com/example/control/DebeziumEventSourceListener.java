package com.example.control;

import com.example.model.DebeziumChangeEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.config.Configuration;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class DebeziumEventSourceListener {

    private final Executor executor;
    private final DebeziumEngine<ChangeEvent<String, String>> engine;
    private final WebSocketService wsService;
    private final ObjectMapper objectMapper;

    public DebeziumEventSourceListener(Configuration config, WebSocketService wsService, ObjectMapper objectMapper) {

        this.wsService = wsService;
        this.objectMapper = objectMapper;
        this.executor = Executors.newSingleThreadExecutor();
        this.engine = DebeziumEngine.create(Json.class)
                .using(config.asProperties())
                .notifying(this::handleEvent)
                .build();
    }

    @SneakyThrows
    private void handleEvent(ChangeEvent<String, String> event) {
        log.debug("Received event: [{}:{}]", event.key(), event.value());
        var DCE = objectMapper
                .readValue(event.value(), DebeziumChangeEvent.class)
                .getPayload()
                .getAfter();

        wsService.sendMessage(DCE.getUpload_token(), String.valueOf(DCE.getToken_status()));
    }

    // TODO add cron job to check regulary for open replication slots with active = false and remove them @Schedules

    void onStart(@Observes StartupEvent event) {
        this.executor.execute(engine);
        log.info("Starting debezium event source listener");
    }

    void onStop(@Observes ShutdownEvent event) {
        log.info("Stopping debezium event source listener");
    }
}
