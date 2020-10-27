package game;

import game.filterer.IFilterer;
import game.filterer.NaiveLegalFilterer;
import game.filterer.NothingFilterer;
import game.filterer.TrumpSavingFilterer;
import game.selector.HighestRankSelector;
import game.selector.RandomSelector;
import game.selector.ISelector;
import game.selector.SmartSelector;

import java.util.HashMap;

public class StrategyFactory {

    private static StrategyFactory instance;

    public static StrategyFactory getInstance() {
        if (instance == null) {
            instance = new StrategyFactory();
        }
        return instance;
    }

    private StrategyFactory() {
        filtererMap.put("nothing", new NothingFilterer());
        filtererMap.put("naive legal", new NaiveLegalFilterer());
        filtererMap.put("trump saving", new TrumpSavingFilterer());

        selectorMap.put("random", new RandomSelector());
        selectorMap.put("highest rank", new HighestRankSelector());
        selectorMap.put("smart", new SmartSelector());
    }

    private final HashMap<String, IFilterer> filtererMap = new HashMap<>();
    public IFilterer getFilterer(String identifier) {
        IFilterer filterer = filtererMap.get(identifier);
        if (filterer == null) {
            throw new IllegalArgumentException("Possible Typo: can not recognize identifier");
        }
        return filterer;
    }

    private final HashMap<String, ISelector> selectorMap = new HashMap<>();
    public ISelector getSelector(String identifier) {
        ISelector selector = selectorMap.get(identifier);
        if (selector == null) {
            throw new IllegalArgumentException("Possible Typo: can not recognize identifier");
        }
        return selector;
    }
}
