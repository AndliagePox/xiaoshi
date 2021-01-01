/*
 * Author: Andliage Pox
 * Date: 2020-12-27
 */

package evaluator;

import ds.Piece;
import ds.Player;
import ds.Position;

public class PieceValueEvaluator extends BaseEvaluator {
    @Override
    public int evaluate(Position position) {
        int sc = 0;
        for (Piece piece: position.getRedPieces()) {
            sc += scoreMap(piece);
        }
        for (Piece piece: position.getBlackPieces()) {
            sc -= scoreMap(piece);
        }
        return position.currentPlayer() == Player.BLACK ? -sc : sc;
    }

    private int scoreMap(Piece piece) {
        int sc = 0;
        switch (piece.type) {
            case KING:
                sc = 30000;
                break;
            case ADVISOR:
            case BISHOP:
                sc = 200;
                break;
            case KNIGHT:
            case CANNON:
                sc = 500;
                break;
            case ROOK:
                sc = 1000;
                break;
            case PAWN:
                sc = 50;
                if (piece.belongBlack()) {
                    if (piece.at.x > 4) {
                        sc = 200;
                    }
                } else {
                    if (piece.at.x < 6) {
                        sc = 200;
                    }
                }
                break;
        }
        return sc;
    }
}
