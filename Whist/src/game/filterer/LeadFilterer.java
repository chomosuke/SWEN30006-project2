package game.filterer;

import ch.aplu.jcardgame.Card;
import game.GameInfo;

import java.util.ArrayList;

public class LeadFilterer implements IFilterer {
    // return only ones in the lead suit
    @Override
    public ArrayList<Card> filter(ArrayList<Card> cards, GameInfo gameInfo) {
        ArrayList<Card> leadCards = new ArrayList<>();

        for (Card card : cards) {
            if (card.getSuit() == gameInfo.getLeadSuit())
                leadCards.add(card);
        }
        return leadCards;
    }
}
