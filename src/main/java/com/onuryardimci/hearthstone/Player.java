package com.onuryardimci.hearthstone;

/**
 * Created by onury.
 */
public class Player {

    private long id;
    private String userName;

    public Player() {
    }

    public Player(long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
