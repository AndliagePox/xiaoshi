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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

abstract public class BaseMLG implements MoveListGenerator {
    protected Set<Move> banMoves = new HashSet<>();

    @Override
    public void addBanMove(Move move) {
        banMoves.add(move);
    }

    @Override
    public void clearBanMoves() {
        banMoves.clear();
    }

    public List<Move> baseMoveList(Position position) {
        List<Move> list = new ArrayList<>();
        for (Piece piece: position.getCurrentPieces()) {
            for (Location location: position.canMoveLocations(piece)) {
                Move move = new Move(piece.at, location);
                if (!banMoves.contains(move)) {
                    list.add(move);
                }
            }
        }
        return list;
    }
}
