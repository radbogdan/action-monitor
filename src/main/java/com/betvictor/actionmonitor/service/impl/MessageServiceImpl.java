package com.betvictor.actionmonitor.service.impl;

import com.betvictor.actionmonitor.error.MessageNotFoundException;
import com.betvictor.actionmonitor.service.MessageService;
import com.betvictor.actionmonitor.service.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.betvictor.actionmonitor.storage.MongoMessageRepository;
import com.betvictor.actionmonitor.storage.model.StoredMessage;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    private static final String INSERT_OPERATION_MESSAGE = "inserted";
    private static final String UPDATE_OPERATION_MESSAGE = "updated";
    private static final String DELETE_OPERATION_MESSAGE = "deleted";
    private final MongoMessageRepository mongoMessageRepository;
    private final Notification notification;


    public MessageServiceImpl(final MongoMessageRepository mongoMessageRepository, final Notification notification) {
        this.mongoMessageRepository = mongoMessageRepository;
        this.notification = notification;
    }

    @Override
    public StoredMessage insertMessage(final String message) {
        final StoredMessage storedMessage = mongoMessageRepository.insert(new StoredMessage().setMessage(message));
        log.info("Message [{}] was inserted successfully!", storedMessage.getMessage());
        notification.notify(INSERT_OPERATION_MESSAGE, storedMessage);
        return storedMessage;
    }

    @Override
    public StoredMessage findById(final String id) throws MessageNotFoundException {
        final Optional<StoredMessage> storedMessage = mongoMessageRepository.findById(id);
        return storedMessage.orElseThrow(() -> new MessageNotFoundException(format("The requested id: [%s] is missing from database!", id)));
    }

    @Override
    public void deleteById(final String id) {
        final Optional<StoredMessage> storedMessage = mongoMessageRepository.findById(id);
        mongoMessageRepository.deleteById(id);
        if (storedMessage.isPresent()) {
            notification.notify(DELETE_OPERATION_MESSAGE, storedMessage.get());
        }
        log.info("Message with id {[]} was deleted!", id);
    }

    @Override
    public StoredMessage updateById(final String id, final StoredMessage message) {
        final StoredMessage storedMessage = mongoMessageRepository.save(message);
        log.info("Message [{}] was updated successfully!", storedMessage.getMessage());
        notification.notify(UPDATE_OPERATION_MESSAGE, storedMessage);
        return storedMessage;
    }

    @Override
    public List<StoredMessage> findAllMessages() {
        final List<StoredMessage> storedMessages = mongoMessageRepository.findAll();
        log.info("Messages found [{}]", storedMessages);
        return storedMessages;
    }

}
