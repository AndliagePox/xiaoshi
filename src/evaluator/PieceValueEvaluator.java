/*
 * Author: Andliage Pox
 * Date: 2020-12-27
 */

package evaluator;

import ds.Piece;
import ds.Player;
import ds.Position;

/**
 * 子力价值评估器，每种棋子都有对应的分值
 */
public class PieceValueEvaluator extends BaseEvaluator {
    PieceValueEvaluator() {}

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
            case PAWN:
                sc = 100;
                break;
            case KNIGHT:
            case CANNON:
                sc = 400;
                break;
            case ROOK:
                sc = 800;
                break;
        }
        return sc;
    }
}
