# Football Scoreboard Library

## Description
football-scoreboard-library is a simple Java library for managing live football game scores. It allows you to start 
games, update scores, finish games, and retrieve a summary of ongoing games.

## Features
- Start a new game with two teams.
- Update the score of an ongoing game.
- Finish a game, removing it from the scoreboard.
- Get a summary of all ongoing games, ordered by total score.

## Requirements
- Java 8 or higher
- Maven 3.x

## How to Use

1. **Add Dependency (for other projects)**:
   - If you want to use this library in another Maven project, add the following dependency in your `pom.xml`:
   ```xml
   <dependency>
       <groupId>com.scoreboard</groupId>
       <artifactId>FootballScoreboardLib</artifactId>
       <version>1.0-SNAPSHOT</version>
   </dependency> 
   ```
   - If you want to use this library in another Gradle project, add the following dependency in your `build.gradle`:
   ```groovy
   implementation 'com.scoreboard:FootballScoreboardLib:1.0-SNAPSHOT'
   ```

2. **Example usage:**:
   ```java
   FootballScoreboard scoreboard = new FootballScoreboard();
   scoreboard.startGame("Team A", "Team B");
   scoreboard.updateScore("Team A", "Team B", 1, 0);
   System.out.println(scoreboard.getSummary());
   scoreboard.finishGame("Team A", "Team B");
   ```

## How to Run the Application Locally

### Maven Commands

To run the project and perform various tasks like compilation, testing, and packaging, you can use the following Maven commands:

1. **Compile the project**: This will compile the source code of the project.
   ```bash
   mvn clean compile
   ```
2. **Run the tests**: This will run the unit tests.
   ```bash
   mvn test
   ```

3. **Package the application**: This will create a JAR file for the project.
   ```bash
   mvn package
   ```
4. **Run the application**: 
   After packaging the application, you can run it directly from your IDE or from the command line.

   - **Option 1: Run from IDE**
      - Open `Main.java` in your IDE (e.g., IntelliJ IDEA) and run the class directly.

   - **Option 2: Run the JAR**
      - After packaging the application, navigate to the `target` folder where the JAR file is created.
      - Run the JAR from the root project folder using the following command:
     ```bash
     java -cp target/football-scoreboard-library-1.0-SNAPSHOT-jar-with-dependencies.jar com.football.scoreboard.Main
     ```
     This command specifies the classpath (`-cp`) to the JAR file and the main class to be executed (`com.football.scoreboard.Main`).