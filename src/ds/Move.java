/*
 * Author: Andliage Pox
 * Date: 2020-12-21
 */

package ds;

/**
 * 移动
 */
public class Move {
    /**
     * 从哪里来
     */
    public Location from;
    /**
     * 到哪里去
     */
    public Location to;

    public Move(Location from, Location to) {
        this.from = from;
        this.to = to;
    }

    public Move(String s) {
        this.from = new Location(s.substring(0, 2));
        this.to = new Location(s.substring(2));
    }

    /**
     * 生成左右对称的着法
     * @return 新着法，与本着法左右对称
     */
    public Move symmetricalMove() {
        Location sf = new Location(from.x, 8 - from.y);
        Location st = new Location(to.x, 8 - to.y);
        return new Move(sf, st);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (!from.equals(move.from)) return false;
        return to.equals(move.to);
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return from.toString() + to.toString();
    }
}
