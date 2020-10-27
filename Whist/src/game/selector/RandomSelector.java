package game.selector;

import ch.aplu.jcardgame.Card;
import game.GameInfo;
import game.Whist;

import java.util.ArrayList;

public class RandomSelector implements ISelector {
    @Override
    public Card select(ArrayList<Card> cards, GameInfo gameInfo) {
        return Whist.randomCard(cards);
    }
}
