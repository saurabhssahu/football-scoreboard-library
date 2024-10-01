package com.football.scoreboard;

public class Main {
    public static void main(String[] args) {
        FootballScoreboard footballScoreboard = new FootballScoreboard();

        // Start games
        footballScoreboard.startGame("Mexico", "Canada");
        footballScoreboard.startGame("Spain", "Brazil");
        footballScoreboard.startGame("Germany", "France");
        footballScoreboard.startGame("Uruguay", "Italy");

        // Update scores
        footballScoreboard.updateScore("Spain", "Brazil", 10, 2);
        footballScoreboard.updateScore("Mexico", "Canada", 0, 5);

        // Display footballScoreboard summary
        System.out.println("Current Scoreboard:");
        System.out.println(footballScoreboard.getSummary());

        // Start some other games
        footballScoreboard.startGame("Norway", "Denmark");
        footballScoreboard.startGame("Argentina", "Australia");

        // Update scores
        footballScoreboard.updateScore("Argentina", "Australia", 3, 1);
        footballScoreboard.updateScore("Germany", "France", 2, 2);
        footballScoreboard.updateScore("Uruguay", "Italy", 6, 6);

        // Finish a game
        footballScoreboard.finishGame("Norway", "Denmark");

        // Display scoreboard summary again
        System.out.println("\nAfter finishing Norway vs Denmark:");
        System.out.println(footballScoreboard.getSummary());
    }
}