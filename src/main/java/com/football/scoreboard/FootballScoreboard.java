package com.football.scoreboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.football.scoreboard.GameConstants.GAME_NOT_FOUND_ERROR;
import static com.football.scoreboard.GameConstants.GAME_NOT_FOUND_MESSAGE;
import static com.football.scoreboard.ScoreboardUtils.generateGameKey;
import static com.football.scoreboard.ScoreboardUtils.validateScores;
import static com.football.scoreboard.ScoreboardUtils.validateTeamNames;
import static java.util.Objects.isNull;

public class FootballScoreboard {

    private static final Logger LOG = LoggerFactory.getLogger(FootballScoreboard.class);

    private final Map<String, Game> scoreboard = new HashMap<>();

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
        if (scoreboard.containsKey(gameKey)) {
            LOG.error("Football game between {} and {} already in progress.", homeTeam, awayTeam);
            throw new ScoreboardException("Game already in progress between " + homeTeam + " and " + awayTeam + ".");
        }

        scoreboard.put(gameKey, new Game(homeTeam, awayTeam));
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
        Game game = scoreboard.get(gameKey);

        if (isNull(game)) {
            LOG.error(GAME_NOT_FOUND_ERROR, homeTeam, awayTeam);
            throw new ScoreboardException(GAME_NOT_FOUND_MESSAGE);
        }

        game.updateScore(homeScore, awayScore);
        LOG.info("Updated score: {} {} - {} {}", homeTeam, homeScore, awayTeam, awayScore);
    }

    /**
     * Finishes an ongoing game and removes it from the scoreboard.
     *
     * @param homeTeam the name of the home team.
     * @param awayTeam the name of the away team.
     * @throws ScoreboardException if a game between these teams is not found.
     */
    public void finishGame(String homeTeam, String awayTeam) {
        String gameKey = generateGameKey(homeTeam, awayTeam);
        Game game = scoreboard.remove(gameKey);

        if (isNull(game)) {
            LOG.error(GAME_NOT_FOUND_ERROR, homeTeam, awayTeam);
            throw new ScoreboardException("Cannot finish a non-existing game.");
        }

        LOG.info("Finished game: {} vs {}", homeTeam, awayTeam);
    }

    /**
     * Retrieves a summary of all ongoing games, ordered by total score (homeTeamScore + awayTeamScore).
     * Games with the same total score are ordered by most recently started.
     *
     * @return Summary of ongoing games
     */
    public String getSummary() {
        String summary = scoreboard.values().stream()
                .sorted((game1, game2) -> {
                    int scoreComparison = Integer.compare(game2.getTotalScore(), game1.getTotalScore());
                    return scoreComparison != 0 ? scoreComparison :
                            game2.getStartTime().compareTo(game1.getStartTime());
                })
                .map(Game::toString)
                .collect(Collectors.joining("\n"));

        if (summary.isEmpty()) {
            LOG.info("No games in progress.");
        } else {
            LOG.info("Summary of ongoing games:\n{}", summary);
        }

        return summary;
    }
}
