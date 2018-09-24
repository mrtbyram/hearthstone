package com.onuryardimci.hearthstone;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by onury.
 */
public class GamePlayer {

    public static final int INITIAL_HEALTH = 30;
    public static final int COUNT_OF_INITIAL_HAND = 3;

    private final Player player;
    private int health = INITIAL_HEALTH;
    private Mana mana = new Mana();
    private GameDeck gameDeck;
    private Hand hand;
    private GameMediator gameMediator;
    private Boolean gameRunning = Boolean.TRUE;

    public GamePlayer(Player player, GameDeck gameDeck, GameMediator gameMediator) {
        this.gameMediator = gameMediator;
        if (player == null) {
            throw new IllegalArgumentException("Player should be given.");
        }
        if (gameDeck == null) {
            throw new IllegalArgumentException("Game Deck should be given.");
        }
        this.player = player;
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
        this.health -= damage;
        if (health <= 0) {
            gameMediator.killPlayer(this);
        }
    }

    public void notifyActivePlayer() {
        mana.notifyNewTurn();
        drawCard();
    }

    private void drawCard() {
        Optional<Card> drawnCard = gameDeck.drawCard();
        if (drawnCard.isPresent()) {
            hand.addCard(drawnCard.get());
        } else {
            dealDamage(1);
        }
    }

    public void playCard(Card card) {
        if (!gameRunning) {
            return;
        }
        mana.playCard(card);
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

    public void simulatePlay() {
        //play some cards from hand.
        List<Card> cardsInHand = new ArrayList<>(getHand().getCards());
        cardsInHand.stream()
                .forEach(card -> {
                    if (card.getCost() <= getMana().getRemaining()) {
                        playCard(card);
                    }
                });
        endTurn();
    }

    public void endTurn() {
        if (!gameRunning) {
            return;
        }
        gameMediator.endTurn(this);
    }

    public Player getPlayer() {
        return player;
    }

    public void endGame() {
        this.gameRunning = Boolean.FALSE;
    }

    public Boolean getGameRunning() {
        return gameRunning;
    }
}
