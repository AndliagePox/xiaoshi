/*
 * Author: Andliage Pox
 * Date: 2020-12-21
 */

import java.io.*;

public class Logger {
    private BufferedWriter bw;

    public Logger() {
        try {
            bw = new BufferedWriter(new FileWriter("input.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String s) {
        try {
            String sb = "[" + System.currentTimeMillis() + "] " + s + "\n";
            bw.write(sb);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
