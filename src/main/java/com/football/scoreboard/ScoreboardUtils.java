package com.football.scoreboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isBlank;

public final class ScoreboardUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ScoreboardUtils.class);

    private ScoreboardUtils() {
    }

    /**
     * Generates a unique key for each game based on team names.
     *
     * @param homeTeam the name of the home team.
     * @param awayTeam the name of the away team.
     * @return the game key.
     */
    public static String generateGameKey(String homeTeam, String awayTeam) {
        return homeTeam.toLowerCase() + ":" + awayTeam.toLowerCase();
    }

    /**
     * Validates team names ensuring they are not null, empty, or the same.
     *
     * @param homeTeam the name of the home team.
     * @param awayTeam the name of the away team.
     */
    public static void validateTeamNames(String homeTeam, String awayTeam) {
        if (isBlank(homeTeam)) {
            LOG.error("Home team name: {} is invalid.", homeTeam);
            throw new ScoreboardException("Home team name must not be null or empty.");
        }
        if (isBlank(awayTeam)) {
            LOG.error("Away team name: {} is invalid.", awayTeam);
            throw new ScoreboardException("Away team name must not be null or empty.");
        }
        if (equalsIgnoreCase(homeTeam, awayTeam)) {
            LOG.error("Home team: {} and away team: {} cannot be the same.", homeTeam, awayTeam);
            throw new ScoreboardException("Home and away teams must be different.");
        }
    }

    /**
     * Validates scores ensuring they are non-negative.
     *
     * @param homeScore the score of the home team.
     * @param awayScore the score of the away team.
     */
    public static void validateScores(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            LOG.error("Invalid score: Home score {} - Away Score {}", homeScore, awayScore);
            throw new ScoreboardException("Scores must not be negative.");
        }
    }
}
