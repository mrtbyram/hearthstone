package com.onuryardimci.hearthstone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

/**
 * Created by onury.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameDeckTest {

    private GameDeck sut;

    @Before
    public void setUp() throws Exception {
        int[] cards = {0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8};
        List<Card> cardList = Arrays.stream(cards).mapToObj(Card::new).collect(Collectors.toList());
        sut = new GameDeck(cardList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_be_initialized_without_cards() {
        GameDeck gameDeck = new GameDeck(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_be_initialized_if_valid_number_of_cards_are_not_provided() {
        GameDeck gameDeck = new GameDeck(Arrays.asList(new Card[GameDeck.REQUIRED_CARD_COUNT_TO_BE_INITIALIZED + 1]));
    }

    @Test
    public void should_shuffle_cards_when_initialized() {
        List<Card> randomDeck = createRandomCardList();
        GameDeck gameDeck = new GameDeck(randomDeck);

        Assert.assertNotEquals(randomDeck, gameDeck.getCards());
    }

    private List<Card> createRandomCardList() {
        List<Card> cards = new ArrayList<>(GameDeck.REQUIRED_CARD_COUNT_TO_BE_INITIALIZED);
        for (int i = 0; i < GameDeck.REQUIRED_CARD_COUNT_TO_BE_INITIALIZED; i++) {
            cards.add(new Card(ThreadLocalRandom.current().nextInt(8)));
        }
        return cards;
    }

    @Test
    public void should_pop_card_from_deck_when_card_drawn() {
        Optional<Card> card = sut.drawCard();

        Assert.assertEquals(Boolean.TRUE, card.isPresent());
    }

    @Test
    public void should_return_empty_card_when_deck_get_empty() {
        Optional<Card> card = Optional.empty();
        for (int i = 0; i < GameDeck.REQUIRED_CARD_COUNT_TO_BE_INITIALIZED + 1; i++) {
            card = sut.drawCard();
        }

        Assert.assertEquals(Boolean.FALSE, card.isPresent());
    }

}