package com.example.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DebeziumChangeEvent {

    DebeziumPayload payload;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DebeziumPayload {

        DebeziumDocument after;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class DebeziumDocument {

            UUID upload_token;
            TokenStatusDto token_status;
        }
    }
}
