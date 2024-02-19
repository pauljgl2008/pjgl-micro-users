package com.demo.pjglmicrousers.controllers;

import com.demo.pjglmicrousers.config.UsersProperties;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping(value = "/users")
public class UsersController {

    private UsersProperties usersProperties;

    private MeterRegistry registry;

    public UsersController(UsersProperties usersProperties, MeterRegistry registry) {
        this.usersProperties = usersProperties;
        this.registry = registry;
    }

    @GetMapping
    @Timed("pauljgl.timer")
    public String getMessage() {
        Timer timer =
                this.registry.timer("pauljgl.getMessage.time");
        timer.record( ()->{
            try {
                Thread.sleep(new
                        Random().nextInt(1500));
            } catch (InterruptedException e) {}
            System.out.println("Waiting...");
        });
        log.info("Total time {} ms",timer.totalTime(TimeUnit.MILLISECONDS));


        return "message: " + this.usersProperties.getMessage();
    }

}
