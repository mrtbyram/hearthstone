package com.onuryardimci.hearthstone;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by onury.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Mock
    private GamePlayerFactory gamePlayerFactory;

    @InjectMocks
    private Game sut;

    @org.junit.Before
    public void setUp() throws Exception {
        Player player1 = new Player(1, "onur");
        Player player2 = new Player(2, "yardimci");
        GamePlayer gamePlayer1 = Mockito.mock(GamePlayer.class);
        Mockito.when(gamePlayer1.getPlayer()).thenReturn(player1);
        GamePlayer gamePlayer2 = Mockito.mock(GamePlayer.class);
        Mockito.when(gamePlayer2.getPlayer()).thenReturn(player2);
        Mockito.when(gamePlayerFactory.create(Mockito.eq(player1), Mockito.any(GameDeck.class), Mockito.eq(sut))).thenReturn(gamePlayer1);
        Mockito.when(gamePlayerFactory.create(Mockito.eq(player2), Mockito.any(GameDeck.class), Mockito.eq(sut))).thenReturn(gamePlayer2);
        sut.assignPlayer(player1, DeckTest.VALID_DECK);
        sut.assignPlayer(player2, DeckTest.VALID_DECK);
    }

    @Test(expected = IllegalStateException.class)
    public void should_not_start_game_if_no_player_joined() {
        Game sut = new Game();
        sut.start();
    }

    @Test(expected = IllegalStateException.class)
    public void should_not_start_game_if_1_player_joined() {
        Game sut = new Game();
        Player player = new Player();
        sut.assignPlayer(player, DeckTest.VALID_DECK);

        sut.start();
    }

    @Test
    public void should_start_game_if_all_players_joined() {
        sut.start();

        Assert.assertEquals(GameState.ONGOING, sut.getState());
    }

    @Test(expected = IllegalStateException.class)
    public void should_not_assign_player_after_game_started() throws Exception {
        sut.start();
        sut.assignPlayer(new Player(), new Deck(GameClass.HUNTER));
    }

    @Test
    public void should_assign_a_player_as_active_after_game_started() {
        sut.start();

        Assert.assertNotNull(sut.getActivePlayer());
    }

    @Test
    public void should_not_allow_passive_player_to_play_card() {
        sut.start();
        expectedEx.expect(IllegalStateException.class);
        expectedEx.expectMessage("Only active players can play card.");
        Card card = new Card(1);
        GamePlayer passivePlayer = sut.getPassivePlayer();

        sut.playCard(card, passivePlayer);
    }

    @Test
    public void should_deal_damage_to_opponent_when_active_player_played_card() {
        sut.start();
        Card card = new Card(1);
        GamePlayer activePlayer = sut.getActivePlayer();
        GamePlayer passivePlayer = sut.getPassivePlayer();

        sut.playCard(card, activePlayer);

        Mockito.verify(passivePlayer, Mockito.times(1)).dealDamage(card.getDamage());
    }

    @Test
    public void should_not_allow_play_card_when_game_state_other_than_ongoing() {
        expectedEx.expect(IllegalStateException.class);
        expectedEx.expectMessage("Cards are playable only in ongoing state.");

        sut.playCard(new Card(0), new GamePlayer(new Player(), new GameDeck(DeckTest.VALID_DECK.getCards()), null));
    }

    @Test
    public void should_change_active_player_after_turn_ended() {
        sut.start();
        GamePlayer activePlayer = sut.getActivePlayer();
        GamePlayer passivePlayerBeforeEndTurn = sut.getPassivePlayer();

        sut.endTurn(activePlayer);

        Assert.assertEquals(sut.getActivePlayer(), passivePlayerBeforeEndTurn);
    }

    @Test
    public void should_notify_player_when_a_player_become_active() {
        sut.start();
        GamePlayer activePlayer = sut.getActivePlayer();
        GamePlayer passivePlayerBeforeEndTurn = sut.getPassivePlayer();

        sut.endTurn(activePlayer);

        Mockito.verify(passivePlayerBeforeEndTurn, Mockito.times(1)).notifyActivePlayer();
    }

    @Test
    public void should_not_allow_passive_player_to_end_turn() {
        sut.start();
        expectedEx.expect(IllegalStateException.class);
        expectedEx.expectMessage("Only active players can end turn.");

        sut.endTurn(sut.getPassivePlayer());
    }

    @Test
    public void should_not_allow_end_turn_when_game_state_other_than_ongoing() {
        expectedEx.expect(IllegalStateException.class);
        expectedEx.expectMessage("End turn is applicable only in ongoing state.");

        sut.endTurn(null);
    }

    @Test
    public void should_notify_all_players_as_game_ended_when_a_player_is_killed() {
        sut.start();
        sut.killPlayer(sut.getPassivePlayer());

        Mockito.verify(sut.getActivePlayer(), Mockito.times(1)).endGame();
        Mockito.verify(sut.getPassivePlayer(), Mockito.times(1)).endGame();
    }

}