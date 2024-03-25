package com.demo.pjglmicrousers.requests;


import lombok.Data;

@Data
public class UsuarioDto {

    private String username;
    private String email;
    private String password;
    private String token;

}
