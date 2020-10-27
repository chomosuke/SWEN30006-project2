package game;

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import game.filterer.IFilterer;
import game.player.InteractivePlayer;
import game.player.NPCPlayer;
import game.player.Player;
import game.selector.ISelector;

import java.util.*;
//import java.util.concurrent.ThreadLocalRandom;


import java.io.FileReader; //new
import java.io.IOException; //new



@SuppressWarnings("serial")
public class Whist extends CardGame {

    private static int Seed; //Fixed Seed Number
    private static int nbPlayers; //number of players
    private static int nbStartCards; //number of Stating Cards
    private static int winningScore; //Score Required to Win the game
    private static final Random random = new Random(Seed); //Random Using Seed
    private static Player[] players;

    public enum Suit {
        SPADES, HEARTS, DIAMONDS, CLUBS
    }

    public enum Rank {
        // Reverse order of rank importance (see rankGreater() below)
        // Order of cards is tied to card images
        ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
    }

    private final String[] trumpImage = {"bigspade.gif", "bigheart.gif", "bigdiamond.gif", "bigclub.gif"};

    // return random Enum value
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    // return random Card from Hand
    public static Card randomCard(Hand hand) {
        int x = random.nextInt(hand.getNumberOfCards());
        return hand.get(x);
    }

    // return random Card from ArrayList
    public static Card randomCard(ArrayList<Card> list) {
        int x = random.nextInt(list.size());
        return list.get(x);
    }

    public static boolean rankGreater(Card card1, Card card2) {
        return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
    }

    private final String version = "1.0";
    private final int trickWidth = 40;
    private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
    private final Location[] handLocations = {
            new Location(350, 625),
            new Location(75, 350),
            new Location(350, 75),
            new Location(625, 350)
    };
    private final Location[] scoreLocations = {
            new Location(575, 675),
            new Location(25, 575),
            new Location(575, 25),
            new Location(650, 575)
    };
    private final Location trickLocation = new Location(350, 350);
    private final Location textLocation = new Location(350, 450);
    private Location hideLocation = new Location(-500, -500);
    private Location trumpsActorLocation = new Location(50, 50);
    private boolean enforceRules = false;

    private Card selected;

    private void initRound() {
        Hand[] hands = deck.dealingOut(nbPlayers, nbStartCards); // Last element of hands is leftover cards; these are ignored
        for (int i = 0; i < nbPlayers; i++) {
            players[i].setHand(hands[i], trickLocation);
        }

//	    for (int i = 1; i < nbPlayers; i++)  // This code can be used to visually hide the cards in a hand (make them face down)
//	      hands[i].setVerso(true);
        // End graphics
    }

    private String printHand(ArrayList<Card> cards) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            out.append(cards.get(i).toString());
            if (i < cards.size() - 1) out.append(",");
        }
        return (out.toString());
    }

    private Optional<Integer> playRound() {  // Returns winner, if any
        // Select and display trump suit
        final Suit trumps = randomEnum(Suit.class);
        final Actor trumpsActor = new Actor("sprites/" + trumpImage[trumps.ordinal()]);
        addActor(trumpsActor, trumpsActorLocation);
        // End trump suit
        Hand trick;
        int winner;
        Card winningCard;
        Suit lead;
        int nextPlayer = random.nextInt(nbPlayers); // randomly select player to lead for this round
        for (int i = 0; i < nbStartCards; i++) {
            trick = new Hand(deck);

            selected = players[nextPlayer].selectCard(new GameInfo(trumps, trick.getCardList()));

            // Lead with selected card
            trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards() + 2) * trickWidth));
            trick.draw();
            selected.setVerso(false);
            // No restrictions on the card being lead
            lead = (Suit) selected.getSuit();
            selected.transfer(trick, true); // transfer to trick (includes graphic effect)
            winner = nextPlayer;
            winningCard = selected;
            System.out.println("New trick: Lead Player = " + nextPlayer + ", Lead suit = " + selected.getSuit() + ", Trump suit = " + trumps);
            System.out.println("Player " + nextPlayer + " play: " + selected.toString() + " from [" + printHand(players[nextPlayer].getHand().getCardList()) + "]");
            // End Lead
            for (int j = 1; j < nbPlayers; j++) {
                if (++nextPlayer >= nbPlayers) nextPlayer = 0;  // From last back to first
                selected = players[nextPlayer].selectCard(new GameInfo(trumps, trick.getCardList()));

                // Follow with selected card
                trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards() + 2) * trickWidth));
                trick.draw();
                selected.setVerso(false);  // In case it is upside down
                // Check: Following card must follow suit if possible
                if (selected.getSuit() != lead && players[nextPlayer].getHand().getNumberOfCardsWithSuit(lead) > 0) {
                    // Rule violation
                    String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
                    //System.out.println(violation);
                    if (enforceRules)
                        try {
                            throw (new BrokeRuleException(violation));
                        } catch (BrokeRuleException e) {
                            e.printStackTrace();
                            System.out.println("A cheating player spoiled the game!");
                            System.exit(0);
                        }
                }
                // End Check
                selected.transfer(trick, true); // transfer to trick (includes graphic effect)
                System.out.println("Winning card: " + winningCard.toString());
                System.out.println("Player " + nextPlayer + " play: " + selected.toString() + " from [" + printHand(players[nextPlayer].getHand().getCardList()) + "]");
                if ( // beat current winner with higher card
                        (selected.getSuit() == winningCard.getSuit() && rankGreater(selected, winningCard)) ||
                                // trumped when non-trump was winning
                                (selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
                    winner = nextPlayer;
                    winningCard = selected;
                }
                // End Follow
            }
            delay(600);
            trick.setView(this, new RowLayout(hideLocation, 0));
            trick.draw();
            nextPlayer = winner;
            System.out.println("Winner: " + winner);
            setStatusText("Player " + nextPlayer + " wins trick.");
            players[nextPlayer].setScore(players[nextPlayer].getScore() + 1);
            if (winningScore == players[nextPlayer].getScore()) return Optional.of(nextPlayer);
        }
        removeActor(trumpsActor);
        return Optional.empty();
    }

    public static Card winningCard(GameInfo gameInfo) {
        ArrayList<Card> trick = gameInfo.getPlayed();
        if (trick.isEmpty())
            return null;
        // assume first card is lead
        Card winningCard = trick.get(0);
        for (int i = 1; i < trick.size(); i++) {
            if (// beat current winner with higher card
                (trick.get(i).getSuit() == winningCard.getSuit() && rankGreater(trick.get(i), winningCard)) ||
                // trumped when non-trump was winning
                (trick.get(i).getSuit() == gameInfo.getTrumpSuit() && winningCard.getSuit() != gameInfo.getTrumpSuit()))
                winningCard = trick.get(i);
        }
        return winningCard;
    }

    public Whist() {
        super(700, 700, 30);

        /** Load properties for whist game based on either default or a properties file.**/
        setUpProperties();

        setTitle("Whist (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
        setStatusText("Initializing...");
        Optional<Integer> winner;
        do {
            initRound();
            winner = playRound();
        } while (!winner.isPresent());
        addActor(new Actor("sprites/gameover.gif"), textLocation);
        setStatusText("Game over. Winner is player: " + winner.get());
        refresh();
    }

    public static void main(String[] args) {
        new Whist();
    }

    //Allows Custom Properties (NEW)
    private void setUpProperties() {
        Properties properties = new Properties();

        // Read properties
        try (FileReader inStream = new FileReader("whist.properties")) {
            properties.load(inStream);
        } catch (IOException e) {
            throw new RuntimeException("error reading whist.properties");
        }

        // seed
        Seed = Integer.parseInt(properties.getProperty("Seed"));
        // Number of Players
        nbPlayers = Integer.parseInt(properties.getProperty("nbPlayers"));
        // Number of Starting Cards
        nbStartCards = Integer.parseInt(properties.getProperty("nbStartCards"));
        //Score Required to Win the game
        winningScore = Integer.parseInt(properties.getProperty("winningScore"));

        players = new Player[nbPlayers];
        for (int i = 0; i < players.length; i++) {
            switch (properties.getProperty("player" + i + "Type")) {
                case "NPC":
                    ISelector selector = StrategyFactory.getInstance().getSelector(
                            properties.getProperty("player" + i + "Selector")
                    );
                    IFilterer filterer = StrategyFactory.getInstance().getFilterer(
                            properties.getProperty("player" + i + "Filterer")
                    );
                    players[i] = new NPCPlayer(selector, filterer, this, handLocations[i], scoreLocations[i], i);
                    break;
                case "interactive":
                    players[i] = new InteractivePlayer(this, handLocations[i], scoreLocations[i], i);
                    break;
                default:
                    throw new IllegalArgumentException("can't recognize player" + i + "Type");
            }
        }

        assert (nbPlayers > 1);
        assert (nbStartCards > 1);
    }
}



