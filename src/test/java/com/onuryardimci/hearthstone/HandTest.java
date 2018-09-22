package com.onuryardimci.hearthstone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HandTest {

    private Hand sut;

    @Before
    public void setUp() throws Exception {
        sut = new Hand(createRandomCardList());
    }

    private List<Card> createRandomCardList() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < GamePlayer.COUNT_OF_INITIAL_HAND; i++) {
            cards.add(new Card(ThreadLocalRandom.current().nextInt(8)));
        }
        return cards;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_initialize_with_nonnull_card_list() {
        sut = new Hand(null);
    }

    @Test
    public void should_add_card_to_hand() {
        Card cardToBeAdded = new Card(1);

        sut.addCard(cardToBeAdded);

        Assert.assertEquals(GamePlayer.COUNT_OF_INITIAL_HAND + 1, sut.getCards().size());
    }

    @Test
    public void should_discard_card_when_card_limit_is_reached() {
        Card cardToBeAdded = new Card(1);

        sut.addCard(cardToBeAdded);
        sut.addCard(cardToBeAdded);
        sut.addCard(cardToBeAdded);

        Assert.assertEquals(Hand.CARD_LIMIT, sut.getCards().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_play_a_nonexistent_card() {
        List<Card> cards = new ArrayList<>();
        Card cardToBeAdded = new Card(1);
        for (int i = 0; i < GamePlayer.COUNT_OF_INITIAL_HAND; i++) {
            cards.add(cardToBeAdded);
        }
        sut = new Hand(cards);

        sut.playCard(new Card(2));
    }

    @Test
    public void should_remove_card_from_hand_after_card_played() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < GamePlayer.COUNT_OF_INITIAL_HAND; i++) {
            cards.add(new Card(i));
        }
        sut = new Hand(cards);
        Card cardToBePlayed = new Card(1);

        sut.playCard(cardToBePlayed);

        Assert.assertEquals(Boolean.FALSE, sut.getCards().contains(cardToBePlayed));
        Assert.assertEquals(2, sut.getCards().size());
    }

}