package com.betvictor.actionmonitor.service;

public interface Notification<O, I> {
    void notify(O operation, I payload);
}
