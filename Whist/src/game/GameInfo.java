package game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameInfo {
    private final Whist.Suit trumpSuit;
    public Whist.Suit getTrumpSuit() {
        return trumpSuit;
    }

    private final ArrayList<Card> played;
    public ArrayList<Card> getPlayed() {
        return new ArrayList<>(played);
    }

    // if non played yet return Null
    public Whist.Suit getLeadSuit() {
        return played.isEmpty() ? null : (Whist.Suit) played.get(0).getSuit();
    }

    public GameInfo(Whist.Suit trumpSuit, ArrayList<Card> played) {
        this.trumpSuit = trumpSuit;
        this.played = played;
    }
}
