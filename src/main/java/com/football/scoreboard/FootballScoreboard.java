package com.football.scoreboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.football.scoreboard.ScoreboardUtils.generateMatchKey;
import static com.football.scoreboard.ScoreboardUtils.validateTeamNames;

public class FootballScoreboard {

    private static final Logger LOG = LoggerFactory.getLogger(FootballScoreboard.class);

    private final Map<String, Match> matches = new HashMap<>();

    /**
     * Starts a new game with the given home and away teams.
     *
     * @param homeTeam the name of the home team.
     * @param awayTeam the name of the away team.
     * @throws ScoreboardException if team names are invalid or a match between these teams is already in progress.
     */
    public void startMatch(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);

        String matchKey = generateMatchKey(homeTeam, awayTeam);
        if (matches.containsKey(matchKey)) {
            LOG.error("Football match between {} and {} already in progress.", homeTeam, awayTeam);
            throw new ScoreboardException("Match already in progress between " + homeTeam + " and " + awayTeam + ".");
        }

        matches.put(matchKey, new Match(homeTeam, awayTeam));
        LOG.info("Started match: {} vs {}", homeTeam, awayTeam);
    }

    /**
     * Updates the score of an ongoing match.
     *
     * @param homeTeam  the name of the home team.
     * @param awayTeam  the name of the away team.
     * @param homeScore the score of the home team.
     * @param awayScore the score of the away team.
     * @throws ScoreboardException if team scores are invalid or a match between these teams is not found.
     */
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {

        String matchKey = generateMatchKey(homeTeam, awayTeam);
        Match match = matches.get(matchKey);

        match.setScores(homeScore, awayScore);
        LOG.info("Updated score: {} {} - {} {}", homeTeam, homeScore, awayTeam, awayScore);
    }

    /**
     * Retrieves a summary of all ongoing matches, ordered by total score.
     * Matches with the same total score are ordered by most recently started.
     *
     * @return Summary of ongoing matches
     */
    public String getSummary() {
        return matches.values().stream()
                .sorted()
                .map(Match::toString)
                .collect(Collectors.joining("\n"));
    }
}
