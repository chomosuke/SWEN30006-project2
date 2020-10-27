package game.filterer;

import ch.aplu.jcardgame.Card;
import game.GameInfo;

import java.util.ArrayList;

public class TrumpFilterer implements IFilterer {

    @Override
    public ArrayList<Card> filter(ArrayList<Card> cards, GameInfo gameInfo) {
        ArrayList<Card> trumpCards = new ArrayList<>();

        for (Card card : cards) {
            if (card.getSuit() == gameInfo.getTrumpSuit())
                trumpCards.add(card);
        }
        return trumpCards;
    }
}
