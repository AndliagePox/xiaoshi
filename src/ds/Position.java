/*
 * Author: Andliage Pox
 * Date: 2020-12-21
 */

package ds;

import java.util.ArrayList;
import java.util.List;

public class Position {
    private Player cur;
    private final Piece[][] board = new Piece[10][9];
    private final List<Piece> redPieces = new ArrayList<>();
    private final List<Piece> blackPieces = new ArrayList<>();

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
                    Piece piece = new Piece(c, i, j);
                    board[i][j] = piece;
                    if (piece.belongBlack()) {
                        blackPieces.add(piece);
                    } else {
                        redPieces.add(piece);
                    }
                    j++;
                }
            }
            i++;
            j = 0;
        }
    }

    public void applyMove(Move move) {
        int fx = move.from.x;
        int fy = move.from.y;
        int tx = move.to.x;
        int ty = move.to.y;

        Piece piece = board[fx][fy];
        board[fx][fy] = null;
        if (board[tx][ty] != null) {
            redPieces.remove(board[tx][ty]);
            blackPieces.remove(board[tx][ty]);
        }
        board[tx][ty] = piece;
        piece.at = new Location(tx, ty);
        cur = anotherPlayer();
    }

    public void applyMoves(List<Move> moves) {
        for (Move move: moves) {
            applyMove(move);
        }
    }


    public Position nextMove(Move move) {
        Position next = new Position(this.toString());
        next.applyMove(move);
        return next;
    }

    @Override
    public String toString() {
        int c = 0;
        StringBuilder sb = new StringBuilder();
        for (Piece[] row: board) {
            for (Piece piece: row) {
                if (piece == null) {
                    c++;
                } else {
                    if (c != 0) {
                        sb.append(c);
                        c = 0;
                    }
                    sb.append(piece.c);
                }
            }
            if (c != 0) {
                sb.append(c);
            }
            sb.append("/");
            c = 0;
        }
        sb.deleteCharAt(sb.length() - 1);
        if (cur == Player.BLACK) {
            sb.append(" b");
        } else {
            sb.append(" w");
        }
        sb.append(" - - 0 1");
        return sb.toString();
    }

    private Player anotherPlayer() {
        if (cur == Player.BLACK) return Player.RED;
        else return Player.BLACK;
    }

    public Player currentPlayer() {
        return cur;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public List<Piece> getRedPieces() {
        return redPieces;
    }

    public List<Piece> getBlackPieces() {
        return blackPieces;
    }
}
