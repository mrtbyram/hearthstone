package com.onuryardimci.hearthstone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onury.
 */
public class Deck {

    public static final int DECK_LIMIT = 20;

    private final GameClass deckGameClass;
    private List<Card> cards = new ArrayList<>();

    public Deck(GameClass deckGameClass) {
        this.deckGameClass = deckGameClass;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void assignCard(Card card) {
        if (cards.size() == DECK_LIMIT) {
            throw new IllegalStateException(String.format("%s of cards are already satisfied.", DECK_LIMIT));
        }
        cards.add(card);
    }
}
