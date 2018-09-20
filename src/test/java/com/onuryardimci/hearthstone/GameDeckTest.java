package com.onuryardimci.hearthstone;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

/**
 * Created by onury.
 */
public class GameDeckTest {

    @Test
    public void should_be_initialized_with_a_count_of_initial_cards() {
        GameDeck deck = new GameDeck(Arrays.asList(new Card[20]));

        assertNotNull(deck);
    }

    @Test
    public void shuffle() throws Exception {

    }

    @Test
    public void drawCards() throws Exception {

    }

}