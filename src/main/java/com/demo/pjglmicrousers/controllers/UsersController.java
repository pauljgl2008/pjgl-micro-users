package com.demo.pjglmicrousers.controllers;

import com.demo.pjglmicrousers.config.UsersProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UsersController {

    private UsersProperties usersProperties;

    public UsersController(UsersProperties usersProperties) {
        this.usersProperties = usersProperties;
    }

    @GetMapping
    public String getMessage() {
        return "message: " + this.usersProperties.getMessage();
    }

}
