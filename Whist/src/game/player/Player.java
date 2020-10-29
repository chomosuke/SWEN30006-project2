package game.player;

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.TextActor;
import game.GameInfo;

import java.awt.*;

// a Player store it's score and hand and select a card
public abstract class Player {

    private Font bigFont = new Font("Serif", Font.BOLD, 36);
    protected final int thinkingTime = 2000;
    private final int handWidth = 400;

    protected final CardGame cardGame;
    private final Location handLocation;
    private final Location scoreLocation;
    protected final int playerNum;
    public Player (CardGame cardGame, Location handLocation, Location scoreLocation, int playerNum) {
        this.cardGame = cardGame;
        this.handLocation = handLocation;
        this.scoreLocation = scoreLocation;
        this.playerNum = playerNum;
        initScore();
    }

    private int score = 0;
    public int getScore() {
        return score;
    }
    public void setScore(int value) {
        score = value;
        cardGame.removeActor(scoreActor);
        scoreActor = new TextActor(String.valueOf(score), Color.WHITE, cardGame.bgColor, bigFont);
        cardGame.addActor(scoreActor, scoreLocation);
    }

    private TextActor scoreActor;
    private void initScore() {
        scoreActor = new TextActor("0", Color.WHITE, cardGame.bgColor, bigFont);
        cardGame.addActor(scoreActor, scoreLocation);
    }

    private Hand hand;
    protected Hand getHand() {
        return this.hand;
    }
    public void setHand(Hand value, Location trickLocation) {
        this.hand = value;

        hand.sort(Hand.SortType.SUITPRIORITY, true);

        // graphics
        RowLayout layout = new RowLayout(handLocation, handWidth);
        layout.setRotationAngle(90 * playerNum);
        // layouts[i].setStepDelay(10);
        hand.setView(cardGame, layout);
        hand.setTargetArea(new TargetArea(trickLocation));
        hand.draw();
    }

    public abstract Card selectCard(GameInfo gameInfo);
}
