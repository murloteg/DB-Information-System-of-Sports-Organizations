package ru.nsu.bolotov.model.exception.sport;

public class SportClubNotFoundException extends RuntimeException {
    public SportClubNotFoundException(String message) {
        super(message);
    }
}
