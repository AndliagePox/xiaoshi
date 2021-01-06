/*
 * Author: Andliage Pox
 * Date: 2020-12-21
 */

import ds.Location;
import ds.Move;
import ds.Position;

public class Test {
    public static void main(String[] args) {
        Position position = new Position("startpos");
        position.applyMove(new Move(new Location(7, 1), new Location(0, 1)));
        System.out.println(position);
        position.applyMove(new Move(new Location(0, 0), new Location(0, 1)));
        System.out.println(position);
        position.undoMove();
        System.out.println(position);
        position.undoMove();
        System.out.println(position);
        System.out.println(position.moveStack);
    }
}
