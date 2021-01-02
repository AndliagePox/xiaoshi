/*
 * Author: Andliage Pox
 * Date: 2021-01-02
 */

package bmg;

import base.Configuration;
import ds.Move;
import ds.Player;
import ds.Position;
import mlg.MLGFactory;

import java.util.LinkedList;

public class ABSearchBMG extends EvaluatorBMG {
    private int nodes;

    public ABSearchBMG(Position position) {
        super(position);
    }

    @Override
    public Move bestMove() {
        nodes = 0;
        int depth = Configuration.getSearchDepth();
        long time = System.currentTimeMillis();
        Result result = abSearch(
                position,
                new Result(-100000, new LinkedList<>()),
                new Result(100000, new LinkedList<>()),
                depth
        );
        time = System.currentTimeMillis() - time;
        StringBuilder sb = new StringBuilder("info depth ");
        sb.append(depth).append(" score ").append(result.score);
        sb.append(" time ").append(time);
        sb.append(" nodes ").append(nodes).append(" pv ");
        for (Move move: result.moveList) {
            sb.append(move).append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb.toString());
        return result.moveList.getFirst();
    }

    private Result abSearch(Position position, Result a, Result b, int depth) {
        Move bestMove = null;
        Result bestResult = new Result(0, new LinkedList<>());

        Player winner = position.winner();
        if (winner != null) {
            nodes++;
            bestResult.score = winner == position.currentPlayer() ? 50002 - depth : depth - 50002;
            return bestResult;
        }

        if (depth == 0) {
            nodes++;
            bestResult.score = evaluator.evaluate(position);
            return bestResult;
        }

        for (Move move: MLGFactory.getMLG().generateMoveList(position)) {
            Result next = abSearch(position.nextMove(move), b.reverse(), a.reverse(), depth - 1).reverse();
            if (next.score >= b.score) {
                b.moveList.addFirst(move);
                return b;
            }
            if (next.score > a.score) {
                bestMove = move;
                a = next;
            }
        }
        a.moveList.addFirst(bestMove);
        return a;
    }

    static class Result {
        int score;
        LinkedList<Move> moveList;

        public Result(int score, LinkedList<Move> moveList) {
            this.score = score;
            this.moveList = moveList;
        }

        public Result reverse() {
            return new Result(-score, new LinkedList<>(moveList));
        }
    }
}
