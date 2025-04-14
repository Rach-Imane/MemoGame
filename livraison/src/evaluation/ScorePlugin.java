package evaluation;

import java.util.HashMap;
import java.util.Map;

public class ScorePlugin {
    private static final Map<String, ScoreStrategy> strategies = new HashMap<>();
    private static ScoreStrategy currentStrategy;

    static {
        // Enregistrer les stratégies disponibles
        registerStrategy("Approximate", new ApproximateScore());
        registerStrategy("Strict", new StrictScore());
        registerStrategy("ColorWeighted", new ColorWeightedScore());
    }

    public static void registerStrategy(String name, ScoreStrategy strategy) {
        strategies.put(name, strategy);
    }

    public static void setCurrentStrategy(String name) {
        ScoreStrategy strategy = strategies.get(name);
        if (strategy != null) {
            currentStrategy = strategy;
        }
    }

    public static ScoreStrategy getCurrentStrategy() {
        if (currentStrategy == null) {
            currentStrategy = strategies.get("Approximate"); // Stratégie par défaut
        }
        return currentStrategy;
    }

    public static String[] getAvailableStrategies() {
        return strategies.keySet().toArray(new String[0]);
    }
}