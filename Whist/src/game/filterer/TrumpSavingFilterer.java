package game.filterer;

import ch.aplu.jcardgame.Card;
import game.GameInfo;

import java.util.ArrayList;

public class TrumpSavingFilterer implements IFilterer {
    @Override
    public ArrayList<Card> filter(ArrayList<Card> cards, GameInfo gameInfo) {
        if (gameInfo.getLeadSuit() == null) {
            // non played yet, player can play any card
            return cards;
        }

        ArrayList<Card> leadCards = new LeadFilterer().filter(cards, gameInfo);
        if (leadCards.isEmpty()) {
            ArrayList<Card> trumpCards = new TrumpFilterer().filter(cards, gameInfo);
            if (trumpCards.isEmpty())
                return cards;
            else
                return trumpCards;
        } else {
            return leadCards;
        }
    }
}
