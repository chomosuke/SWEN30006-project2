package game.player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.CardListener;
import ch.aplu.jcardgame.Hand;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
import game.GameInfo;

import static ch.aplu.jgamegrid.GameGrid.delay;

public class InteractivePlayer extends Player {
    public InteractivePlayer(GameGrid gameGrid, Location scoreLocation, int playerNum) {
        super(gameGrid, scoreLocation, playerNum);
    }

    private Card selected;

    @Override
    public void setHand(Hand value) {
        super.setHand(value);
        CardListener cardListener = new CardAdapter()  // Human Player plays card
        {
            public void leftDoubleClicked(Card card) {
                selected = card;
            }
        };
        value.addCardListener(cardListener);
    }

    @Override
    public Card selectCard(GameInfo gameInfo) {
        selected = null;
        getHand().setTouchEnabled(true); // enable touch for this human player in this round
        gameGrid.setStatusText("Player " + playerNum + " double-click on card to lead."); //status
        while (null == selected) delay(100);
        getHand().setTouchEnabled(false);
        return selected;
    }
}
