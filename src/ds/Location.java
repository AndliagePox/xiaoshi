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
    public String toString() {
        return String.valueOf((char)('a' + y)) + (char) ('9' - x);
    }
}
