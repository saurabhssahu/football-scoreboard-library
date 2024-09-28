package com.football.scoreboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.football.scoreboard.ScoreboardUtils.validateTeamNames;

public class FootballScoreboard {

    private static final Logger LOG = LoggerFactory.getLogger(FootballScoreboard.class);

    private final Map<String, Match> matches = new HashMap<>();

    /**
     * Starts a new game with the given home and away teams.
     * @param homeTeam the name of the home team.
     * @param awayTeam the name of the away team.
     */
    public void startMatch(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);
        String matchKey = homeTeam + ":" + awayTeam;
        matches.put(matchKey, new Match(homeTeam, awayTeam));
        LOG.info("Started match: {} vs {}", homeTeam, awayTeam);
    }

    /**
     * Retrieves a summary of all ongoing games, ordered by total score.
     * Games with the same total score are ordered by most recently started.
     *
     * @return Summary of ongoing games
     */
    public String getSummary() {
        return matches.values().stream()
                .sorted()
                .map(Match::toString)
                .collect(Collectors.joining("\n"));
    }
}
