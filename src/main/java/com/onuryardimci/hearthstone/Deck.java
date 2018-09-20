package com.onuryardimci.hearthstone;

import java.util.List;

/**
 * Created by onury.
 */
public class Deck {

    private final GameClass deckGameClass;
    private List<Card> cards;

    public Deck(GameClass deckGameClass) {
        this.deckGameClass = deckGameClass;
    }

    public List<Card> getCards() {
        return cards;
    }
}
