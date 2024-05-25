package ru.nsu.bolotov.model.exception.sport;

public class SportTypeNotFoundException extends RuntimeException {
    public SportTypeNotFoundException(String message) {
        super(message);
    }
}
