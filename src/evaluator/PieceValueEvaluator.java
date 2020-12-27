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
            sc += piece.score;
        }
        for (Piece piece: position.getBlackPieces()) {
            sc -= piece.score;
        }
        return position.currentPlayer() == Player.BLACK ? -sc : sc;
    }
}
