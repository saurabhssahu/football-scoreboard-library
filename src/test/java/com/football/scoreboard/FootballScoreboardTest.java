package com.football.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FootballScoreboardTest {

    private FootballScoreboard footballScoreboard;

    @BeforeEach
    void setup() {
        footballScoreboard = new FootballScoreboard();
    }

    @Test
    void testStartMatchSuccessfully() {
        footballScoreboard.startMatch("Mexico", "Canada");
        assertEquals("Mexico 0 - Canada 0", footballScoreboard.getSummary());
    }
}