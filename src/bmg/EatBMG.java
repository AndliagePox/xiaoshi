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

public class EatBMG extends EvaluatorBMG {
    public EatBMG(Position position) {
        super(position);
    }

    @Override
    public Move bestMove() {
        Move best = null;
        boolean canEat = false;
        int min = -evaluator.evaluate(position);
        List<Piece> curPieces = position.getCurrentPieces();

        for (Piece piece: curPieces) {
            for (Location location: position.canMoveLocations(piece)) {
                Move move = new Move(piece.at, location);
                int nextScore = evaluator.evaluate(position.nextMove(move));
                if (nextScore < min) {
                    canEat = true;
                    min = nextScore;
                    best = move;
                }
            }
        }

        if (!canEat) {
            best = new RandBMG(position).bestMove();
        }

        System.out.println("info depth 1 score " + -min + " pv " + best);

        return best;
    }
}
