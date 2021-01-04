/*
 * Author: Andliage Pox
 * Date: 2020-12-26
 */

package bmg;

import ds.Player;
import ds.Piece;
import ds.Position;
import mlg.MLGFactory;
import mlg.MoveListGenerator;

import java.util.ArrayList;
import java.util.List;

abstract public class BaseBMG implements BestMoveGenerator {
    /**
     * 当前局面
     */
    protected Position position;

    /**
     * 当前玩家
     */
    protected Player cur;

    /**
     * 当前局面的棋盘
     */
    protected Piece[][] board;

    /**
     * 红黑双方的棋子列表
     */
    protected List<Piece> redPieces;
    protected List<Piece> blackPieces;

    /**
     * 着法列表生成器
     */
    protected MoveListGenerator mlg;

    public BaseBMG(Position position) {
        this.position = position;
        cur = position.currentPlayer();
        board = position.getBoard();
        redPieces = new ArrayList<>(position.getRedPieces());
        blackPieces = new ArrayList<>(position.getBlackPieces());
        mlg = MLGFactory.getMLG();
    }
}
