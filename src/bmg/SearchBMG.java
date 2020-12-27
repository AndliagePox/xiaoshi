/*
 * Author: Andliage Pox
 * Date: 2020-12-27
 */

package bmg;

import ds.Location;
import ds.Move;
import ds.Piece;
import ds.Position;

import java.util.List;

public class SearchBMG extends EvaluatorBMG {
    public SearchBMG(Position position) {
        super(position);
    }

    @Override
    public Move bestMove() {
        Move best = null;
        int min = Integer.MAX_VALUE;
        List<Piece> curPieces = getCurrentPieces();

        for (Piece piece: curPieces) {
            for (Location location: canMoveLocations(piece)) {
                Move move = new Move(piece.at, location);
                int nextScore = evaluator.evaluate(position.nextMove(move));
                if (nextScore < min) {
                    min = nextScore;
                    best = move;
                }
            }
        }
        System.out.println("info depth 1 score " + min + " pv " + best);

        return best;
    }
}
