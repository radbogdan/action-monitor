package com.betvictor.actionmonitor.storage;

import com.betvictor.actionmonitor.storage.model.StoredMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoMessageRepository extends MongoRepository<StoredMessage, String> {
}
