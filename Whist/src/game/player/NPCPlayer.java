package game.player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
import game.GameInfo;
import game.filterer.IFilterer;
import game.selector.ISelector;

public class NPCPlayer extends Player {
    private final ISelector selector;
    private final IFilterer filterer;

    public NPCPlayer (ISelector selector, IFilterer filterer,
                      GameGrid gameGrid, Location handLocation, Location scoreLocation, int playerNum) {
        super(gameGrid, handLocation, scoreLocation, playerNum);
        this.selector = selector;
        this.filterer = filterer;
    }

    @Override
    public Card selectCard(GameInfo gameInfo) {
        gameGrid.setStatusText("Player " + playerNum + " thinking...");
        GameGrid.delay(thinkingTime);
        return selector.select(
                filterer.filter(getHand().getCardList(), gameInfo),
                gameInfo);
    }
}
