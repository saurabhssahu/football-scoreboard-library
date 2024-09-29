package com.football.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.football.scoreboard.GameConstants.DENMARK;
import static com.football.scoreboard.GameConstants.GAME_NOT_FOUND_MESSAGE;
import static com.football.scoreboard.GameConstants.NORWAY;
import static com.football.scoreboard.GameConstants.SWEDEN;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FootballScoreboardTest {

    private FootballScoreboard footballScoreboard;

    @BeforeEach
    void setup() {
        footballScoreboard = new FootballScoreboard();
    }

    @DisplayName("Test startGame method with valid team names")
    @Test
    void testStartGame_successfully() {
        footballScoreboard.startGame("Mexico", "Canada");
        assertEquals("Mexico 0 - Canada 0", footballScoreboard.getSummary());
    }

    @DisplayName("Test startGame method with invalid team names")
    @ParameterizedTest
    @CsvSource({
            ", Canada",
            "' ', Mexico",
            "India, "
    })
    void testStartGame_invalidTeamNames(String homeTeam, String awayTeam) {
        Exception exception = assertThrows(ScoreboardException.class, () ->
                footballScoreboard.startGame(homeTeam, awayTeam));
        if (isBlank(homeTeam)) {
            assertEquals("Home team name must not be null or empty.", exception.getMessage());
        } else {
            assertEquals("Away team name must not be null or empty.", exception.getMessage());
        }
    }

    @DisplayName("Test startGame method with same teams")
    @Test
    void testStartGame_withSameTeams() {
        Exception exception = assertThrows(ScoreboardException.class, () ->
                footballScoreboard.startGame(NORWAY, "norway"));
        assertEquals("Home and away teams must be different.", exception.getMessage());
    }

    @DisplayName("Test startGame method with duplicate games")
    @ParameterizedTest
    @CsvSource({
            "Norway, Denmark",
            "Norway, denmark"
    })
    void testStartGame_duplicateGames(String homeTeam, String awayTeam) {
        footballScoreboard.startGame(NORWAY, DENMARK);
        Exception exception = assertThrows(ScoreboardException.class, () ->
                footballScoreboard.startGame(homeTeam, awayTeam));
        String errorMessage = "Game already in progress between " + homeTeam + " and " + awayTeam + ".";
        assertEquals(errorMessage, exception.getMessage());
    }

    @DisplayName("Test updateScore method with valid team scores")
    @Test
    void testUpdateScore_successfully() {
        footballScoreboard.startGame(NORWAY, DENMARK);
        footballScoreboard.updateScore(NORWAY, DENMARK, 3, 2);

        assertEquals("Norway 3 - Denmark 2", footballScoreboard.getSummary());
    }

    @DisplayName("Test updateScore method with invalid team scores")
    @ParameterizedTest
    @CsvSource({
            "-2, 3",
            "4, -5"
    })
    void testUpdateScore_invalidTeamScore(int homeScore, int awayScore) {
        footballScoreboard.startGame(NORWAY, DENMARK);

        Exception exception = assertThrows(ScoreboardException.class, () ->
                footballScoreboard.updateScore(NORWAY, DENMARK, homeScore, awayScore));

        assertEquals("Scores must not be negative.", exception.getMessage());
    }

    @DisplayName("Test updateScore method for non existing game")
    @Test
    void testUpdateScore_nonExistentGame() {
        Exception exception = assertThrows(ScoreboardException.class, () ->
                footballScoreboard.updateScore(NORWAY, SWEDEN, 1, 0));
        assertEquals(GAME_NOT_FOUND_MESSAGE, exception.getMessage());
    }

    @DisplayName("Test finishGame method for an ongoing game")
    @Test
    void testFinishGame_successfully() {
        footballScoreboard.startGame(NORWAY, SWEDEN);
        footballScoreboard.finishGame(NORWAY, SWEDEN);
        assertTrue(footballScoreboard.getSummary().isEmpty());
    }

    @DisplayName("Test finishGame method for non existing game")
    @Test
    void testFinishGame_nonExistentGame() {
        Exception exception = assertThrows(ScoreboardException.class, () ->
                footballScoreboard.finishGame(NORWAY, SWEDEN));
        assertEquals("Cannot finish a non-existing game.", exception.getMessage());
    }
}