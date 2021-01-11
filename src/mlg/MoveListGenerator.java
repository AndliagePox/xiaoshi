package mlg;

import ds.Move;
import ds.Position;

import java.util.List;

public interface MoveListGenerator {
    List<Move> generateMoveList(Position position);

    void addBanMove(Move move);

    void clearBanMoves();
}
