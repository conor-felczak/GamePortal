import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import Game.ErrorCheck;

public class GamePortal {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Game> games = new ArrayList<Game>();
    public static void main(String[] args) {
        HashMap<String, Integer> gameCounts = new HashMap<String, Integer>();
        // writes highscores
        File f = new File("Highscore.csv");
        while (true) {
            loadGames();
            
            System.out.println("Which game would you like to play?");
            printGameChoices();
            Game g = getGameChoice();
            System.out.println("You're playing " + g.getGameName());

            g.play();
            g.writeHighScore(f);
            // add one to game counts the hashmap, if you wanted to store some stats.
            String gameKey = g.getGameName();
            if (gameCounts.containsKey(gameKey)) {
                gameCounts.put(gameKey, gameCounts.get(gameKey) + 1);
            } else {
                gameCounts.put(gameKey, 1);
            }
        }
    }

    public static void loadGames() {
        games.clear();
        games.add(new Quiz());
        games.add(new NumberGame(0, 100));
        games.add(new main());
    }

    public static void printGameChoices() {
        int n = 1;
        for (Game s : games) {
            System.out.println("[" + (n++) + "]: " + s.getGameName());
        }
    }

    /*
     * Takes in user input for printing out all games in
     */
    public static Game getGameChoice() {
        int choice = ErrorCheck.getInt(sc);
        // for it to be numbered, we can't use hashmaps.
        while (choice < 1 || choice > games.size()) {
            System.out.println("We don't have this number. Try again.");
            choice = ErrorCheck.getInt(sc);
        }

        // valid game choice
        return games.get(choice - 1);
    }
}
