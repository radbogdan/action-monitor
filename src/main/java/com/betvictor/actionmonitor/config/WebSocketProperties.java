package com.betvictor.actionmonitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@lombok.Data
public class WebSocketProperties {
    @Value("${websocket.destination}")
    private String destination;
    @Value("${websocket.endpoint}")
    private String endpoint;
}
