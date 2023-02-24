package com.emiteai.challengeEmiteaiTms.controllers;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController implements HealthIndicator {
    @Override
    @GetMapping("/health")
    public Health health() {
        // You can add your custom health checks here
        return Health.up().build();
    }
}




