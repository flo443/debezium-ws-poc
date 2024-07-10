package com.example.control;

import com.example.entity.UploadKeys;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UploadKeysRepository implements PanacheRepository<UploadKeys> {
    public Collection<UploadKeys> getAllDocuments() {
        return this.listAll();
    }

    public Optional<UploadKeys> findByToken(UUID token) {
        return find("upload_token", token).firstResultOptional();
    }
}
