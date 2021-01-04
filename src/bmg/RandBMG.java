/*
 * Author: Andliage Pox
 * Date: 2020-12-26
 */

package bmg;

import base.Util;
import ds.Player;
import ds.Location;
import ds.Move;
import ds.Piece;
import ds.Position;

import java.util.List;

/**
 * 随机着法生成器，她还真就是乱走的。
 * 最早的实验着法生成器之一，留着玩。
 */
public class RandBMG extends BaseBMG {

    public RandBMG(Position position) {
        super(position);
    }

    @Override
    public Move bestMove() {
        Piece piece;
        int times = 0;
        Location from, to;
        while (times < 5) {
            if (cur == Player.BLACK) {
                if (blackPieces.isEmpty()) return null;
                piece = blackPieces.get(Util.random(blackPieces.size()));
            } else {
                if (redPieces.isEmpty()) return null;
                piece = redPieces.get(Util.random(redPieces.size()));
            }
            List<Location> locations = position.canMoveLocations(piece);
            if (locations.size() > 0) {
                from = piece.at;
                to = locations.get(Util.random(locations.size()));
                return new Move(from, to);
            }
            times++;
        }
        return null;
    }
}
