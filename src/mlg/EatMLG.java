/*
 * Author: Andliage Pox
 * Date: 2021-01-02
 */

package mlg;

import ds.Move;
import ds.Position;

import java.util.Comparator;
import java.util.List;

/**
 * 优先吃子列表生成器
 */
public class EatMLG extends BaseMLG {
    EatMLG() {}

    @Override
    public List<Move> generateMoveList(Position position) {
        List<Move> list = new DefaultMLG().generateMoveList(position);
        list.sort(Comparator.comparingInt((move) -> position.getBoard()[move.to.x][move.to.y] == null ? 1 : 0));
        return list;
    }
}
