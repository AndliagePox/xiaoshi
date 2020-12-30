package book;

import ds.Move;
import ds.Position;

public interface Book {
    Move nextMove(Position position);
}
