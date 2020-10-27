package game.filterer;

import ch.aplu.jcardgame.Card;
import game.GameInfo;
import game.Whist;

import java.util.ArrayList;

public class NaiveLegalFilterer implements IFilterer {
    @Override
    public ArrayList<Card> filter(ArrayList<Card> cards, GameInfo gameInfo) {
        if (gameInfo.getLeadSuit() == null) {
            // non played yet, player can play any card
            return cards;
        }

        ArrayList<Card> leadTrumpCards = new LeadFilterer().filter(cards, gameInfo);
        leadTrumpCards.addAll(new TrumpFilterer().filter(cards, gameInfo));

        if (leadTrumpCards.isEmpty())
            return cards;
        else
            return leadTrumpCards;
    }
}
