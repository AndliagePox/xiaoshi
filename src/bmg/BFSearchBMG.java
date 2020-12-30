/*
 * Author: Andliage Pox
 * Date: 2020-12-28
 */

package bmg;

import base.Configuration;
import ds.Location;
import ds.Move;
import ds.Piece;
import ds.Position;

import java.util.LinkedList;

public class BFSearchBMG extends EvaluatorBMG {

    public BFSearchBMG(Position position) {
        super(position);
    }

    @Override
    public Move bestMove() {
        int depth = Configuration.getSearchDepth();
        Result result = searchDepth(position, depth);
        StringBuilder sb = new StringBuilder("info depth ");
        sb.append(depth).append(" score ").append(result.score).append(" pv ");
        for (Move move: result.moveList) {
            sb.append(move).append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb.toString());
        return result.moveList.getFirst();
    }

    private Result searchDepth(Position position, int depth) {
        int score = -100000;
        Result bestResult = new Result(score, new LinkedList<>());
        if (depth == 0) {
            bestResult.score = evaluator.evaluate(position);
            return bestResult;
        }

        Move bestMove = null;
        for (Piece piece: position.getCurrentPieces()) {
            for (Location location: position.canMoveLocations(piece)) {
                Move move = new Move(piece.at, location);
                Result next = searchDepth(position.nextMove(move), depth - 1);
                if (-next.score > score) {
                    score = -next.score;
                    bestMove = move;
                    bestResult = next;
                }
            }
        }

        if (bestMove == null) {
            bestResult.moveList.add(new RandBMG(position).bestMove());
        } else {
            bestResult.moveList.addFirst(bestMove);
        }

        return new Result(score, bestResult.moveList);
    }

    static class Result {
        int score;
        LinkedList<Move> moveList;

        public Result(int score, LinkedList<Move> moveList) {
            this.score = score;
            this.moveList = moveList;
        }
    }
}
