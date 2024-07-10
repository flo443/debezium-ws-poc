package com.example.boundry.error;

import com.example.control.error.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Slf4j
public class ExceptionMapper {

    @ServerExceptionMapper
    public Response handleNotFound(NotFoundException e) {
        var message = String.format("No entry found for %s", e.getUpload_token());
        log.warn(message);

        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorDto.builder()
                        .severity(ErrorDto.Severity.INFO)
                        .message(message))
                .build();
    }

    @ServerExceptionMapper
    public Response handleError(Exception e) {
        log.warn("Unknown error occurred", e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ErrorDto.builder()
                        .severity(ErrorDto.Severity.ERROR)
                        .message(e.getMessage()))
                .build();
    }
}
