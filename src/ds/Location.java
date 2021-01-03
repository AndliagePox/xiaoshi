/*
 * Author: Andliage Pox
 * Date: 2020-12-21
 */
package ds;

public class Location {
    public int x, y;

   public Location(int x, int y) {
       this.x = x;
       this.y = y;
   }

   public Location(String sp) {
       this.x = '9' - sp.charAt(1);
       this.y = sp.charAt(0) - 'a';
   }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (x != location.x) return false;
        return y == location.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return String.valueOf((char)('a' + y)) + (char) ('9' - x);
    }
}
