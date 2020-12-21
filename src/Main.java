/*
 * Author: Andliage Pox
 * Date: 2020-12-20
 */

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String command;
        Logger logger = new Logger();
        Position position = new Position("startpos");
        while (!(command = sc.nextLine()).equals("quit")) {
            logger.write(command);
            if ("ucci".equals(command)) {
                printUcciOk();
            } else if ("isready".equals(command)) {
                System.out.println("readyok");
            } else if (command.startsWith("go")) {
                Move move = position.bestMove();
                System.out.println("bestmove " + move.from + move.to);
            } else if (command.startsWith("position")) {
                String fen;
                int movesStart = command.indexOf("moves");
                if (movesStart == -1) {
                    fen = command.substring(13);
                    position = new Position(fen);
                } else {
                    fen = command.substring(13, movesStart);
                    String[] movesStr = command.substring(movesStart).split(" ");
                    List<Move> moves = new LinkedList<>();
                    for (int i = 1; i < movesStr.length; i++) {
                        String curMove = movesStr[i];
                        Location from = new Location(curMove.substring(0, 2));
                        Location to = new Location(curMove.substring(2));
                        moves.add(new Move(from, to));
                    }
                    position = new Position(fen);
                    position.applyMoves(moves);
                }
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
