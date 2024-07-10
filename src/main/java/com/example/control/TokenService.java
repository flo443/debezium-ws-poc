package com.example.control;

import com.example.control.error.NotFoundException;
import com.example.entity.UploadKeys;
import com.example.model.TokenStatusDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.UUID;
import lombok.SneakyThrows;

@ApplicationScoped
public class TokenService {

    @Inject
    UploadKeysRepository uploadKeysRepository;

    @Transactional
    public UploadKeys generateToken() {
        UUID uuid = UUID.randomUUID();

        UploadKeys uploadKey = UploadKeys.builder()
                .upload_token(uuid)
                .token_status(TokenStatusDto.INITIALIZED)
                .build();

        uploadKeysRepository.getEntityManager().persist(uploadKey);

        return uploadKey;
    }

    public Collection<UploadKeys> getUploadKeys() {
        return uploadKeysRepository.getAllDocuments();
    }

    @SneakyThrows
    public UploadKeys getUploadKey(UUID uploadToken) {
        return uploadKeysRepository.findByToken(uploadToken).orElseThrow(() -> new NotFoundException(uploadToken));
    }

    @Transactional
    public UploadKeys updateProcess(UUID token, TokenStatusDto tokenStatus) {
        UploadKeys entry = uploadKeysRepository.findByToken(token).orElseThrow(() -> new NotFoundException(token));

        entry.setToken_status(tokenStatus);
        entry.persist();
        return entry;
    }
}
