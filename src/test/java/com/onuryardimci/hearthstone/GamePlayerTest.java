package com.onuryardimci.hearthstone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

/**
 * Created by onury.
 */
public class GamePlayerTest {

    public static final Player PRO_PLAYER = new Player(1, "dragonist");

    private GamePlayer sut;

    @Before
    public void setUp() throws Exception {
        GameDeck mockDeck = Mockito.mock(GameDeck.class);
        Game mockGame = Mockito.mock(Game.class);
        sut = new GamePlayer(PRO_PLAYER, mockDeck, mockGame);
        sut.setHand(Mockito.mock(Hand.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_initialize_without_a_player() {
        GamePlayer gamePlayer = new GamePlayer(null, new GameDeck(Collections.emptyList()), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_initialize_without_a_deck() {
        GamePlayer gamePlayer = new GamePlayer(PRO_PLAYER, null, null);
    }

    @Test
    public void should_have_initial_cards_in_hand_when_initialized() {
        Mockito.verify(sut.getGameDeck(), Mockito.times(1)).drawCards(GamePlayer.COUNT_OF_INITIAL_HAND);
    }

    @Test
    public void should_decrease_health_when_damage_dealt() {
        int healthBeforeDamageDeal = sut.getHealth();

        sut.dealDamage(5);

        Assert.assertEquals(healthBeforeDamageDeal - 5, sut.getHealth());
    }

    @Test
    public void should_increase_mana_by_one_when_player_gotten_active() {
        int manaBeforeActive = sut.getMana().getTotal();

        sut.notifyActivePlayer();

        Assert.assertEquals(manaBeforeActive + 1, sut.getMana().getTotal());
    }

    @Test
    public void should_refill_mana_when_player_gotten_active() {
        sut.notifyActivePlayer();
        sut.playCard(new Card(1));

        sut.notifyActivePlayer();

        Assert.assertEquals(2, sut.getMana().getTotal());
    }

    @Test
    public void should_not_be_increased_mana_more_than_ten() {
        for (int i = 0; i < 11; i++) {
            sut.notifyActivePlayer();
        }

        Assert.assertEquals(10, sut.getMana().getTotal());
    }

    @Test
    public void should_draw_card_when_player_gotten_active() {
        Mockito.when(sut.getGameDeck().drawCard()).thenReturn(new Card(1));

        sut.notifyActivePlayer();

        Mockito.verify(sut.getGameDeck(), Mockito.times(1)).drawCard();
        Mockito.verify(sut.getHand(), Mockito.times(1)).addCard(new Card(1));
    }

    @Test
    public void should_play_card_from_hand() {
        Card cardToBePlayed = new Card(1);

        sut.playCard(cardToBePlayed);

        Mockito.verify(sut.getHand(), Mockito.times(1)).playCard(cardToBePlayed);
        Mockito.verify(sut.getGameMediator(), Mockito.times(1)).playCard(cardToBePlayed, sut);
    }
}