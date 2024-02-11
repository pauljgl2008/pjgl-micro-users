package com.demo.pjglmicrousers.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@RefreshScope
@Configuration
@Data
public class UsersProperties {

    @Value("${message}")
    private String message;

}

