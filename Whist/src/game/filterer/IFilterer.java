package game.filterer;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import game.GameInfo;

import java.util.ArrayList;

public interface IFilterer {
    // arraylist instead of hand because we don't want to remove card from hand when filtering
    // you are not meant to modify your input
    ArrayList<Card> filter(ArrayList<Card> cards, GameInfo gameInfo);
}
