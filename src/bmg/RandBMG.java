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
                piece = blackPieces.get(random(blackPieces.size()));
            } else {
                piece = redPieces.get(random(redPieces.size()));
            }
            List<Location> locations = canMoveLocations(piece);
            if (locations.size() > 0) {
                from = piece.at;
                to = locations.get(random(locations.size()));
                Move move = new Move(from, to);
                System.out.println("info depth 1 score 0 pv " + move);
                return move;
            }
            times++;
        }
        return null;
    }

    private int random(int b) {
        Random random = new Random();
        return random.nextInt(b);
    }
}
