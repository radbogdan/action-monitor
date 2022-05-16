package com.betvictor.actionmonitor.storage.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class StoredMessage {
    @Id
    private String id;
    private String message;


    public String getId() {
        return id;
    }

    public StoredMessage setId(String id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public StoredMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "ID='" + id + '\'' + " and message='" + message + '\'' + " was ";
    }
}
