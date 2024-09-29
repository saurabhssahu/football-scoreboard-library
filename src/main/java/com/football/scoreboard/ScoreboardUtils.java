package com.football.scoreboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class ScoreboardUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ScoreboardUtils.class);

    private ScoreboardUtils() {
    }

    /**
     * Generates a unique key for each match based on team names.
     *
     * @param homeTeam the name of the home team.
     * @param awayTeam the name of the away team.
     * @return the match key.
     */
    public static String generateMatchKey(String homeTeam, String awayTeam) {
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
}
