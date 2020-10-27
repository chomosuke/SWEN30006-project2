package game;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

public class ActorAdder {
    // to prevent Whist being excessively accessed

    private final GameGrid gameGrid;
    public ActorAdder(GameGrid gameGrid) {
        this.gameGrid = gameGrid;
    }

    public void addActor(Actor actor, Location location) {
        gameGrid.addActor(actor, location);
    }
}
