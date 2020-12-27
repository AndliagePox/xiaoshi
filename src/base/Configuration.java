/*
 * Author: Andliage Pox
 * Date: 2020-12-26
 */

package base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    public static Map<String, String> map = new HashMap<>();

    static {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader("xiaoshi.cfg"));
            while ((line = reader.readLine()) != null) {
                int i = line.indexOf('=');
                if (i == -1) continue;
                String key = line.substring(0, i);
                String value = line.substring(i + 1);
                map.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getBestMoveType() {
        return map.get("bestmove-type");
    }

    public static String getEvaluateType() {
        return map.get("evaluate-type");
    }
}
