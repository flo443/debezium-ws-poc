package com.example.boundry;

import com.example.control.TokenService;
import com.example.control.UploadKeysRepository;
import com.example.entity.UploadKeys;
import com.example.model.TokenStatusDto;
import io.smallrye.common.constraint.NotNull;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.Collection;
import java.util.UUID;

@Path("/v1/token")
public class TokenResource {

    @Inject
    TokenService tokenService;

    @Inject
    UploadKeysRepository uploadKeysRepository;

    @POST
    public UploadKeys createUploadToken() {
        return tokenService.generateToken();
    }

    @GET
    public Collection<UploadKeys> getUploadToken() {
        return tokenService.getUploadKeys();
    }

    @GET
    @Path("/{token}")
    public Response getUploadToken(@PathParam("token") UUID token) {

        return Response.status(Response.Status.FOUND)
                .entity(tokenService.getUploadKey(token))
                .build();
    }

    @PATCH
    @Path("/{token}/status/{status}")
    public UploadKeys updateUploadToken(
            @PathParam("token") UUID token, @PathParam("status") TokenStatusDto tokenStatus) {
        return tokenService.updateProcess(token, tokenStatus);
    }
}
