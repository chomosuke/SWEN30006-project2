package game.player;

import ch.aplu.jgamegrid.Location;
import game.ActorAdder;

public class InteractivePlayer extends Player {
    public InteractivePlayer(ActorAdder actorAdder, Location handLocation, Location scoreLocation) {
        super(actorAdder, handLocation, scoreLocation);
    }
}
