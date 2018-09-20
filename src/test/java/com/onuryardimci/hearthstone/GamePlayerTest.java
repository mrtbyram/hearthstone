package com.onuryardimci.hearthstone;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by onury.
 */
public class GamePlayerTest {

    @Test(expected = IllegalArgumentException.class)
    public void should_not_initialize_without_a_player() {
        GamePlayer gamePlayer = new GamePlayer(null, new GameDeck(Collections.emptyList()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_initialize_without_a_deck(){
        GamePlayer gamePlayer = new GamePlayer(new Player(), null);
    }

    public void should_have_initial_cards_in_hand_when_initialized(){
//        GamePlayer gamePlayer = new GamePlayer()
    }
}