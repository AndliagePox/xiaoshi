/*
 * Author: Andliage Pox
 * Date: 2020-12-26
 */

package bmg;

import ds.Player;
import ds.Location;
import ds.Move;
import ds.Piece;
import ds.Position;

import java.util.List;
import java.util.Random;

public class RandBMG extends BaseBMG {
    private final Random random = new Random();

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
                piece = blackPieces.get(random(blackPieces.size()));
            } else {
                if (redPieces.isEmpty()) return null;
                piece = redPieces.get(random(redPieces.size()));
            }
            List<Location> locations = position.canMoveLocations(piece);
            if (locations.size() > 0) {
                from = piece.at;
                to = locations.get(random(locations.size()));
                return new Move(from, to);
            }
            times++;
        }
        return null;
    }

    private int random(int b) {
        return random.nextInt(b);
    }
}
