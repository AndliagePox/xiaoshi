/*
 * Author: Andliage Pox
 * Date: 2021-01-03
 */

package mlg;

import ds.Move;
import ds.Piece;
import ds.PieceType;
import ds.Position;

import java.util.*;

/**
 * 积分着法列表生成器，类似于组合评估器，实现了IteratorMLG和CutOffMLG，可以支持迭代启发搜索和截断启发搜索
 */
public class ScoreMLG extends BaseMLG implements IteratorMLG, CutOffMLG {
    private Position position;
    private List<Move> moveList;
    private List<Move> lastResultMoveList;
    private final Map<Move, Integer> moveScore = new HashMap<>();
    private final Set<Move> cutOffSet = new HashSet<>();

    @Override
    public void clearCutOffMoves() {
        cutOffSet.clear();
    }

    @Override
    public void add(Move move) {
        cutOffSet.add(move);
    }

    @Override
    public void setLastResultMoveList(List<Move> list) {
        lastResultMoveList = list;
    }

    @Override
    public List<Move> generateMoveList(Position position) {
        this.position = position;
        moveList = baseMoveList(position);
        moveScore.clear();
        calcScore();
        moveList.sort(Comparator.comparingInt((move) -> -moveScore.get(move)));
        return moveList;
    }


    private void calcScore() {
        for (Move move: moveList) {
            int sc = 0;

            // 上次搜索结果
            if (lastResultMoveList != null && lastResultMoveList.contains(move)) {
                sc += 5000;
            }

            Piece self = position.getPieceByLocation(move.from);
            Piece tar = position.getPieceByLocation(move.to);

            // 吃子着法
            if (tar != null) {
                switch (tar.type) {
                    case KING:
                        sc += 30000;
                        break;
                    case KNIGHT:
                    case CANNON:
                        sc += 500;
                        break;
                    case ROOK:
                        sc += 1000;
                        break;
                    default:
                        // 但吃小子并不优于截断着法
                        sc += 200;
                }
            }

            // 产生截断的着法
            if (cutOffSet.contains(move)) {
                sc += 400;
            }

            // 优先动大子
            if (self.type == PieceType.ROOK
                    || self.type == PieceType.KNIGHT
                    || self.type == PieceType.CANNON) {
                sc += 100;
            }

            moveScore.put(move, sc);
        }
    }
}
