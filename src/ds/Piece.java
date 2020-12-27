/*
 * Author: Andliage Pox
 * Date: 2020-12-21
 */

package ds;

public class Piece {
    public int score;
    public char c;
    public Player belong;
    public PieceType type;
    public Location at;

    public Piece(char c, int x, int y) {
        this.c = c;

        at = new Location(x, y);

        if (c >= 'a') {
            belong = Player.BLACK;
            c -= 32;
        } else {
            belong = Player.RED;
        }

        switch (c) {
            case 'K':
                type = PieceType.KING;
                score = 30000;
                break;
            case 'A':
                type = PieceType.ADVISOR;
                score = 200;
                break;
            case 'B':
                type = PieceType.BISHOP;
                score = 200;
                break;
            case 'N':
                type = PieceType.KNIGHT;
                score = 500;
                break;
            case 'R':
                type = PieceType.ROOK;
                score = 1000;
                break;
            case 'C':
                type = PieceType.CANNON;
                score = 500;
                break;
            case 'P':
                type = PieceType.PAWN;
                score = 200;
                break;
        }
    }

    public boolean belongBlack() {
        return belong == Player.BLACK;
    }
}
