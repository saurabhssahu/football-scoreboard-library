package com.football.scoreboard;

/**
 * Custom exception for handling scoreboard-related errors.
 */
public class ScoreboardException extends RuntimeException {

    public ScoreboardException(String message) {
        super(message);
    }
}
