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
        System.out.println(position);
        System.out.println(position.nextMove(new Move(new Location("b2"), new Location("e2"))));
    }
}
