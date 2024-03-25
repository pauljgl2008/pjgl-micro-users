package com.demo.pjglmicrousers.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
@Document(collection = "usuarios")
public class UsuarioEntity {

    @Id
    private UUID id = UUID.randomUUID();

    private Instant created;
    private String username;
    private String email;

    private String password;


}

