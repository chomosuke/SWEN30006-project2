package game.player;

import ch.aplu.jcardgame.Hand;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import game.ActorAdder;
import game.filterer.IFilterer;
import game.selector.ISelector;

public abstract class Player {
    private final ActorAdder actorAdder;
    public Player (ActorAdder actorAdder, Location handLocation, Location scoreLocation) {
        this.actorAdder = actorAdder;
    }

    private int score = 0;
    public int getScore() {
        return score;
    }
    public void setScore(int value) {
        score = value;
    }

    private Hand hand;
    public void setHand(Hand value) {
        this.hand = value;
    }
    protected Hand getHand() {
        return this.hand;
    }
}
