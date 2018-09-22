package com.onuryardimci.hearthstone;

public class GamePlayerFactory {

    public GamePlayer create(Player player, GameDeck gameDeck, GameMediator gameMediator) {
        return new GamePlayer(player, gameDeck, gameMediator);
    }
}
