package ru.nsu.bolotov.model.exception.championship;

public class ChampionshipNotFoundException extends RuntimeException {
    public ChampionshipNotFoundException(String message) {
        super(message);
    }
}
