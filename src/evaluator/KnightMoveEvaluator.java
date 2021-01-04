/*
 * Author: Andliage Pox
 * Date: 2020-12-30
 */

package evaluator;

import ds.Piece;
import ds.PieceType;
import ds.Position;

import java.util.List;

/**
 * 马的灵活性评估器，马的可移动列表每多一个位置+10分
 */
public class KnightMoveEvaluator extends BaseEvaluator {
    KnightMoveEvaluator() {}

    @Override
    public int evaluate(Position position) {
        int sc = 0;
        List<Piece> pieces = position.getCurrentPieces();
        for (Piece piece: pieces) {
            if (piece.type == PieceType.KNIGHT) {
                sc += 10 * position.canMoveLocations(piece).size();
            }
        }
        List<Piece> antiPieces = position.getAntiPieces();
        for (Piece piece: antiPieces) {
            if (piece.type == PieceType.KNIGHT) {
                sc -= 10 * position.canMoveLocations(piece).size();
            }
        }
        return sc;
    }
}
