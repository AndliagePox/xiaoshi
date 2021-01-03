/*
 * Author: Andliage Pox
 * Date: 2021-01-02
 */

package mlg;

import base.Configuration;

public class MLGFactory {
    private static final MoveListGenerator mlg = createMLGByString(Configuration.getMoveListType());

    public static MoveListGenerator getMLG() {
        return mlg;
    }

    static MoveListGenerator createMLGByString(String s) {
        switch (s) {
            case "default":
                return new DefaultMLG();
            case "eat":
                return new EatMLG();
            case "score":
                return new ScoreMLG();
            default:
                throw new RuntimeException("Invalid move list generator type [" + s + "].");
        }
    }
}
