/*
 * Author: Andliage Pox
 * Date: 2020-12-21
 */

public class Piece {
    char c;
    Player belong;
    PieceType type;
    Location at;

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
                break;
            case 'A':
                type = PieceType.ADVISOR;
                break;
            case 'B':
                type = PieceType.BISHOP;
                break;
            case 'N':
                type = PieceType.KNIGHT;
                break;
            case 'R':
                type = PieceType.ROOK;
                break;
            case 'C':
                type = PieceType.CANNON;
                break;
            case 'P':
                type = PieceType.PAWN;
                break;
        }
    }

    public boolean belongBlack() {
        return belong == Player.BLACK;
    }
}
