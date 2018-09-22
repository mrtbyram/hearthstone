package com.onuryardimci.hearthstone;

public interface GameMediator {

    void playCard(Card card, GamePlayer gamePlayer);
    void endTurn(GamePlayer gamePlayer);
}
