import processing.core.PApplet;
import java.util.Random;
import java.util.ArrayList;

public class MoneyComputer {

    static int num = 0;

    Random gonNumber = new Random();
    

    public  int initiateRockPaperScissors() {
        int gonRandom = gonNumber.nextInt(3);
        //System.out.println(gonRandom);
        return gonRandom;
    }

    static public void flipOneCard() {
        
        if (num < MoneyGame.playerTwoHand.getSize()) {
            Hand hand = MoneyGame.getPlayerTwoHand();
            ArrayList<Card> cards = hand.getCards();
            cards.get(num).setTurned(false);
        }
        num += 1;
    }

}
