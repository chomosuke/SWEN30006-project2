package game.filterer;

import ch.aplu.jcardgame.Card;
import game.GameInfo;

import java.util.ArrayList;

public class NothingFilterer implements IFilterer {

    @Override
    public ArrayList<Card> filter(ArrayList<Card> cards, GameInfo gameInfo) {
        return cards;
    }
}
