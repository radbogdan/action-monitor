package com.betvictor.actionmonitor.api;

import com.betvictor.actionmonitor.api.model.MessageDto;
import com.betvictor.actionmonitor.error.MessageNotFoundException;
import com.betvictor.actionmonitor.mapper.MessageMapper;
import com.betvictor.actionmonitor.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper mapper;

    public MessageController(final MessageService messageService, final MessageMapper mapper) {
        this.messageService = messageService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Object> addMessage(@RequestParam String message) {
        log.info("Received message [{}]", message);
        if(message.isEmpty() || Objects.isNull(message)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Received message cannot be null or empty!");
        }
        messageService.insertMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findMessageById(@PathVariable String id) {
        log.info("Searching for id [{}]", id);
        try {
            return ResponseEntity.ok(mapper.asMessageDto(messageService.findById(id)));
        } catch (MessageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMessage(@PathVariable String id, @RequestBody MessageDto messageDto) {
        log.info("Received message [{}]", messageDto);
        final MessageDto updatedMessage = mapper.asMessageDto(messageService.updateById(id, mapper.asStoredMessage(messageDto)));
        return ResponseEntity.ok().body(updatedMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMessage(@PathVariable String id) {
        log.info("Deleting message with id [{}]", id);
        messageService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<Object> findAllMessages() {
        log.info("Retrieving all messages!");
        return ResponseEntity.ok(mapper.asMessageDtoList(messageService.findAllMessages()));
    }

}
