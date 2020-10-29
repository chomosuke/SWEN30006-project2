package game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;
import ch.aplu.jcardgame.Hand;

import java.util.Random;

public class DeterministicDeck extends Deck {

    // for a fixed seeds dealing out
    public <T extends Enum<T>, R extends Enum<R>> DeterministicDeck(T[] suits, R[] ranks, String cover) {
        super(suits, ranks, cover);
    }

    public Hand[] dealingOutDeterministically(int nbPlayers, int nbCardsPerPlayer, Random random) {
        Hand[] hands = new Hand[nbPlayers + 1];

        // deal out from last one so last one will be have remaining cards
        hands[hands.length - 1] = toHand(false);
        Hand last = hands[hands.length - 1];

        for (int i = 0; i < nbPlayers; i++) {
            hands[i] = new Hand(this);
            for (int j = 0; j < nbCardsPerPlayer; j++) {
                int index = random.nextInt(last.getNumberOfCards());
                Card card = last.get(index);
                card.removeFromHand(false);
                hands[i].insert(card, false);
            }
        }
        return hands;
    }
}
