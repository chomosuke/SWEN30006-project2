package game.player;

import ch.aplu.jgamegrid.Location;
import game.ActorAdder;
import game.filterer.IFilterer;
import game.selector.ISelector;

public class NPCPlayer extends Player {
    private final ISelector selector;
    private final IFilterer filterer;

    public NPCPlayer (ISelector selector, IFilterer filterer,
                      ActorAdder actorAdder, Location handLocation, Location scoreLocation) {
        super(actorAdder, handLocation, scoreLocation);
        this.selector = selector;
        this.filterer = filterer;
    }
}
