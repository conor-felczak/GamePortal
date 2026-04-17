import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class MoneyGame extends CardGame {
    
    static boolean lose;
    static boolean win;
    static boolean draw;

    public boolean apocalypse = false;
    

    ClickableRectangle restartButton;
    int restartX = 255;
    int restartY = 400;
    int restartWidth = 100;
    int restartHeight = 35;

    ClickableRectangle devilButton;
    int devilX = 365;
    int devilY = 400;
    int devilWidth = 100;
    int devilHeight = 35;

    // the rectangles for the trolley problem
    ClickableRectangle trolleyTopButton;
    int trolleyTopX = 0;
    int trolleyTopY = 0;
    int trolleyTopWidth = 600;
    int trolleyTopHeight = 300;

    ClickableRectangle trolleyBottomButton;
    int trolleyBottomX = 0;
    int trolleyBottomY = 300;
    int trolleyBottomWidth = 600;
    int trolleyBottomHeight = 300;
    
    public static boolean gonTime = false;

    static boolean rock = false;
    static boolean paper = false;
    static boolean scissors = false;

    static public int crowdInterest = 5;
    
    MoneyComputer computerPlayer;

    public int playerMoney;
    public int computerMoney;


    // Trolley Problem Variables
    static public boolean trolleyTime;
    public Card bottomCard;
    Card[] topCards = new Card[2];

    public MoneyGame() {
        initializeGame();
        dealCards(6);
        //gameEnd();
        //trolleyProblem();
    }


    @Override
    public void switchTurns() {
        // TODO Auto-generated method stub
        super.switchTurns();
        
        if (playerOneTurn == true) {
            crowdInterest -= 1;
        }
        MoneyComputer.flipOneCard();
        System.out.println("switchd");
        Random trolleyRandom = new Random();
        int trolleyRandomInt = trolleyRandom.nextInt(11);
        System.out.println(trolleyRandomInt);
        if (trolleyRandomInt == 2) {
            System.out.println("trolley problem initiated");
            trolleyProblem();
        }
    }

    @Override
    public void drawCard(Hand hand) {
        System.out.println("Ha ha you can't draw cards");
    }

    @Override
    protected void createDeck() {
        String[] types = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
        int[] values = { 2, 3, 4, 5, 6, 7, 8, 9 , 10, 11, 12, 13, 14};

        for (int i = 0; i <= 12; i++) {
            deck.add(createCard(types[i], values[i]));
        }
    }


    public void gonMouseClicked(int mouseX, int mouseY) {
        if (gonStartButton.isClicked(mouseX, mouseY) && playerOneTurn && MoneyGame.lastPlayedCard != null) {
            rockPaperScissors();
            gonTime = true;
            System.out.println("gon");
        }
    }

    public void rockMouseClicked(int mouseX, int mouseY) {
        if (gonTime && rockButton.isClicked(mouseX, mouseY)) {
            rock = true;
            System.out.println("rock");
        }
    }

    public void scissorsMouseClicked(int mouseX, int mouseY) {
        if (gonTime && scissorsButton.isClicked(mouseX, mouseY)) {
            scissors = true;
            System.out.println("scissors");
        }
    }

    public void paperMouseClicked(int mouseX, int mouseY) {
        if (gonTime && paperButton.isClicked(mouseX, mouseY)) {
            paper = true;
            System.out.println("paper");
        }

    }

    public void trolleyTopClicked(int mouseX, int mouseY) {
        if (trolleyTime && trolleyTopButton.isClicked(mouseX, mouseY)) {
            killTopCards();
            System.out.println("killedTop");
        }
    }

    public void trolleyBottomClicked(int mouseX, int mouseY) {
        if (trolleyTime && trolleyBottomButton.isClicked(mouseX, mouseY)) {
            killBottomCards();
            System.out.println("killedBottom");
        }
    }

    public void devilClicked(int mouseX, int mouseY) {
        if (trolleyTime == false && devilButton.isClicked(mouseX, mouseY) && selectedCard != null) {
            playerOneHand.removeCard(selectedCard);
            deck.add(selectedCard);
            apocalypse = true;
            System.out.println("Devil");
        }
    }

    public void restartClicked(int mouseX, int mouseY) {
        if (win || lose || draw) {
            System.out.println("RAAAAA");
            reset();
        }
    }
    
    @Override
    protected void initializeGame() {

        devilButton = new ClickableRectangle();
        devilButton.x = devilX;
        devilButton.y = devilY;
        devilButton.width = devilWidth;
        devilButton.height = devilHeight;

        rockButton = new ClickableRectangle();
        rockButton.x = rockButtonX;
        rockButton.y = rockButtonY;
        rockButton.width = rockButtonWidth;
        rockButton.height = rockButtonHeight;

        paperButton = new ClickableRectangle();
        paperButton.x = paperButtonX;
        paperButton.y = paperButtonY;
        paperButton.width = paperButtonWidth;
        paperButton.height = paperButtonHeight;

        scissorsButton = new ClickableRectangle();
        scissorsButton.x = scissorsButtonX;
        scissorsButton.y = scissorsButtonY;
        scissorsButton.width = scissorsButtonWidth;
        scissorsButton.height = scissorsButtonHeight;
        
        gonStartButton = new ClickableRectangle();
        gonStartButton.x = gonStartX;
        gonStartButton.y = gonStartY;
        gonStartButton.width = gonStartWidth;
        gonStartButton.height = gonStartHeight;


        trolleyTopButton = new ClickableRectangle();
        trolleyTopButton.x = trolleyTopX;
        trolleyTopButton.y = trolleyTopY;
        trolleyTopButton.width = trolleyTopWidth;
        trolleyTopButton.height = trolleyTopHeight;
        
        trolleyBottomButton = new ClickableRectangle();
        trolleyBottomButton.x = trolleyBottomX;
        trolleyBottomButton.y = trolleyBottomY;
        trolleyBottomButton.width = trolleyBottomWidth;
        trolleyBottomButton.height = trolleyBottomHeight;

        restartButton = new ClickableRectangle();
        restartButton.x = restartX;
        restartButton.y = restartY;
        restartButton.width = restartWidth;
        restartButton.height = restartHeight;

        //super.initializeGame();
        computerPlayer = new MoneyComputer();
        super.initializeGame();

        //lastPlayedCard = deck.remove(0);
        //discardPile.add(lastPlayedCard);

    }


    private MoneyCard createCard(String type, int cost) {
        MoneyCard card = new MoneyCard(type, cost);
        card.type = type;
        card.cost = cost;
        return card;
    }

    // Look I get this is like actual baby code but I'm so tired I can't bother to code it better
    // function returns a value based on the computers randomly generated rock paper scissors value
    public int rockPaperScissors() {
        // System.out.println("Rocking paper!");
        int computerNumber = computerPlayer.initiateRockPaperScissors();
        System.out.println(computerNumber);

        // for the computer, 0 = rock, 1 = paper, 2 = scissors
        if (rock == true && computerNumber == 0) {
            // zero means that it's a draw
            gonTime = false;
            System.out.println("draw");
            return 0;
        }
        if (rock == true && computerNumber == 1) {
            // one means that computer wins 
            gonTime = false;
            crowdInterest -= 1;
            MoneyGame.lastPlayedCard.cost -= 1;
            System.out.println("Loser");
            return 1;
        }
        if (rock == true && computerNumber == 2) {
            // two means that you win
            gonTime = false;
            crowdInterest += 1;
            MoneyGame.lastPlayedCard.cost += 1;

            System.out.println("WINNER YIPPEE");
            return 2;
        }
        if (paper == true && computerNumber == 0) {
            gonTime = false;
            crowdInterest += 1;
            MoneyGame.lastPlayedCard.cost += 1;
            System.out.println("WINNER YIPPEE");
            return 2;
        }
        if (paper == true && computerNumber == 1) {
            gonTime = false;
            System.out.println("draw");
            return 0;
        }
        if (paper == true && computerNumber == 2) {
            gonTime = false;
            crowdInterest -= 1;
            MoneyGame.lastPlayedCard.cost -= 1;
            System.out.println("Loser");
            return 1;
        }
        if (scissors == true && computerNumber == 0) {
            gonTime = false;
            crowdInterest -= 1;
            MoneyGame.lastPlayedCard.cost -= 1;
            System.out.println("Loser");
            return 1;
        }
        if (scissors == true && computerNumber == 1) {
            gonTime = false;
            crowdInterest += 1;
            MoneyGame.lastPlayedCard.cost += 1;
            System.out.println("WINNER YIPPEE");
            return 2;
        }
        if (scissors == true && computerNumber == 2) {
            gonTime = false;
            System.out.println("draw");
            return 0;
        }

        return 3;

    }

    public void gameEnd() {
        gameActive = false;
        for (Card card: playerOneHand.getCards()) {
            playerMoney += card.cost;
        }

        for (Card card : playerTwoHand.getCards()) {
            computerMoney += card.cost;
        }


        if (playerMoney > computerMoney) {
            //System.out.println("You Win Capitalism!");
            win = true;
        }
        if (playerMoney < computerMoney) {
            //System.out.println("You Lose Capitalism");
            lose = true;
        }
        if (playerMoney == computerMoney) {
            //System.out.println("You Tied Capitalism?");
            draw = true;
        }
        //System.out.println(playerMoney);
        //System.out.println(computerMoney);
    }


    // functions for the trolley problem :)
    public void trolleyProblem() {
        ArrayList<Card> cards = playerOneHand.getCards();
        
        bottomCard = cards.get(0);
        topCards[0] = cards.get(1);
        topCards[1] = cards.get(2);

        trolleyTime = true;
    }

    void killTopCards() {
        for (Card card : topCards) {
            playerOneHand.removeCard(card);
            deck.add(card);
        }
        System.out.println("killedTopCards");
        trolleyTime = false;
        playerOneHand.positionCards(50, 450, 80, 120, 20);
    }

    void killBottomCards() {
        playerOneHand.removeCard(bottomCard);
        deck.add(bottomCard);
        System.out.println("killedBottomCards");
        trolleyTime = false;

        playerOneHand.positionCards(50, 450, 80, 120, 20);
    }

    @Override
    public boolean playCard(Card card, Hand hand) {
        // Check if card is valid to play
        if (!isValidPlay(card)) {
            System.out.println("Invalid play: " + card.value + " of " + card.suit);
            return false;
        }
        // Remove card from hand
        card.setTurned(false);
        // Add to discard pile
        lastPlayedCard = card;
        // Switch turns
        switchTurns();
        return true;
    }

    
    @Override
    public void drawChoices(PApplet sketch) {


        if (gonTime == true) {
            rockPaperScissors();
        }

        if (trolleyTime) {
            sketch.fill(0, 255, 0);
            trolleyTopButton.draw(sketch);
            trolleyBottomButton.draw(sketch);

            // sketch.fill(255, 0, 0);
            // sketch.rect(0, 0, 600, 600);

            sketch.strokeWeight(5);
            sketch.line(0, 300, 600, 550);
            sketch.line(0, 325, 600, 575);

            sketch.line(0, 300, 600, 150);
            sketch.line(0, 275, 600, 125);

            sketch.fill(0, 0, 255);
            bottomCard.x = 300;
            bottomCard.y = 400;
            bottomCard.draw(sketch);

            topCards[0].x = 300;
            topCards[1].x = 400;
            topCards[0].y = 125;
            topCards[1].y = 100;

            topCards[0].draw(sketch);
            topCards[1].draw(sketch);

        }

        if (trolleyTime == false) {
            // Draw rock paper scissors button
            sketch.fill(0, 255, 0);

            rockButton.draw(sketch);
            paperButton.draw(sketch);
            scissorsButton.draw(sketch);
            gonStartButton.draw(sketch);

            sketch.fill(0, 0, 0);
            sketch.text("Rock", rockButton.x + 35, rockButton.y + 10);
            sketch.text("Scissors", scissorsButton.x + 35, scissorsButton.y + 10);
            sketch.text("Paper", paperButton.x + 35, paperButton.y + 10);

            sketch.text("Play", gonStartButton.x + 35, gonStartButton.y + 10);

            sketch.text("Crowd Interest: " + String.valueOf(crowdInterest), 500, 50);

            sketch.fill(255, 0, 0);
            devilButton.draw(sketch);
            sketch.fill(0, 0, 0);
            sketch.text("devil", devilButton.x + 45, devilButton.y + 10);
        }

        if (apocalypse) {
            gameEnd();
        }

        if (gameActive == false) {
            if (lose) {
                sketch.fill(0, 0, 255);
                sketch.rect(0, 0, 600, 600);
                sketch.fill(255, 0, 0);
                sketch.textSize(50);
                sketch.text("Lose", 300, 250);
            }
            if (win) {
                sketch.fill(0, 0, 255);
                sketch.rect(0, 0, 600, 600);
                sketch.fill(255, 0, 0);
                sketch.textSize(50);
                sketch.text("Win", 300, 250);
            }
            if (draw) {
                sketch.fill(0, 0, 255);
                sketch.rect(0, 0, 600, 600);
                sketch.fill(255, 0, 0);
                sketch.textSize(50);
                sketch.text("Draw", 300, 250);
            }
            if (win || lose || draw) {
                sketch.fill(255, 255, 0);
                restartButton.draw(sketch);
                sketch.fill(0, 0, 0);
                sketch.textSize(25);
                sketch.text("Restart?", restartX + 50, restartY + 10);
            }
    }

    }

     void reset() {

        System.out.println("RESET");

        // for (Card card : playerOneHand.getCards()) {
        //     System.out.println("Hiya");
        //     discardPile.add(card);
        // }
        // for (Card card : playerTwoHand.getCards()) {
        //     discardPile.add(card);
        // }
        //System.out.println(playerOneHand.getCards());
        

        


        // I really don't know why it doesn't just remove every card, but for some reason if I run it thrice, it works, so we're just gonna keep it this way
    //     for (int i = 0; i <= playerOneHand.getSize(); i++) {
    //         playerOneHand.removeCard(playerOneHand.getCard(0));
    //         System.out.println("yes");
    //         deck.add(playerOneHand.getCard(0));
    //         System.out.println("no");
    //     }

    //     System.out.println(playerOneHand.getCards());

    //     //this is for player two

    //    for (int i = 0; i <= playerTwoHand.getSize(); i++) {
    //         playerTwoHand.removeCard(playerTwoHand.getCard(0));
    //         deck.add(playerTwoHand.getCard(0));
    //    }

    //    System.out.println(playerTwoHand.getCards());

    //     //checking for null :/
    //     for (int i = 0; i < deck.size(); i++) {
    //         if (deck.get(i) == null) {
    //             deck.remove(i);
    //         }
    //     }

    //     System.out.println("dealing cards");
        
    //     System.out.println(deck.size());

    //     //System.out.println(deck);
    //     debugDealCards(5);
        initializeGame();
        
        dealCards(6);
        crowdInterest = 5;
        gameActive = true;
        apocalypse = false;
        lose = false;
        draw = false;
        win = false;
        computerPlayer.num = 0;        
    }
}
