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
        if (s.equals("default")) {
            return new DefaultMLG();
        } else if (s.equals("eat")) {
            return new EatMLG();
        } else {
            throw new RuntimeException("Invalid move list generator type [" + s + "].");
        }
    }
}
