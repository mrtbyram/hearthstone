package com.onuryardimci.hearthstone;

import java.util.Arrays;

public class GameSimulator {

    private static final int[] cards = {0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8};
    private static final int[] fatiqueCards = {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    public static void main(String... args) {
        Game game = new Game();
        Player player1 = new Player(1, "Anduin");
        Player player2 = new Player(2, "Malfurion");

        Deck deck1 = new Deck(GameClass.PRIEST);
        Arrays.stream(cards).forEach(card -> deck1.assignCard(new Card(card)));

        Deck deck2 = new Deck(GameClass.DRUID);
        Arrays.stream(cards).forEach(card -> deck2.assignCard(new Card(card)));

        game.assignPlayer(player1, deck1);
        game.assignPlayer(player2, deck2);

        game.start();

        String gameSummary = game.simulateGame();

        System.out.println(gameSummary);
    }
}
