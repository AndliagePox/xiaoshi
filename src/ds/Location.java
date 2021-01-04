/*
 * Author: Andliage Pox
 * Date: 2020-12-21
 */
package ds;

/**
 * 位置，指棋盘上的一个点
 * 用黑上红下的棋盘来说
 * xy表示法左上角为(0, 0)右下角为(9, 8)，方便程序编写
 * abc表示法左下角为a0，右上角为i9，是协议支持的表示法
 * 参阅：http://www.xqbase.com/protocol/cchess_move.htm
 */
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
