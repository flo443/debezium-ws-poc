package com.example.boundry.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ErrorDto {

    @Builder.Default
    private Severity severity = Severity.INFO;

    private String message;

    @AllArgsConstructor
    public enum Severity {
        INFO("INFO"),
        WARNING("WARNING"),
        ERROR("ERROR");

        private final String value;
    }
}
