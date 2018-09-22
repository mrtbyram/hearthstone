package com.onuryardimci.hearthstone;

import org.junit.Test;

public class DeckTest {

    public static final Deck VALID_DECK;

    static {
        VALID_DECK = new Deck(GameClass.DRUID);
        Card cardToBeAssign = new Card(1);
        for (int i = 0; i < Deck.DECK_LIMIT; i++) {
            VALID_DECK.assignCard(cardToBeAssign);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void should_not_exceed_deck_limit() throws Exception {
        Deck deck = new Deck(GameClass.DRUID);

        Card cardToBeAssign = new Card(1);
        for (int i = 0; i < Deck.DECK_LIMIT + 1; i++) {
            deck.assignCard(cardToBeAssign);
        }
    }

}