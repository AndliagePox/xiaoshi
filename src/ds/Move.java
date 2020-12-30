/*
 * Author: Andliage Pox
 * Date: 2020-12-21
 */

package ds;

public class Move {
    public Location from;
    public Location to;

    public Move(Location from, Location to) {
        this.from = from;
        this.to = to;
    }

    public Move(String s) {
        this.from = new Location(s.substring(0, 2));
        this.to = new Location(s.substring(2));
    }

    @Override
    public String toString() {
        return from.toString() + to.toString();
    }
}
