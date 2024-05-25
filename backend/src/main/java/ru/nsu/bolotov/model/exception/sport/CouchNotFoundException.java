package ru.nsu.bolotov.model.exception.sport;

public class CouchNotFoundException extends RuntimeException {
    public CouchNotFoundException(String message) {
        super(message);
    }
}
