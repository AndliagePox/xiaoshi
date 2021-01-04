/*
 * Author: Andliage Pox
 * Date: 2021-01-03
 */

package bmg;

import base.Configuration;
import ds.Move;
import ds.Player;
import ds.Position;
import mlg.CutOffMLG;
import mlg.IteratorMLG;

import java.util.LinkedList;

/**
 * 第三版搜索生成器，基础还是Alpha-Beta搜索，采用了迭代搜索和截断启发搜索。
 * 要求MLG必须支持迭代启发搜索和截断启发搜索(即实现IteratorMLG和CutOffMLG接口)。
 */
public class V3SearchBMG extends ABSearchBMG {
    private int curDepth;
    private final IteratorMLG iteratorMLG = (IteratorMLG) mlg;
    private final CutOffMLG cutOffMLG = (CutOffMLG) mlg;

    public V3SearchBMG(Position position) {
        super(position);
    }

    @Override
    public Move bestMove() {
        nodes = 0;

        // 清除历史截断移动，这玩意还是该一次一表的
        cutOffMLG.clearCutOffMoves();

        int depth = Configuration.getSearchDepth();
        long time, totalTime = System.currentTimeMillis();
        Result result = null;
        for (int i = 1; i <= depth; i++) {
            time = System.currentTimeMillis();
            curDepth = 0;
            result = search(
                    position,
                    new Result(-100000, new LinkedList<>()),
                    new Result(100000, new LinkedList<>()),
                    i
            );

            // 设置上层搜索结果，加速下一层搜索
            iteratorMLG.setLastResultMoveList(result.moveList);

            time = System.currentTimeMillis() - time;
            StringBuilder sb = new StringBuilder("info depth ");
            sb.append(i).append(" score ").append(result.score);
            sb.append(" time ").append(time);
            sb.append(" nodes ").append(nodes);
            sb.append(" nps ").append((int)((float) nodes / time * 1000));
            sb.append(" pv ");
            for (Move move: result.moveList) {
                sb.append(move).append(' ');
            }
            sb.deleteCharAt(sb.length() - 1);
            System.out.println(sb.toString());
            long ct = System.currentTimeMillis() - totalTime;
            if (i == depth && ct < 4000) {
                depth++;
            }
        }
        System.out.println("info total time " + (System.currentTimeMillis() - totalTime));
        if (result == null) return null;
        return result.moveList.getFirst();
    }

    private Result search(Position position, Result a, Result b, int depth) {
        nodes++;
        Move bestMove = null;
        Result bestResult = new Result(0, new LinkedList<>());

        Player winner = position.winner();
        if (winner != null) {
            bestResult.score = winner == position.currentPlayer() ? 50000 - curDepth / 2 : curDepth / 2 - 50000;
            return bestResult;
        }

        if (depth <= 0) {
            nodes++;
            bestResult.score = evaluator.evaluate(position);
            return bestResult;
        }

        curDepth++;

        for (Move move: mlg.generateMoveList(position)) {
            Result next = search(position.nextMove(move), b.reverse(), a.reverse(), depth - 1).reverse();
            if (next.score >= b.score) {
                b.moveList.addFirst(move);
                // 发生截断，添加着法
                cutOffMLG.add(move);
                curDepth--;
                return b;
            }
            if (next.score > a.score) {
                bestMove = move;
                // 发生截断，添加着法
                cutOffMLG.add(move);
                a = next;
            }
        }

        a.moveList.addFirst(bestMove);
        curDepth--;
        return a;
    }
}
