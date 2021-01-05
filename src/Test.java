/*
 * Author: Andliage Pox
 * Date: 2020-12-21
 */

import ds.Move;
import ds.Position;

public class Test {
    public static void main(String[] args) {
        Position position = new Position("4k4/9/8R/9/9/9/9/9/r3p4/3K5 w - - 0 1");
        System.out.println(position);
        System.out.println((position.clone()));
        Move move = new Move("b2e2");
        System.out.println(move.symmetricalMove());
    }
}
