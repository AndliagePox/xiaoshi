/*
 * Author: Andliage Pox
 * Date: 2020-12-30
 */

package evaluator;

import ds.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合评估器
 * 将多种评估器的分数相加，得到最终评分
 */
public class ComboEvaluator extends BaseEvaluator {
    private final List<Evaluator> evaluators = new ArrayList<>();

    ComboEvaluator(String s) {
        String[] evaluatorsType = s.split(",");
        for (String type: evaluatorsType) {
            evaluators.add(EvaluatorFactory.createEvaluatorByString(type));
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
