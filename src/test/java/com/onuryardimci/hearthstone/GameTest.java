package com.onuryardimci.hearthstone;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by onury.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameTest {

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @Test(expected = IllegalStateException.class)
    public void should_not_start_game_if_no_player_joined() {
        Game game = new Game();
        game.start();
    }

    @Test(expected = IllegalStateException.class)
    public void should_not_start_game_if_1_player_joined() {
        Game game = new Game();
        Player player = new Player();
        Deck deck = new Deck(GameClass.DRUID);
        game.assignPlayer(player, deck);

        game.start();
    }

    @Test
    public void should_start_game_if_all_players_joined() {
        Game game = new Game();
        Player player1 = new Player();
        Deck deck1 = new Deck(GameClass.DRUID);
        Player player2 = new Player();
        Deck deck2 = new Deck(GameClass.SHAMAN);
        game.assignPlayer(player1, deck1);
        game.assignPlayer(player2, deck2);

        game.start();

        Assert.assertEquals(GameState.ONGOING, game.getState());
    }

    @Test(expected = IllegalStateException.class)
    public void should_not_assign_player_after_game_started() throws Exception {
        Game game = new Game();
        Player player1 = new Player();
        Deck deck1 = new Deck(GameClass.DRUID);
        Player player2 = new Player();
        Deck deck2 = new Deck(GameClass.SHAMAN);
        game.assignPlayer(player1, deck1);
        game.assignPlayer(player2, deck2);

        game.start();
        game.assignPlayer(new Player(), new Deck(GameClass.HUNTER));
    }

}