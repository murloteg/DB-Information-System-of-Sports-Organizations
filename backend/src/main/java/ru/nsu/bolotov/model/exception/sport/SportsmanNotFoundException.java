package ru.nsu.bolotov.model.exception.sport;

public class SportsmanNotFoundException extends RuntimeException {
    public SportsmanNotFoundException(String message) {
        super(message);
    }
}
