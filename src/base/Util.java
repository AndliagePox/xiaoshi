/*
 * Author: Andliage Pox
 * Date: 2020-12-31
 */

package base;

import java.util.Random;

abstract public class Util {
    private static final Random random = new Random();

    public static int random(int b) {
        return random.nextInt(b);
    }
}
