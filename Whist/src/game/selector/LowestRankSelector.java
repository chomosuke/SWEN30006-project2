package game.selector;

import ch.aplu.jcardgame.Card;
import game.GameInfo;
import game.Whist;

import java.util.ArrayList;

public class LowestRankSelector implements ISelector {
    @Override
    public Card select(ArrayList<Card> cards, GameInfo gameInfo) {
        Card highestRank = null; // return null if cards is empty
        for (Card card : cards) {
            if (highestRank == null || Whist.rankGreater(highestRank, card))
                highestRank = card;
        }
        return highestRank;
    }
}
