package com.onuryardimci.hearthstone;

public class ActivePlayer {

    private byte playerIndex;

    public ActivePlayer(int playerIndex) {
        this.playerIndex = (byte) playerIndex;
    }

    public byte getPlayerIndex() {
        return playerIndex;
    }

    public ActivePlayer changeActive() {
        return new ActivePlayer((this.playerIndex + 1) % 2);
    }
}
