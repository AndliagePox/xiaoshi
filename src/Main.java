/*
 * Author: Andliage Pox
 * Date: 2020-12-20
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String command;
        while (!(command = sc.nextLine()).equals("quit")) {
            if ("ucci".equals(command)) {
                printUcciOk();
            } else if ("isready".equals(command)) {
                System.out.println("readyok");
            } else if (command.startsWith("go")) {
                System.out.println("bestmove b2e2");
            }
        }
        System.out.println("bye");
    }

    private static void printUcciOk() {
        System.out.println("id name xiaoshi 0.1");
        System.out.println("id author Andliage");
        System.out.println("id copyright 2020 Andliage");
        System.out.println("id user Andliage");
        System.out.println("ucciok");
    }
}
