/*
 * Author: Andliage Pox
 * Date: 2020-12-27
 */

package bmg;

import base.Configuration;
import ds.Position;

/**
 * BMG工厂，调用createBMG根据配置创建对应的BMG。
 */
abstract public class BMGFactory {
    public static BestMoveGenerator createBMG(Position position) {
        String bmgType = Configuration.getBestMoveType();
        return createBMGByString(bmgType, position);
    }

    static BestMoveGenerator createBMGByString(String s, Position position) {
        switch (s) {
            case "rand":
                return new RandBMG(position);
            case "eat":
                return new EatBMG(position);
            case "bf-search":
                return new BFSearchBMG(position);
            case "ab-search":
                return new ABSearchBMG(position);
            case "v3-search":
                return new V3SearchBMG(position);
            default:
                throw new RuntimeException("Invalid bestmove generator type [" + s + "].");
        }
    }
}
