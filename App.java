
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Task 0: Make the NumberGuessGame work in Game.java
// Optional: 
// Task 1: Get getNumGuesses() to work correctly for each Game, and call getBestGame() here in App.java
// Task 2: Be forgiving to players - tell them they already guessed a number if they have, and do not add it to numGuesses.

public class App {

    static HashMap<Integer, Integer> hg = new HashMap<>();
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to the Number Guess Game!");
        // See Game for pseudocode you need to fill out
        // Create a new Game that sets a number within the user's specified number range
        ArrayList<NumberGame> games = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("To play a game, press 'y'. If you want to stop playing, type anything other than 'y'.");
        while (sc.hasNext() && sc.next().equals("y")) {
            NumberGame g = new NumberGame(1, 50);
            g.play();
            games.add(g);
            System.out.println("press 'y' to play another game.");


            int val = g.getNumGuesses();

            if (hg.containsKey(g.getNumGuesses())) {
                hg.put(g.getNumGuesses(), hg.get(val) + 1);
            } else {
                hg.put(val, 1);
            }
            
            System.err.println(hg);

        }
        

    }

    public static void getBestGame(ArrayList<NumberGame> games) {
        // best game
        int minGame = 0;
        for (NumberGame g : games) {
            if (g.getNumGuesses() < minGame) {
                minGame = g.getNumGuesses();
            }
        }
        System.out.println("min game is: " + minGame);
    }
}
