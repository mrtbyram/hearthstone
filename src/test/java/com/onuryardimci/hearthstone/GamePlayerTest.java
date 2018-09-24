package com.onuryardimci.hearthstone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

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
        Mockito.when(sut.getGameDeck().drawCard()).thenReturn(Optional.of(new Card(1)));

        sut.notifyActivePlayer();

        Mockito.verify(sut.getGameDeck(), Mockito.times(1)).drawCard();
        Mockito.verify(sut.getHand(), Mockito.times(1)).addCard(new Card(1));
    }

    @Test
    public void should_play_card_from_hand() {
        Card cardToBePlayed = new Card(1);
        sut.notifyActivePlayer();

        sut.playCard(cardToBePlayed);

        Mockito.verify(sut.getHand(), Mockito.times(1)).playCard(cardToBePlayed);
        Mockito.verify(sut.getGameMediator(), Mockito.times(1)).playCard(cardToBePlayed, sut);
    }

    @Test
    public void should_kill_the_player_when_a_player_health_drop_to_zero_or_less() {
        sut.dealDamage(GamePlayer.INITIAL_HEALTH + 1);

        Mockito.verify(sut.getGameMediator(), Mockito.times(1)).killPlayer(sut);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_play_card_if_sufficient_mana_does_not_exist() {
        Card cardToBePlayed = new Card(8);

        sut.playCard(cardToBePlayed);
    }

    @Test
    public void should_decrease_mana_as_played_card_cost() {
        Card cardToBePlayed = new Card(1);
        sut.notifyActivePlayer();
        int totalManaBeforePlay = sut.getMana().getTotal();

        sut.playCard(cardToBePlayed);

        Assert.assertEquals(totalManaBeforePlay - cardToBePlayed.getCost(), sut.getMana().getRemaining());
    }

    @Test
    public void should_take_damage_when_draw_card_from_empty_game_deck() {
        Mockito.when(sut.getGameDeck().drawCard()).thenReturn(Optional.empty());
        int healthBeforeCardDraw = sut.getHealth();

        sut.notifyActivePlayer();

        Assert.assertEquals(healthBeforeCardDraw - 1, sut.getHealth());
    }

    @Test
    public void should_not_end_turn_while_game_is_not_running() {
        sut.endGame();
        sut.endTurn();

        Mockito.verify(sut.getGameMediator(), Mockito.times(0)).endTurn(sut);
    }

    @Test
    public void should_not_play_card_while_game_is_not_running() {
        sut.endGame();
        sut.playCard(null);

        Mockito.verify(sut.getGameMediator(), Mockito.times(0)).playCard(null, sut);
    }

    @Test
    public void should_end_turn_when_no_more_available_play_left() {
        sut.endTurn();

        Mockito.verify(sut.getGameMediator(), Mockito.times(1)).endTurn(sut);
    }

    @Test
    public void should_end_game(){
        sut.endGame();

        Assert.assertEquals(Boolean.FALSE, sut.getGameRunning());
    }
}