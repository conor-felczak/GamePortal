
import java.util.Scanner;

import java.util.Random;
import java.io.File;
import java.util.ArrayList;


public class NumberGame implements GameWriteable {
    int guesses;
    int numToGuess;
    static Scanner sc = new Scanner(System.in);

    static ArrayList<Integer> prevGuesses = new ArrayList<>();
    

    static Random rn = new Random();


    NumberGame(int low, int high) {
        
        int rnrn = rn.nextInt(low, high);
        
        numToGuess = rnrn;
        // TODO: setup.
        System.out.println("I'm thinking of a number " + low + " to " + high);
        // when we create a game, we get a random number between low to high.
        // assign numToGuess to that random number.
    }

    @Override
    public void play() {

        int guess = getGuess();

        while (guess != numToGuess) {
            System.err.println("you guessed " + guess);
            
            guesses += 1;

            System.out.println(guesses);

            for (int g : prevGuesses) {
                if (guess == g) {
                    System.err.println("you already guessed that!");
                }  
            }

            if (guess < numToGuess) {
                System.err.println("My number is higher");
                prevGuesses.add(guess);
            }
            else if (guess > numToGuess) {
                System.err.println("My number is lower");
                prevGuesses.add(guess);
            }

            

            

            // if (guess < numToGuess) {
            //     System.err.println("My number is higher");
            // }
            // else if (guess > numToGuess) {
            //     System.err.println("My number is lower");
            // }
            guess = getGuess();
        }
        // you can remove these println statements, they are just here for you to
        // understand how we call this code.
        
        // gets the user guess by calling getGuess()
       
        // When user guesses incorrectly, says whether the number is higher or lower.

        // When user guesses correctly, finishes the game and tells them how many
        // guesses they had.
        System.out.println("Done playing!");
        prevGuesses.clear();

        
    }

    int getGuess() {
        // get user input from a Scanner.
        // bonus: go back to your BuzzFeedQuiz code and recurse to not allow invalid
        // input.
       // int guesser = sc.nextInt();

        

        return sc.nextInt();


    }

    int getNumGuesses() {
        return guesses;
    }

    @Override
    public String getGameName() {
        // TODO Auto-generated method stub
        return "Number Guess Game";
    }

    @Override
    public String getScore() {
        // TODO Auto-generated method stub
        return String.valueOf(getNumGuesses());
    }


    @Override
    public boolean isHighScore(String score, String currentHighScore) {
        // TODO Auto-generated method stub
        score = String.valueOf(guesses);

        if (currentHighScore == null) {
            return true;
        }

        if (currentHighScore != null) {
            if (Integer.parseInt(currentHighScore) > Integer.parseInt(score)) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
        
    }
}
