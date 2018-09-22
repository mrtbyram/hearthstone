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
}
