package com.onuryardimci.hearthstone;

import java.util.List;

/**
 * Created by onury.
 */
public class GameDeck {

    private final List<Card> cards;

    public GameDeck(List<Card> cards) {
        this.cards = shuffle(cards);
    }

    public List<Card> shuffle(List<Card> cards) {
        return null;
    }

    public List<Card> drawCards(int numberOfCards) {
        return null;
    }

}
