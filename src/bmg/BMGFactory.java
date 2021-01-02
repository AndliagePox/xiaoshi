/*
 * Author: Andliage Pox
 * Date: 2020-12-27
 */

package bmg;

import base.Configuration;
import ds.Position;

abstract public class BMGFactory {
    public static BestMoveGenerator createBMG(Position position) {
        String bmgType = Configuration.getBestMoveType();
        switch (bmgType) {
            case "rand":
                return new RandBMG(position);
            case "eat":
                return new EatBMG(position);
            case "bf-search":
                return new BFSearchBMG(position);
            case "ab-search":
                return new ABSearchBMG(position);
            default:
                throw new RuntimeException("Invalid bestmove generator type [" + bmgType + "].");
        }
    }
}
