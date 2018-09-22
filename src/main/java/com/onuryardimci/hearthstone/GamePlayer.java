package com.onuryardimci.hearthstone;

/**
 * Created by onury.
 */
public class GamePlayer {

    public static final int INITIAL_HEALTH = 30;
    public static final int COUNT_OF_INITIAL_HAND = 3;

    private final Player player;
    private int health;
    private Mana mana;
    private GameDeck gameDeck;
    private Hand hand;
    private GameMediator gameMediator;

    public GamePlayer(Player player, GameDeck gameDeck, GameMediator gameMediator) {
        this.gameMediator = gameMediator;
        if (player == null) {
            throw new IllegalArgumentException("Player should be given.");
        }
        if (gameDeck == null) {
            throw new IllegalArgumentException("Game Deck should be given.");
        }
        this.player = player;
        this.health = INITIAL_HEALTH;
        this.mana = new Mana();
        this.gameDeck = gameDeck;
        this.hand = new Hand(this.gameDeck.drawCards(COUNT_OF_INITIAL_HAND));
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public int getHealth() {
        return health;
    }

    public void dealDamage(int damage) {
        this.health = this.health - damage;
    }

    public void notifyActivePlayer() {
        mana.notifyNewTurn();
        drawCard();
    }

    private void drawCard() {
        hand.addCard(gameDeck.drawCard());
    }

    public void playCard(Card card) {
        hand.playCard(card);
        gameMediator.playCard(card, this);
    }

    public GameDeck getGameDeck() {
        return gameDeck;
    }

    public Mana getMana() {
        return mana;
    }

    public GameMediator getGameMediator() {
        return gameMediator;
    }

}
