package com.demo.pjglmicrousers.controllers;

import com.demo.pjglmicrousers.config.UsersProperties;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
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

    private MeterRegistry meterRegistry;

    public UsersController(UsersProperties usersProperties, MeterRegistry meterRegistry) {
        this.usersProperties = usersProperties;
        this.meterRegistry = meterRegistry;
    }

    private final Random random = new Random();

    @GetMapping("/read")
    @Timed(value = "read.test",histogram = true, percentiles = {0.5, 0.75, 0.95, 0.99, 0.9999})
    public String read() throws InterruptedException {
        Counter counter = Counter.builder("api_read_get")
                .description("a number of requests to /read endpoint")
                .register(meterRegistry);

        // Imitating call latency
        Thread.sleep(10 + random.nextLong(50));
        counter.increment();
        return "Done";
    }

    @GetMapping
    @Timed("pauljgl.getMessage.timed")
    public String getMessage() {
        Timer timer =
                this.meterRegistry.timer("pauljgl.getMessage.time");
        timer.record(() -> {
            try {
                Thread.sleep(new
                        Random().nextInt(1500));
            } catch (InterruptedException e) {
            }
            System.out.println("Waiting...");
        });
        log.info("Total time {} ms", timer.totalTime(TimeUnit.MILLISECONDS));


        return "message: " + this.usersProperties.getMessage();
    }

}
