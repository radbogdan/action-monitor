package com.betvictor.actionmonitor.service.impl;

import com.betvictor.actionmonitor.config.WebSocketProperties;
import com.betvictor.actionmonitor.service.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import com.betvictor.actionmonitor.storage.model.StoredMessage;

import java.util.Objects;

@Component
@Slf4j
public class MessageNotification implements Notification<String, StoredMessage> {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final WebSocketProperties webSocketProperties;

    public MessageNotification(final SimpMessagingTemplate simpMessagingTemplate, final WebSocketProperties webSocketProperties) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.webSocketProperties = webSocketProperties;
    }

    @Override
    public void notify(final String operation, final StoredMessage payload) {
        if (Objects.isNull(payload)) {
            log.warn("Payload must not be null!");
        }
        simpMessagingTemplate.convertAndSend(webSocketProperties.getDestination(), payload + operation);
        log.info("Notification sent!");

    }
}
