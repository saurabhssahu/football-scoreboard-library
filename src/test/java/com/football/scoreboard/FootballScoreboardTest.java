package com.football.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FootballScoreboardTest {

    private FootballScoreboard footballScoreboard;

    @BeforeEach
    void setup() {
        footballScoreboard = new FootballScoreboard();
    }

    @DisplayName("Test startMatch method with valid team names")
    @Test
    void testStartMatch_Successfully() {
        footballScoreboard.startMatch("Mexico", "Canada");
        assertEquals("Mexico 0 - Canada 0", footballScoreboard.getSummary());
    }

    @DisplayName("Test startMatch method with invalid team names")
    @ParameterizedTest
    @CsvSource({
            ", Canada",
            "' ', Mexico",
            "India, "
    })
    void testStartMatch_invalidTeamNames(String homeTeam, String awayTeam) {
        Exception exception = assertThrows(ScoreboardException.class, () ->
                footballScoreboard.startMatch(homeTeam, awayTeam));
        if (isBlank(homeTeam)) {
            assertEquals("Home team name must not be null or empty.", exception.getMessage());
        } else {
            assertEquals("Away team name must not be null or empty.", exception.getMessage());
        }
    }

    @DisplayName("Test startMatch method with duplicate team names")
    @Test
    void testStartMatch_duplicateTeamNames() {
        Exception exception = assertThrows(ScoreboardException.class, () ->
                footballScoreboard.startMatch("Norway", "norway"));
        assertEquals("Home and away teams must be different.", exception.getMessage());
    }

    @DisplayName("Test startMatch method with duplicate matches")
    @ParameterizedTest
    @CsvSource({
            "Norway, Denmark",
            "Norway, denmark"
    })
    void testStartMatch_duplicateMatches(String homeTeam, String awayTeam) {

        footballScoreboard.startMatch("Norway", "Denmark");
        Exception exception = assertThrows(ScoreboardException.class, () ->
                footballScoreboard.startMatch(homeTeam, awayTeam));
        String errorMessage = "Match already in progress between " + homeTeam + " and " + awayTeam + ".";
        assertEquals(errorMessage, exception.getMessage());
    }
}