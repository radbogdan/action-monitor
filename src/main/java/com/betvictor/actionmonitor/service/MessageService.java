package com.betvictor.actionmonitor.service;

import com.betvictor.actionmonitor.error.MessageNotFoundException;
import com.betvictor.actionmonitor.storage.model.StoredMessage;

import java.util.List;

public interface MessageService {

    StoredMessage insertMessage(final String message);

    StoredMessage findById(final String id) throws MessageNotFoundException;

    void deleteById(final String id);

    StoredMessage updateById(final String id, final StoredMessage message);

    List<StoredMessage> findAllMessages();
}
