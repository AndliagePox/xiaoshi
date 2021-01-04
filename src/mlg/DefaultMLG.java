/*
 * Author: Andliage Pox
 * Date: 2021-01-02
 */

package mlg;

import ds.Location;
import ds.Move;
import ds.Piece;
import ds.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认着法列表生成器，只扫描着法列表，不排序
 */
public class DefaultMLG extends BaseMLG {
    DefaultMLG() {}

    @Override
    public List<Move> generateMoveList(Position position) {
        List<Move> list = new ArrayList<>();
        for (Piece piece: position.getCurrentPieces()) {
            for (Location location: position.canMoveLocations(piece)) {
                list.add(new Move(piece.at, location));
            }
        }
        return list;
    }
}
