/*
 * Author: Andliage Pox
 * Date: 2020-12-27
 */

package bmg;

import ds.Position;
import evaluator.Evaluator;
import evaluator.EvaluatorFactory;

abstract public class EvaluatorBMG extends BaseBMG {
    protected Evaluator evaluator;

    public EvaluatorBMG(Position position) {
        super(position);
        evaluator = EvaluatorFactory.createEvaluator();
    }
}
