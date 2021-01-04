/*
 * Author: Andliage Pox
 * Date: 2020-12-27
 */

package bmg;

import ds.Position;
import evaluator.Evaluator;
import evaluator.EvaluatorFactory;

/**
 * 带局面评估器的着法生成器，一般都会带的，除非……
 */
abstract public class EvaluatorBMG extends BaseBMG {
    protected Evaluator evaluator;

    public EvaluatorBMG(Position position) {
        super(position);
        evaluator = EvaluatorFactory.getEvaluator();
    }
}
