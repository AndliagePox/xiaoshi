/*
 * Author: Andliage Pox
 * Date: 2020-12-21
 */

import java.util.List;

public class Position {
    Player cur;
    Piece[][] board = new Piece[10][9];

    public Position(String fen) {
        if (fen.equals("startpos")) {
            fen = "rnbakabnr/9/1c5c1/p1p1p1p1p/9/9/P1P1P1P1P/1C5C1/9/RNBAKABNR w - - 0 1";
        }
        String[] parts = fen.split(" ");

        if (parts[1].equals("b")) {
            cur = Player.BLACK;
        } else {
            cur = Player.RED;
        }

        int i = 0, j = 0;
        String[] rows = parts[0].split("/");
        for (String row: rows) {
            for (char c: row.toCharArray()) {
                if (c > '0' && c <= '9') {
                    j += (c - '0');
                } else {
                    board[i][j] = new Piece(c, i, j);
                    j++;
                }
            }
            i++;
            j = 0;
        }
    }

    public void applyMoves(List<Move> moves) {
        for (Move move: moves) {
            int fx = move.from.x;
            int fy = move.from.y;
            int tx = move.to.x;
            int ty = move.to.y;

            Piece piece = board[fx][fy];
            board[fx][fy] = null;
            board[tx][ty] = piece;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Piece[] row: board) {
            for (Piece piece: row) {
                if (piece == null) {
                    sb.append(' ');
                } else {
                    sb.append(piece.c);
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
