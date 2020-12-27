/*
 * Author: Andliage Pox
 * Date: 2020-12-26
 */

package base;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
    public static Map<String, String> map = new HashMap<>();

    static {
        map.put("evaluate-type", "os");
        map.put("bestmove-type", "rand");
    }

    public static String getBestMoveType() {
        return map.get("bestmove-type");
    }

    public static String getEvaluateType() {
        return map.get("evaluate-type");
    }
}
