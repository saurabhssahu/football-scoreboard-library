package com.football.scoreboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.football.scoreboard.ScoreboardUtils.generateGameKey;
import static com.football.scoreboard.ScoreboardUtils.validateScores;
import static com.football.scoreboard.ScoreboardUtils.validateTeamNames;
import static java.util.Objects.isNull;

public class FootballScoreboard {

    private static final Logger LOG = LoggerFactory.getLogger(FootballScoreboard.class);

    private final Map<String, Game> games = new HashMap<>();

    /**
     * Starts a new game with the given home and away teams.
     *
     * @param homeTeam the name of the home team.
     * @param awayTeam the name of the away team.
     * @throws ScoreboardException if team names are invalid or a game between these teams is already in progress.
     */
    public void startGame(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);

        String gameKey = generateGameKey(homeTeam, awayTeam);
        if (games.containsKey(gameKey)) {
            LOG.error("Football game between {} and {} already in progress.", homeTeam, awayTeam);
            throw new ScoreboardException("Game already in progress between " + homeTeam + " and " + awayTeam + ".");
        }

        games.put(gameKey, new Game(homeTeam, awayTeam));
        LOG.info("Started game: {} vs {}", homeTeam, awayTeam);
    }

    /**
     * Updates the score of an ongoing game.
     *
     * @param homeTeam  the name of the home team.
     * @param awayTeam  the name of the away team.
     * @param homeScore the score of the home team.
     * @param awayScore the score of the away team.
     * @throws ScoreboardException if team scores are invalid or a game between these teams is not found.
     */
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        validateScores(homeScore, awayScore);

        String gameKey = generateGameKey(homeTeam, awayTeam);
        Game game = games.get(gameKey);

        if (isNull(game)) {
            LOG.error("No game found between {} and {}", homeTeam, awayTeam);
            throw new ScoreboardException("Game not found between these teams.");
        }

        game.setScores(homeScore, awayScore);
        LOG.info("Updated score: {} {} - {} {}", homeTeam, homeScore, awayTeam, awayScore);
    }

    /**
     * Retrieves a summary of all ongoing games, ordered by total score.
     * Gamees with the same total score are ordered by most recently started.
     *
     * @return Summary of ongoing games
     */
    public String getSummary() {
        return games.values().stream()
                .sorted()
                .map(Game::toString)
                .collect(Collectors.joining("\n"));
    }
}
