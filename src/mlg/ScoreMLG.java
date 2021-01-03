/*
 * Author: Andliage Pox
 * Date: 2021-01-03
 */

package mlg;

import ds.Move;
import ds.Position;

import java.util.*;

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
        moveList = new DefaultMLG().generateMoveList(position);
        moveScore.clear();
        calcScore();
        moveList.sort(Comparator.comparingInt((move) -> -moveScore.get(move)));
        return moveList;
    }


    private void calcScore() {
        for (Move move: moveList) {
            int sc = 0;
            if (lastResultMoveList != null && lastResultMoveList.contains(move)) {
                sc += 1000;
            }
            if (cutOffSet.contains(move)) {
                sc += 500;
            }
            if (position.getBoard()[move.to.x][move.to.y] != null) {
                sc += 200;
            }
            moveScore.put(move, sc);
        }
    }
}
