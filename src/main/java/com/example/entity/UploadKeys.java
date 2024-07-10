package com.example.entity;

import com.example.model.TokenStatusDto;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "upload_keys")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadKeys extends PanacheEntityBase {

    @Id
    @Column(name = "upload_token")
    private UUID upload_token;

    @Column(name = "token_status")
    private TokenStatusDto token_status;
}
