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

/**
 * 配置类
 * 看起来挺通俗易懂的，就不多介绍了
 */
public class Configuration {
    /**
     * 配置HashMap，初始化时读取文件，put各配置项
     */
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

    /*
    然后通过下面这一众方法获取配置的值
     */

    public static String getBestMoveType() {
        return map.get("bestmove-type");
    }

    public static String getEvaluateType() {
        return map.get("evaluate-type");
    }

    public static int getSearchDepth() {
        return Integer.parseInt(map.get("depth"));
    }

    public static boolean enableBook() {
        return map.get("book-enable").equals("true");
    }

    public static String getBookType() {
        return map.get("book-type");
    }

    public static String getComboList() {
        return map.get("combo-list");
    }

    public static String getMoveListType() {
        return map.get("move-list-type");
    }
}
