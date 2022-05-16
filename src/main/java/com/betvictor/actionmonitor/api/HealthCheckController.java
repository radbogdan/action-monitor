package com.betvictor.actionmonitor.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HealthCheckController {
    @Value("${build.version}")
    private String buildVersion;

    @GetMapping("/status")
    public String health() {
        return "Up and running!";
    }

    @GetMapping("/version")
    public String applicationVersion() {
        return buildVersion;
    }
}
