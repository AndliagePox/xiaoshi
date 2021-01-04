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

/**
 * 吃子着法生成器，扫描走法，能吃就吃最大的，不能吃就随机走子
 * 最早的实验着法生成器之一，留着玩。
 */
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
