/*
 * Author: Andliage Pox
 * Date: 2020-12-21
 */

package ds;

/**
 * 棋子
 */
public class Piece {
    /**
     * 用字母表示
     * 还是可参阅：http://www.xqbase.com/protocol/cchess_move.htm
     * 小写表示黑方，大写表示红方
     */
    public char c;

    /**
     * 属于哪个玩家
     */
    public Player belong;

    /**
     * 棋子类型
     */
    public PieceType type;

    /**
     * 棋子所在位置
     */
    public Location at;

    public Piece(char c, int x, int y) {
        this.c = c;

        at = new Location(x, y);

        if (c >= 'a') {
            belong = Player.BLACK;
            c -= 32;
        } else {
            belong = Player.RED;
        }

        switch (c) {
            case 'K':
                type = PieceType.KING;
                break;
            case 'A':
                type = PieceType.ADVISOR;
                break;
            case 'B':
                type = PieceType.BISHOP;
                break;
            case 'N':
                type = PieceType.KNIGHT;
                break;
            case 'R':
                type = PieceType.ROOK;
                break;
            case 'C':
                type = PieceType.CANNON;
                break;
            case 'P':
                type = PieceType.PAWN;
                break;
        }
    }

    public boolean belongBlack() {
        return belong == Player.BLACK;
    }
}
