package com.onuryardimci.hearthstone;

import java.util.List;

/**
 * Created by onury.
 */
public class Hand {

    public static final int CARD_LIMIT = 5;

    private List<Card> cards;

    public Hand(List<Card> initialCards) {
        if (initialCards == null) {
            throw new IllegalArgumentException("Card list should be provided.");
        }
        this.cards = initialCards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        if (cards.size() == CARD_LIMIT) {
            return;
        }
        this.cards.add(card);
    }

    public void playCard(Card cardToBePlayed) {
        if (cardToBePlayed == null) {
            return;
        }
        if (!cards.contains(cardToBePlayed)) {
            throw new IllegalArgumentException("Card to be played does not exist in hand.");
        }
        cards.remove(cardToBePlayed);
    }
}
