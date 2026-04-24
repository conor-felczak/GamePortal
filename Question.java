/* Irene Feng 10/12/2022
A question class with Answers.
*/ 
import java.util.Scanner;

public class Question {
    // Fields
    String label;
    Answer[] possibleAnswers = new Answer[4];

    Question(String label) {
        this.label = label;
    }

    // ask a question, and return the category that corresponds to the answer
    Category ask(Scanner sc) {
        System.out.println(this.label);
        // prints out all the answer choices
        for (int i = 0; i < this.possibleAnswers.length; i++) {
            String choice = Integer.toString(i + 1);
            System.out.println("[" + choice + "]:" +
                    this.possibleAnswers[i].label);
        }

        
        if (sc.hasNextInt() != true) {
            System.err.println("NO LETTERS AAAGHH THE PAIN RAAAH");
            sc.next();
            return this.ask(sc);
        }

        int ans = sc.nextInt();

        if (ans == 1 || ans == 2 || ans == 3 || ans == 4) {
            return possibleAnswers[ans - 1].cat;
        }
        else {
            System.err.println("put a viable answer pls :(");
            return this.ask(sc);
        }
        
    }

}
