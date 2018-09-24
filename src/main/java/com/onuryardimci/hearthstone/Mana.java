package com.onuryardimci.hearthstone;

public class Mana {

    private static final int MANA_LIMIT = 10;

    private int total;
    private int remaining;

    public void notifyNewTurn() {
        if (total < MANA_LIMIT) {
            total++;
        }
        remaining = total;
    }

    public int getTotal() {
        return total;
    }

    public void playCard(Card card) {
        if (remaining < card.getCost()) {
            throw new IllegalArgumentException("Not enough mana.");
        }
        remaining -= card.getCost();
    }

    public int getRemaining() {
        return remaining;
    }
}
