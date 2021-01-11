/*
 * Author: Andliage Pox
 * Date: 2020-12-20
 */

import bmg.BMGFactory;
import bmg.BestMoveGenerator;
import base.Logger;
import book.Book;
import book.BookFactory;
import ds.Location;
import ds.Move;
import ds.Position;
import mlg.MLGFactory;
import mlg.MoveListGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String command;
        Logger logger = new Logger();
        Position position = new Position("startpos");
        Book book = BookFactory.getBook();

        while (!(command = sc.nextLine()).equals("quit")) {
            logger.write(command);
            if ("ucci".equals(command)) {
                printUcciOk();
            } else if ("isready".equals(command)) {
                System.out.println("readyok");
            } else if (command.startsWith("go")) {
                Move move = null;
                if (book != null) {
                    move = book.nextMove(position);
                }
                if (move == null) {
                    BestMoveGenerator bmg = BMGFactory.createBMG(position);
                    move = bmg.bestMove();
                }
                if (move == null) {
                    System.out.println("nobestmove");
                } else {
                    System.out.println("bestmove " + move);
                }
                MLGFactory.getMLG().clearBanMoves();
            } else if (command.startsWith("position")) {
                String fen;
                Position np;
                int movesStart = command.indexOf("moves");
                if (movesStart == -1) {
                    fen = command.substring(13);
                    np = new Position(fen);
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
                    np = new Position(fen);
                    np.applyMoves(moves);
                }
                if (np.pieceCount() > position.pieceCount()) {
                    Position.newGame();
                }
                position = np;
            } else if (command.startsWith("banmoves")) {
                String moveStr = command.substring(command.indexOf(' ') + 1);
                MoveListGenerator mlg = MLGFactory.getMLG();
                for (String s: moveStr.split(" ")) {
                    mlg.addBanMove(new Move(s));
                }
            } else if (command.equals("test")) {
                System.out.println("test");
            }
            System.out.flush();
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
