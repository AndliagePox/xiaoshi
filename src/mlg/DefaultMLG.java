/*
 * Author: Andliage Pox
 * Date: 2021-01-02
 */

package mlg;

import ds.Move;
import ds.Position;

import java.util.List;

/**
 * 默认着法列表生成器，只扫描着法列表，不排序
 */
public class DefaultMLG extends BaseMLG {
    DefaultMLG() {}

    @Override
    public List<Move> generateMoveList(Position position) {
        return baseMoveList(position);
    }
}
