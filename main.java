import processing.core.PApplet;
import java.util.concurrent.CountDownLatch;

public class main extends PApplet implements GameWriteable {

    CountDownLatch latch;
    MoneyGame cardGame = new MoneyGame();
    private int timer;

    public static void main(String[] args) {
        PApplet.main("main");
    }
    @Override
    public void settings() {
        size(600, 600);
    }

    @Override
    public void draw() {
        
        background(255);
        // Draw player hands
        for (int i = 0; i < cardGame.playerOneHand.getSize(); i++) {
            Card card = cardGame.playerOneHand.getCard(i);
            if (card != null) {
                card.draw(this);
            }
        }
        // Draw computer hand
        for (int i = 0; i < cardGame.playerTwoHand.getSize(); i++) {
            Card card = cardGame.playerTwoHand.getCard(i);
            if (card != null) {
                card.draw(this);
            }
        }

        if (cardGame.crowdInterest == 0) {
            cardGame.gameEnd();
            latch.countDown();
        }

        if (cardGame.gonTime == false) {
            //System.out.println(MoneyGame.rockPaperScissors());
            MoneyGame.rock = false;
            MoneyGame.paper = false;
            MoneyGame.scissors = false;
        }
        
        // Draw draw button
        fill(200);
        // cardGame.drawButton.draw(this);

        

        // fill(0);
         textAlign(CENTER, CENTER);
        // text("Draw", cardGame.drawButton.x + cardGame.drawButton.width / 2, cardGame.drawButton.y + cardGame.drawButton.height / 2);

        // Display current player
        fill(0);
        textSize(16);
        text("Current Player: " + cardGame.getCurrentPlayer(), width / 2, 20);

        // Display deck size
        text("Deck Size: " + cardGame.getDeckSize(), width / 2,
                height - 20);
        // Display last played card
        if (cardGame.getLastPlayedCard() != null) {
            cardGame.getLastPlayedCard().setPosition(width / 2 - 40, height / 2 - 60, 80, 120);
            cardGame.getLastPlayedCard().draw(this);
        }
        if (cardGame.getCurrentPlayer() == "Player Two") {
            fill(0);
            textSize(16);
            text("Computer is thinking...", width / 2, height / 2 + 80);
            timer++;
            if (timer == 100) {
                cardGame.handleComputerTurn();
                timer = 0;
            }
        }

        cardGame.drawChoices(this);
    }

    
    @Override
    public void mousePressed() {
        if (MoneyGame.trolleyTime == false) {

            // cardGame.handleDrawButtonClick(mouseX, mouseY);
            cardGame.gonMouseClicked(mouseX, mouseY);
            cardGame.paperMouseClicked(mouseX, mouseY);
            cardGame.rockMouseClicked(mouseX, mouseY);
            cardGame.scissorsMouseClicked(mouseX, mouseY);
            cardGame.handleCardClick(mouseX, mouseY);
            cardGame.devilClicked(mouseX, mouseY);
        }

        cardGame.trolleyBottomClicked(mouseX, mouseY);
        cardGame.trolleyTopClicked(mouseX, mouseY);

        if (MoneyGame.win || MoneyGame.draw || MoneyGame.lose) {
            cardGame.restartClicked(mouseX, mouseY);
        }
    }





    @Override
    public String getGameName() {
        // TODO Auto-generated method stub
        return "card game";
    }

    @Override
    public void play() {
        // TODO Auto-generated method stub
        PApplet.runSketch(new String[]{"Game Name"}, this);

        latch = new CountDownLatch(1);
        try {
            latch.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }
    
    @Override
    public String getScore() {
        // TODO Auto-generated method stub
        return Integer.toString(cardGame.playerMoney);
    }

    @Override
    public boolean isHighScore(String score, String currentHighScore) {
        // TODO Auto-generated method stub
        score = Integer.toString(cardGame.playerMoney);

        if (currentHighScore != null) {
            if (Integer.parseInt(score) > Integer.parseInt(currentHighScore)) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return true;
        }
    }

}
