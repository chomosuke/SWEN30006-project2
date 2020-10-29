package game.player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.TextActor;
import game.GameInfo;
import org.w3c.dom.Text;

import java.awt.*;

// a Player store it's score and hand and select a card
public abstract class Player {

    private Font bigFont = new Font("Serif", Font.BOLD, 36);
    protected final int thinkingTime = 2000;

    protected final GameGrid gameGrid;
    protected final int playerNum;
    private final Location scoreLocation;
    public Player (GameGrid gameGrid, Location scoreLocation, int playerNum) {
        this.gameGrid = gameGrid;
        this.playerNum = playerNum;
        this.scoreLocation = scoreLocation;
        initScore();
    }

    private int score = 0;
    public int getScore() {
        return score;
    }
    public void setScore(int value) {
        score = value;
        gameGrid.removeActor(scoreActor);
        scoreActor = new TextActor(String.valueOf(score), Color.WHITE, gameGrid.bgColor, bigFont);
        gameGrid.addActor(scoreActor, scoreLocation);
    }

    private TextActor scoreActor;
    private void initScore() {
        scoreActor = new TextActor("0", Color.WHITE, gameGrid.bgColor, bigFont);
        gameGrid.addActor(scoreActor, scoreLocation);
    }

    private Hand hand;
    public void setHand(Hand value) {
        this.hand = value;
    }
    protected Hand getHand() {
        return this.hand;
    }

    public abstract Card selectCard(GameInfo gameInfo);
}
