package com.demo.pjglmicrousers.controllers;

import com.demo.pjglmicrousers.config.UsersProperties;
import com.demo.pjglmicrousers.requests.UsuarioDto;
import com.demo.pjglmicrousers.services.JwtService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping(value = "/users")
public class UsersController {

    private UsersProperties usersProperties;

    private MeterRegistry meterRegistry;

    private JwtService jwtService;

    public UsersController(UsersProperties usersProperties, MeterRegistry meterRegistry, JwtService jwtService) {
        this.usersProperties = usersProperties;
        this.meterRegistry = meterRegistry;
        this.jwtService = jwtService;
    }

    private final Random random = new Random();

    @GetMapping("/read")
    @Timed(value = "read.test", histogram = true, percentiles = {0.5, 0.75, 0.95, 0.99, 0.9999})
    public String read() throws InterruptedException {
        Counter counter = Counter.builder("api_read_get")
                .description("a number of requests to /read endpoint")
                .register(meterRegistry);

        // Imitating call latency
        Thread.sleep(10 + random.nextLong(50));
        counter.increment();
        return "Done";
    }

    @PostMapping("/signup")
    public ResponseEntity<UsuarioDto> signUp(@Valid @RequestBody UsuarioDto request) {
        // Generar token JWT
        String token = jwtService.generateToken(request);
        // Enviar token JWT al usuario
        UsuarioDto response = new UsuarioDto();
        response.setToken(token);
        response.setUsername(request.getUsername());
        response.setPassword(request.getPassword());
        ResponseEntity<UsuarioDto> response = new ResponseEntity<>(request, HttpStatus.CREATED);
        return response;
    }

    @PostMapping("/signin")
    public ResponseEntity<String> login(@RequestBody UsuarioDto request) {
        // Generar un nuevo token JWT
        String token = jwtService.generateToken(request);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping
    @Timed("pauljgl.getMessage.timed")
    public String getMessage() {
        Timer timer =
                this.meterRegistry.timer("pauljgl.getMessage.time");
        timer.record(() -> {
            try {
                Thread.sleep(new SecureRandom().nextInt(1500));
            } catch (InterruptedException e) {
                log.info(e.getMessage());
            }
            System.out.println("Waiting...");
        });
        log.info("Total time {} ms", timer.totalTime(TimeUnit.MILLISECONDS));


        return "message: " + this.usersProperties.getMessage();
    }

}
