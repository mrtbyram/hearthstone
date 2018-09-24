package com.onuryardimci.hearthstone;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by onury.
 */
public class Game implements GameMediator {

    public static final int COUNT_OF_REQUIRED_PLAYER = 2;

    private List<GamePlayer> players;
    private GameState state;
    private ActivePlayer activePlayer;
    private GamePlayerFactory gamePlayerFactory;
    private String summary;

    public Game() {
        this.gamePlayerFactory = new GamePlayerFactory();
        this.players = new ArrayList<>();
        this.state = GameState.DRAFT;
    }

    public void assignPlayer(Player player, Deck selectedDeck) {
        if (GameState.DRAFT != state) {
            throw new IllegalStateException("Game should be in draft state to be able to assign a player.");
        }
        players.add(gamePlayerFactory.create(player, new GameDeck(selectedDeck.getCards()), this));
    }

    public void start() {
        if (players == null || players.size() != COUNT_OF_REQUIRED_PLAYER) {
            throw new IllegalStateException("2 Players needed to be able to start a game!");
        }

        state = GameState.ONGOING;
        changeActivePlayer();
    }

    private void changeActivePlayer() {
        if (activePlayer == null) {
            this.activePlayer = new ActivePlayer(ThreadLocalRandom.current().nextInt(2));
        } else {
            this.activePlayer = activePlayer.changeActive();
        }
        getActivePlayer().notifyActivePlayer();
    }

    public GameState getState() {
        return state;
    }

    public GamePlayer getActivePlayer() {
        return players.get(activePlayer.getPlayerIndex());
    }

    public GamePlayer getPassivePlayer() {
        return findOpponentPlayer(getActivePlayer());
    }

    @Override
    public void playCard(Card card, GamePlayer gamePlayer) {
        if (!this.state.equals(GameState.ONGOING)) {
            throw new IllegalStateException("Cards are playable only in ongoing state.");
        }
        if (!getActivePlayer().equals(gamePlayer)) {
            throw new IllegalStateException("Only active players can play card.");
        }
        GamePlayer opponentPlayer = findOpponentPlayer(gamePlayer);
        opponentPlayer.dealDamage(card.getDamage());
    }

    private GamePlayer findOpponentPlayer(GamePlayer gamePlayer) {
        return players.stream()
                .filter(player -> !player.equals(gamePlayer))
                .findFirst()
                .get();
    }

    @Override
    public void endTurn(GamePlayer gamePlayer) {
        if (!this.state.equals(GameState.ONGOING)) {
            throw new IllegalStateException("End turn is applicable only in ongoing state.");
        }
        if (!getActivePlayer().equals(gamePlayer)) {
            throw new IllegalStateException("Only active players can end turn.");
        }
        changeActivePlayer();
    }

    @Override
    public void killPlayer(GamePlayer gamePlayer) {
        endGame();
        GamePlayer winner = findOpponentPlayer(gamePlayer);
        this.summary = String.format("Winner is %s remaining health: %d \nLoser is %s health: %d",
                winner.getPlayer().getUserName(), winner.getHealth(),
                gamePlayer.getPlayer().getUserName(), gamePlayer.getHealth());
    }

    private void endGame() {
        this.state = GameState.FINISHED;
        this.players.forEach(GamePlayer::endGame);
    }

    public void setGamePlayerFactory(GamePlayerFactory gamePlayerFactory) {
        this.gamePlayerFactory = gamePlayerFactory;
    }

    public String simulateGame() {
        do {
            getActivePlayer().simulatePlay();
        } while (!state.equals(GameState.FINISHED));
        return summary;
    }
}
