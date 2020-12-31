/*
 * Author: Andliage Pox
 * Date: 2020-12-30
 */

package evaluator;

import ds.Position;

import java.util.ArrayList;
import java.util.List;

public class ComboEvaluator extends BaseEvaluator {
    private final List<Evaluator> evaluators = new ArrayList<>();

    public ComboEvaluator(String s) {
        String[] evaluatorsType = s.split(",");
        for (String type: evaluatorsType) {
            switch (type) {
                case "piece-value":
                    evaluators.add(new PieceValueEvaluator());
                    break;
                case "knight-move":
                    evaluators.add(new KnightMoveEvaluator());
                    break;
                case "rook-move":
                    evaluators.add(new RookMoveEvaluator());
                    break;
            }
        }
    }

    @Override
    public int evaluate(Position position) {
        int sc = 0;
        for (Evaluator evaluator: evaluators) {
            sc += evaluator.evaluate(position);
        }
        return sc;
    }
}
