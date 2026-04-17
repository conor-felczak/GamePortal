import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;

public class CardGame {
    // Core game components
    static ArrayList<Card> deck = new ArrayList<>();
    static public Hand playerOneHand;
    static public Hand playerTwoHand;
    static ArrayList<Card> discardPile = new ArrayList<>();
    static Card selectedCard;
    int selectedCardRaiseAmount = 15;

    // Game state
    static boolean playerOneTurn = true;
    static Card lastPlayedCard;
    static boolean gameActive;

    // UI
    // ClickableRectangle drawButton;
    // int drawButtonX = 250;
    // int drawButtonY = 400;
    // int drawButtonWidth = 100;
    // int drawButtonHeight = 35;


    // Shoulda put this stuff in the Money Game class but oh well it's here now I can't bother copy and pasting it
    static ClickableRectangle rockButton;
    int rockButtonX = 275;
    int rockButtonY = 500;
    int rockButtonWidth = 75;
    int rockButtonHeight = 35;

    static ClickableRectangle paperButton;
    int paperButtonX = 375;
    int paperButtonY = 500;
    int paperButtonWidth = 75;
    int paperButtonHeight = 35;

    static ClickableRectangle gonStartButton;
    static int gonStartX = 375;
    static int gonStartY = 450;
    static int gonStartWidth = 75;
    static int gonStartHeight = 35;

    static ClickableRectangle scissorsButton;
    int scissorsButtonX = 475;
    int scissorsButtonY = 500;
    int scissorsButtonWidth = 75;
    int scissorsButtonHeight = 35;

    public CardGame() {
        initializeGame();
        dealCards(6);
    }

    protected void initializeGame() {
        // Initialize draw button
        // drawButton = new ClickableRectangle();
        // drawButton.x = drawButtonX;
        // drawButton.y = drawButtonY;
        // drawButton.width = drawButtonWidth;
        // drawButton.height = drawButtonHeight;

        // Initialize decks and hands
        deck = new ArrayList<>();
        discardPile = new ArrayList<>();
        playerOneHand = new Hand();
        playerTwoHand = new Hand();
        gameActive = true;

        createDeck();
    }

    protected void createDeck() {
        // Create a standard deck of cards (for simplicity, using numbers and suits)
        String[] suits = { "Hearts", "Diamonds", "Clubs", "Spades" };
        String[] values = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
        for (String suit : suits) {
            for (String value : values) {
                deck.add(new Card(value, suit));
            }
        }
    }

    static protected void dealCards(int numCards) {
        Collections.shuffle(deck);
        for (int i = 0; i < numCards; i++) {
            playerOneHand.addCard(deck.remove(0));
            Card card = deck.remove(0);
            card.setTurned(true);
            playerTwoHand.addCard(card);
        }

        // position cards
        playerOneHand.positionCards(50, 450, 80, 120, 20);
        playerTwoHand.positionCards(50, 50, 80, 120, 20);
    }

    static void debugDealCards(int numCards) {
        Collections.shuffle(deck);
        for (int i = 0; i < numCards; i++) {
            Card oneCard = deck.remove(0);
            if (oneCard.isSelected()) {
                oneCard.selected = false;
            }
            oneCard.setTurned(false);
            playerOneHand.addCard(oneCard);
            Card card = deck.remove(0);
            card.setTurned(true);
            playerTwoHand.addCard(card);

            //System.out.println(playerOneHand.getCards());
            //System.out.println(playerTwoHand.getCards());
        }
        for (Card card : playerOneHand.getCards()) {
            card.setTurned(false);
        }

        // position cards
        playerOneHand.positionCards(50, 450, 80, 120, 20);
        playerTwoHand.positionCards(50, 50, 80, 120, 20);
    }

    protected boolean isValidPlay(Card card) {
        return true;
    }

    public void drawCard(Hand hand) {
        if (deck != null && !deck.isEmpty()) {
            hand.addCard(deck.remove(0));
        } else if (discardPile != null && discardPile.size() > 1) {
            // Reshuffle discard pile into deck if deck is empty
            lastPlayedCard = discardPile.remove(discardPile.size() - 1);
            deck.addAll(discardPile);
            discardPile.clear();
            discardPile.add(lastPlayedCard);
            Collections.shuffle(deck);

            if (!deck.isEmpty()) {
                hand.addCard(deck.remove(0));
            }
        }
    }

    // public void handleDrawButtonClick(int mouseX, int mouseY) {
    //     if (drawButton.isClicked(mouseX, mouseY) && playerOneTurn) {
    //         drawCard(playerOneHand);
    //         // Switch turns after drawing
    //         switchTurns();
    //     }
    // }

    

    public boolean playCard(Card card, Hand hand) {
        // Check if card is valid to play
        if (!isValidPlay(card)) {
            System.out.println("Invalid play: " + card.value + " of " + card.suit);
            return false;
        }
        // Remove card from hand
        hand.removeCard(card);
        card.setTurned(false);
        // Add to discard pile
        discardPile.add(card);
        lastPlayedCard = card;
        // Switch turns
        switchTurns();
        return true;
    }

    public void switchTurns() {
        playerOneTurn = !playerOneTurn;
        playerOneHand.positionCards(50, 450, 80, 120, 20);
        playerTwoHand.positionCards(50, 50, 80, 120, 20);
    }

    public String getCurrentPlayer() {
        return playerOneTurn ? "Player One" : "Player Two";
    }

    static public Card getLastPlayedCard() {
        return lastPlayedCard;
    }

    public int getDeckSize() {
        return deck != null ? deck.size() : 0;
    }

    public Hand getPlayerOneHand() {
        return playerOneHand;
    }

    static public Hand getPlayerTwoHand() {
        return playerTwoHand;
    }

    public void handleComputerTurn() {
        drawCard(playerTwoHand);
        switchTurns();
    }

    public void handleCardClick(int mouseX, int mouseY) {
        if (!playerOneTurn) {
            return;
        }
        Card clickedCard = getClickedCard(mouseX, mouseY);
        if (clickedCard == null) {
            return;
        }
        // this is for the first time
        if (selectedCard == null) {
            selectedCard = clickedCard;
            selectedCard.setSelected(true, selectedCardRaiseAmount);
            return;
        }

        if (selectedCard == clickedCard) {
            System.out.println("playing card: " + selectedCard.value + " of " + selectedCard.suit);
            if (playCard(selectedCard, playerOneHand)) {
                selectedCard.setSelected(false, selectedCardRaiseAmount);
                selectedCard = null;
            }
            return;
        }
        // change selection
        selectedCard.setSelected(false, selectedCardRaiseAmount);
        selectedCard = clickedCard;
        selectedCard.setSelected(true, selectedCardRaiseAmount);
    }

    // return the card that is clicked!
    public Card getClickedCard(int mouseX, int mouseY) {
        for (int i = playerOneHand.getSize() - 1; i >= 0; i--) {
            Card card = playerOneHand.getCard(i);
            if (card != null && card.isClicked(mouseX, mouseY)) {
                return card;
            }
        }
        return null;
    }

    public void drawChoices(PApplet app) {
        
        // this method is available for overriding
        // if you want to draw additional things (like Uno's wild color choices)
    }
}
