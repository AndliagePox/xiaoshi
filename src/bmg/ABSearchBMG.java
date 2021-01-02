/*
 * Author: Andliage Pox
 * Date: 2021-01-02
 */

package bmg;

import base.Configuration;
import ds.Location;
import ds.Move;
import ds.Piece;
import ds.Position;

import java.util.LinkedList;

public class ABSearchBMG extends EvaluatorBMG {
    public ABSearchBMG(Position position) {
        super(position);
    }

    @Override
    public Move bestMove() {
        int depth = Configuration.getSearchDepth();
        Result result = abSearch(position, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        StringBuilder sb = new StringBuilder("info depth ");
        sb.append(depth).append(" score ").append(result.score).append(" pv ");
        for (Move move: result.moveList) {
            sb.append(move).append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb.toString());
        return result.moveList.getFirst();
    }

    private Result abSearch(Position position, int depth, int a, int b, boolean self) {
        Move bestMove = null;
        Result bestResult = new Result(0, new LinkedList<>());
        if (depth == 0) {
            bestResult.score = evaluator.evaluate(position);
            return bestResult;
        }

        if (self) {
            selfSearch: for (Piece piece: position.getCurrentPieces()) {
                for (Location location: position.canMoveLocations(piece)) {
                    Move move = new Move(piece.at, location);
                    Result next = abSearch(position.nextMove(move), depth - 1, a, b, false);
                    if (next.score > a) {
                        a = next.score;
                        bestMove = move;
                        bestResult = next;
                    }
                    if (b <= a) {
                        break selfSearch;
                    }
                }
            }
            bestResult.score = a;
        } else {
            antiSearch: for (Piece piece: position.getCurrentPieces()) {
                for (Location location: position.canMoveLocations(piece)) {
                    Move move = new Move(piece.at, location);
                    Result next = abSearch(position.nextMove(move), depth - 1, a, b, true);
                    if (next.score < b) {
                        b = next.score;
                        bestMove = move;
                        bestResult = next;
                    }
                    if (b <= a) {
                        break antiSearch;
                    }
                }
            }
            bestResult.score = b;
        }
        bestResult.moveList.addFirst(bestMove);
        return bestResult;
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
