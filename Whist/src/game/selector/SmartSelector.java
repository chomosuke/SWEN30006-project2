package game.selector;


import ch.aplu.jcardgame.Card;
import game.GameInfo;
import game.Whist;
import game.filterer.LeadFilterer;
import game.filterer.TrumpFilterer;

import java.util.ArrayList;

public class SmartSelector implements ISelector {

    @Override
    public Card select(ArrayList<Card> cards, GameInfo gameInfo) {

        // check if player is taking the lead
        if (gameInfo.getPlayed().isEmpty()) {
            // chose highest rank to maximize winning chances
            return new HighestRankSelector().select(cards, gameInfo);
        }

        Card chosen;

        // see whether it can win or not
        
        ArrayList<Card> leadCards = new LeadFilterer().filter(cards, gameInfo);
        chosen = new HighestRankSelector().select(leadCards, gameInfo);

        if (chosen == null) { // means HighestRankSelector had empty hand to select from
            ArrayList<Card> trumpCards = new TrumpFilterer().filter(cards, gameInfo);
            chosen = new HighestRankSelector().select(trumpCards, gameInfo);
        }

        if (chosen == null) { // no trump or lead card available
            return new LowestRankSelector().select(cards, gameInfo);
        }

        // check if chosen becomes winning card
        ArrayList<Card> trick = gameInfo.getPlayed();
        trick.add(chosen);
        if (chosen == Whist.winningCard(new GameInfo(gameInfo.getTrumpSuit(), trick))) {
            return chosen; // as chosen might win
        } else {
            // chosen can't win
            return new LowestRankSelector().select(cards, gameInfo);
        }
    }
}
