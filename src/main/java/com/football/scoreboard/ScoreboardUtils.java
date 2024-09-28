package com.football.scoreboard;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class ScoreboardUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ScoreboardUtils.class);

    private ScoreboardUtils() {
    }

    public static void validateTeamNames(String homeTeam, String awayTeam) {
        if (isBlank(homeTeam)) {
            LOG.error("Home team name: {} is invalid.", homeTeam);
            throw new IllegalArgumentException("Home team name must not be null or empty.");
        }
        if (isBlank(awayTeam)) {
            LOG.error("Away team name: {} is invalid.", awayTeam);
            throw new IllegalArgumentException("Away team name must not be null or empty.");
        }
        if (equalsIgnoreCase(homeTeam, awayTeam)) {
            LOG.error("Home team {} and away team {} cannot be the same.", homeTeam, awayTeam);
            throw new IllegalArgumentException("Home and away teams must be different.");
        }
    }
}
