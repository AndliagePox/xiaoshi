/*
 * Author: Andliage Pox
 * Date: 2020-12-26
 */

package bmg;

import ds.PieceType;
import ds.Player;
import ds.Location;
import ds.Piece;
import ds.Position;

import java.util.ArrayList;
import java.util.List;

abstract public class BaseBMG implements BestMoveGenerator {
    protected Position position;
    protected Player cur;
    protected Piece[][] board;
    protected List<Piece> redPieces;
    protected List<Piece> blackPieces;

    public BaseBMG(Position position) {
        this.position = position;
        cur = position.currentPlayer();
        board = position.getBoard();
        redPieces = new ArrayList<>(position.getRedPieces());
        blackPieces = new ArrayList<>(position.getBlackPieces());
    }

    protected List<Piece> getCurrentPieces() {
        if (cur == Player.BLACK) {
            return blackPieces;
        } else {
            return redPieces;
        }
    }

    protected List<Location> canMoveLocations(Piece piece) {
        int cx = piece.at.x;
        int cy = piece.at.y;
        List<Location> locations = new ArrayList<>();
        if (piece.type == PieceType.KING) {
            if (piece.belongBlack()) {
                if (cx - 1 >= 0) {
                    checkForAdd(locations, cx - 1, cy);
                }
                if (cx + 1 <= 2) {
                    checkForAdd(locations, cx + 1, cy);
                }
            } else {
                if (cx - 1 >= 7) {
                    checkForAdd(locations, cx - 1, cy);
                }
                if (cx + 1 <= 9) {
                    checkForAdd(locations, cx + 1, cy);
                }
            }
            if (cy - 1 >= 3) {
                checkForAdd(locations, cx, cy - 1);
            }
            if (cy + 1 <= 5) {
                checkForAdd(locations, cx, cy + 1);
            }
        } else if (piece.type == PieceType.ADVISOR) {
            if (piece.belongBlack()) {
                if (cy == 4) {
                    checkForAdd(locations, 0, 3);
                    checkForAdd(locations, 0, 5);
                    checkForAdd(locations, 2, 3);
                    checkForAdd(locations, 2, 5);
                } else {
                    checkForAdd(locations, 1, 4);
                }
            } else {
                if (cy == 4) {
                    checkForAdd(locations, 7, 3);
                    checkForAdd(locations, 7, 5);
                    checkForAdd(locations, 9, 3);
                    checkForAdd(locations, 9, 5);
                } else {
                    checkForAdd(locations, 8, 4);
                }
            }
        } else if (piece.type == PieceType.BISHOP) {
            if (cx == 0 || cx == 5) {
                if (board[cx + 1][cy + 1] == null) checkForAdd(locations, cx + 2, cy + 2);
                if (board[cx + 1][cy - 1] == null) checkForAdd(locations, cx + 2, cy - 2);
            } else if (cx == 9 || cx == 4) {
                if (board[cx - 1][cy + 1] == null) checkForAdd(locations, cx - 2, cy + 2);
                if (board[cx - 1][cy - 1] == null) checkForAdd(locations, cx - 2, cy - 2);
            } else {
                if (cy != 0) {
                    if (board[cx + 1][cy - 1] == null) checkForAdd(locations, cx + 2, cy - 2);
                    if (board[cx - 1][cy - 1] == null) checkForAdd(locations, cx - 2, cy - 2);
                }
                if (cy != 8) {
                    if (board[cx + 1][cy + 1] == null) checkForAdd(locations, cx + 2, cy + 2);
                    if (board[cx - 1][cy + 1] == null) checkForAdd(locations, cx - 2, cy + 2);
                }
            }
        } else if (piece.type == PieceType.KNIGHT) {
            if (cx + 1 < 9 && board[cx + 1][cy] == null) {
                checkForAdd(locations, cx + 2, cy + 1);
                checkForAdd(locations, cx + 2, cy - 1);
            }
            if (cx - 1 > 0 && board[cx - 1][cy] == null) {
                checkForAdd(locations, cx - 2, cy + 1);
                checkForAdd(locations, cx - 2, cy - 1);
            }
            if (cy + 1 < 8 && board[cx][cy + 1] == null) {
                checkForAdd(locations, cx + 1, cy + 2);
                checkForAdd(locations, cx - 1, cy + 2);
            }
            if (cy - 1 > 0 && board[cx][cy - 1] == null) {
                checkForAdd(locations, cx + 1, cy - 2);
                checkForAdd(locations, cx - 1, cy - 2);
            }
        } else if (piece.type == PieceType.ROOK) {
            int x, y;
            for (x = cx - 1; x >= 0 && board[x][cy] == null; x--) {
                checkForAdd(locations, x, cy);
            }
            checkForAdd(locations, x, cy);
            for (x = cx + 1; x <= 9 && board[x][cy] == null; x++) {
                checkForAdd(locations, x, cy);
            }
            checkForAdd(locations, x, cy);
            for (y = cy - 1; y >= 0 && board[cx][y] == null; y--) {
                checkForAdd(locations, cx, y);
            }
            checkForAdd(locations, cx, y);
            for (y = cy + 1; y <= 8 && board[cx][y] == null; y++) {
                checkForAdd(locations, cx, y);
            }
            checkForAdd(locations, cx, y);
        } else if (piece.type == PieceType.CANNON) {
            int x, y, c;
            for (x = cx - 1; x >= 0 && board[x][cy] == null; x--) {
                checkForAdd(locations, x, cy);
            }
            for (x = cx + 1; x <= 9 && board[x][cy] == null; x++) {
                checkForAdd(locations, x, cy);
            }
            for (y = cy - 1; y >= 0 && board[cx][y] == null; y--) {
                checkForAdd(locations, cx, y);
            }
            for (y = cy + 1; y <= 8 && board[cx][y] == null; y++) {
                checkForAdd(locations, cx, y);
            }
            for (c = 0, x = cx - 1; x >= 0; x--) {
                if (board[x][cy] != null) c++;
                if (c == 2) {
                    checkForAdd(locations, x, cy);
                    break;
                }
            }
            for (c = 0, x = cx + 1; x <= 9; x++) {
                if (board[x][cy] != null) c++;
                if (c == 2) {
                    checkForAdd(locations, x, cy);
                    break;
                }
            }
            for (c = 0, y = cy - 1; y >= 0; y--) {
                if (board[cx][y] != null) c++;
                if (c == 2) {
                    checkForAdd(locations, cx, y);
                    break;
                }
            }
            for (c = 0, y = cy + 1; y <= 8; y++) {
                if (board[cx][y] != null) c++;
                if (c == 2) {
                    checkForAdd(locations, cx, y);
                    break;
                }
            }
        } else if (piece.type == PieceType.PAWN) {
            if (piece.belongBlack()) {
                if (cx < 5) {
                    checkForAdd(locations, cx + 1, cy);
                } else {
                    checkForAdd(locations, cx + 1, cy);
                    checkForAdd(locations, cx, cy - 1);
                    checkForAdd(locations, cx, cy + 1);
                }
            } else {
                if (cx > 4) {
                    checkForAdd(locations, cx - 1, cy);
                } else {
                    checkForAdd(locations, cx - 1, cy);
                    checkForAdd(locations, cx, cy - 1);
                    checkForAdd(locations, cx, cy + 1);
                }
            }
        }
        return locations;
    }

    private void checkForAdd(List<Location> list, int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > 8) return;
        Piece piece = board[x][y];
        if (piece == null || piece.belong != cur) {
            list.add(new Location(x, y));
        }
    }
}
