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
        if (bmgType.equals("rand")) {
            return new RandBMG(position);
        } else {
            throw new RuntimeException("Invalid bestmove generator type [" + bmgType + "].");
        }
    }
}
