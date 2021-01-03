/*
 * Author: Andliage Pox
 * Date: 2020-12-27
 */

package evaluator;

import base.Configuration;

abstract public class EvaluatorFactory {
    private static final Evaluator evaluator = createEvaluatorByString(Configuration.getEvaluateType());

    public static Evaluator getEvaluator() {
        return evaluator;
    }

    static Evaluator createEvaluatorByString(String s) {
        switch (s) {
            case "piece-value":
                return new PieceValueEvaluator();
            case "combo":
                return new ComboEvaluator(Configuration.getComboList());
            case "knight-move":
                return new KnightMoveEvaluator();
            case "rook-move":
                return new RookMoveEvaluator();
            case "location":
                return new LocationEvaluator();
            default:
                throw new RuntimeException("Invalid evaluate type [" + s + "].");
        }
    }
}
