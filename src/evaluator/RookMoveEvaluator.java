/*
 * Author: Andliage Pox
 * Date: 2020-12-30
 */

package evaluator;

import ds.Piece;
import ds.PieceType;
import ds.Position;

import java.util.List;

public class RookMoveEvaluator extends BaseEvaluator {
    RookMoveEvaluator() {}

    @Override
    public int evaluate(Position position) {
        int sc = 0;
        List<Piece> pieces = position.getCurrentPieces();
        for (Piece piece: pieces) {
            if (piece.type == PieceType.ROOK) {
                sc += 5 * position.canMoveLocations(piece).size();
            }
        }
        List<Piece> antiPieces = position.getAntiPieces();
        for (Piece piece: antiPieces) {
            if (piece.type == PieceType.ROOK) {
                sc -= 5 * position.canMoveLocations(piece).size();
            }
        }
        return sc;
    }
}
