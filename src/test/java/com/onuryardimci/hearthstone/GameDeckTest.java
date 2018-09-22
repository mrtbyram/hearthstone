package com.onuryardimci.hearthstone;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertNotNull;

/**
 * Created by onury.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameDeckTest {

    public static final GameDeck VALID_GAME_DECK = new GameDeck(Arrays.asList(new Card[GameDeck.REQUIRED_CARD_COUNT_TO_BE_INITIALIZED]));

    @Test(expected = IllegalArgumentException.class)
    public void should_not_be_initialized_without_cards() {
        GameDeck gameDeck = new GameDeck(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_be_initialized_if_valid_number_of_cards_are_not_provided() {
        GameDeck gameDeck = new GameDeck(Arrays.asList(new Card[GameDeck.REQUIRED_CARD_COUNT_TO_BE_INITIALIZED + 1]));
    }

    @Test
    public void should_be_initialized_with_a_count_of_initial_cards() {
        GameDeck gameDeck = VALID_GAME_DECK;

        assertNotNull(gameDeck);
    }

    @Test
    public void should_shuffle_cards_when_initialized() {
        List<Card> randomDeck = createRandomCardList();
        GameDeck gameDeck = new GameDeck(randomDeck);

        Assert.assertNotEquals(randomDeck, gameDeck.getCards());
    }

    public static List<Card> createRandomCardList() {
        List<Card> cards = new ArrayList<>(GameDeck.REQUIRED_CARD_COUNT_TO_BE_INITIALIZED);
        for (int i = 0; i < GameDeck.REQUIRED_CARD_COUNT_TO_BE_INITIALIZED; i++) {
            cards.add(new Card(ThreadLocalRandom.current().nextInt(8)));
        }
        return cards;
    }

    @Test
    public void drawCards() throws Exception {

    }

}