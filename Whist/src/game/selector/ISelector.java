package game.selector;

import ch.aplu.jcardgame.Card;
import game.GameInfo;

import java.util.ArrayList;

public interface ISelector {
    // arraylist instead of hand because Filterer
    // you're not meant to modify your input
    Card select(ArrayList<Card> cards, GameInfo gameInfo);
}
