package com.onuryardimci.hearthstone;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by onury.
 */
public class Game {

    public static final int COUNT_OF_REQUIRED_PLAYER = 2;

    private List<GamePlayer> players;
    private GameState state;
    private int turn = 0;
    private byte activePlayer;

    public Game() {
        this.players = new ArrayList<>();
        this.state = GameState.DRAFT;
        this.activePlayer = (byte) ThreadLocalRandom.current().nextInt(2);
    }

    public void assignPlayer(Player player, Deck selectedDeck) {
        if (GameState.DRAFT != state) {
            throw new IllegalStateException("Game should be in draft state to be able to assign a player.");
        }
//        players.add(new GamePlayer(player, new GameDeck(selectedDeck.getCards())));
    }

    public void start() {
        if (players == null || players.size() != COUNT_OF_REQUIRED_PLAYER) {
            throw new IllegalStateException("2 Players needed to be able to start a game!");
        }

        state = GameState.ONGOING;
    }

    public GameState getState() {
        return state;
    }
}
