/*
 * Author: Andliage Pox
 * Date: 2020-12-27
 */

package evaluator;

import base.Configuration;

abstract public class EvaluatorFactory {
    public static Evaluator createEvaluator() {
        String evaluateType = Configuration.getEvaluateType();
        if (evaluateType.equals("piece-value")) {
            return new PieceValueEvaluator();
        } else if (evaluateType.equals("combo")) {
            return new ComboEvaluator(Configuration.getComboList());
        } else {
            throw new RuntimeException("Invalid evaluate type [" + evaluateType + "].");
        }
    }
}
