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
    protected Position position;
    protected Player cur;
    protected Piece[][] board;
    protected List<Piece> redPieces;
    protected List<Piece> blackPieces;
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
