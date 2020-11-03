package game.player;

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.TextActor;
import game.GameInfo;

import java.awt.*;

// a Player store it's score and hand and select a card
public abstract class Player {

    protected final CardGame cardGame;
    protected final int playerNum;
    public Player (CardGame cardGame, int playerNum) {
        this.cardGame = cardGame;
        this.playerNum = playerNum;
    }

    private int score = 0;
    public int getScore() {
        return score;
    }
    public void setScore(int value) {
        score = value;
    }

    private Hand hand;
    public Hand getHand() {
        return this.hand;
    }
    public void setHand(Hand value) {
        this.hand = value;
    }

    public abstract Card selectCard(GameInfo gameInfo);
}
