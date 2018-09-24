package com.onuryardimci.hearthstone;

/**
 * Created by onury.
 */
public class Card {

    private final int cost;
    private final int damage;

    public Card(int cost) {
        this.cost = cost;
        this.damage = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return cost == card.cost;
    }

    @Override
    public int hashCode() {
        return cost;
    }

    public int getDamage() {
        return damage;
    }

    public int getCost() {
        return cost;
    }
}
