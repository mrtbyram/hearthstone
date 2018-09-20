package com.onuryardimci.hearthstone;

/**
 * Created by onury.
 */
public class GamePlayer {

    public static final int INITIAL_HEALTH = 30;
    public static final int INITIAL_MANA = 0;
    public static final int COUNT_OF_INITIAL_HAND = 5;

//    private final Player player;
//    private int health;
//    private int mana;
//    private final GameDeck gameDeck;
//    private final Hand hand;

    public GamePlayer(Player player, GameDeck gameDeck) {
        if(player == null){
            throw new IllegalArgumentException("Player should be given.");
        }

        if(gameDeck == null){
            throw new IllegalArgumentException("Game Deck should be given.");
        }
//        this.player = player;
//        this.health = INITIAL_HEALTH;
//        this.mana = INITIAL_MANA;
//        this.gameDeck = gameDeck;
//        this.hand = new Hand(gameDeck.drawCards(COUNT_OF_INITIAL_HAND));
    }

}
