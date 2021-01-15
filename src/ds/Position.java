/*
 * Author: Andliage Pox
 * Date: 2020-12-21
 */

package ds;

import mlg.MLGFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 棋局，描述当前局面
 */
public class Position implements Cloneable {
    /**
     * 到哪方走棋
     */
    private Player cur;

    /**
     * 棋盘
     */
    private Piece[][] board = new Piece[10][9];

    /**
     * 红黑双方的子
     */
    private List<Piece> redPieces = new ArrayList<>();
    private List<Piece> blackPieces = new ArrayList<>();

    /**
     * 将帅，用于判定胜负
     */
    private Piece redKing;
    private Piece blackKing;

    /**
     * 移动栈，用于撤销上一步移动
     */
    public LinkedList<MoveRecord> moveStack = new LinkedList<>();

    /**
     * 执行一些新棋局初始化工作
     * 目前通过棋子数量是不是变多了来判断
     */
    public static void newGame() {
        MLGFactory.getMLG().clearBanMoves();
    }

    /**
     * 通过fen串创建局面，fen串规则参阅：http://www.xqbase.com/protocol/cchess_fen.htm
     * @param fen fen串
     */
    public Position(String fen) {
        if (fen.equals("startpos")) {
            fen = "rnbakabnr/9/1c5c1/p1p1p1p1p/9/9/P1P1P1P1P/1C5C1/9/RNBAKABNR w - - 0 1";
        }
        String[] parts = fen.split(" ");

        if (parts[1].equals("b")) {
            cur = Player.BLACK;
        } else {
            cur = Player.RED;
        }

        int i = 0, j = 0;
        String[] rows = parts[0].split("/");
        for (String row: rows) {
            for (char c: row.toCharArray()) {
                if (c > '0' && c <= '9') {
                    j += (c - '0');
                } else {
                    Piece piece = new Piece(c, i, j);
                    board[i][j] = piece;
                    if (piece.belongBlack()) {
                        blackPieces.add(piece);
                        if (piece.type == PieceType.KING) {
                            blackKing = piece;
                        }
                    } else {
                        redPieces.add(piece);
                        if (piece.type == PieceType.KING) {
                            redKing = piece;
                        }
                    }
                    j++;
                }
            }
            i++;
            j = 0;
        }
    }

    /**
     * 进行某个移动
     * @param move 进行的移动
     */
    public void applyMove(Move move) {
        applyMove(move, true);
    }

    public void applyMove(Move move, boolean addToStack) {
        int fx = move.from.x;
        int fy = move.from.y;
        int tx = move.to.x;
        int ty = move.to.y;

        Piece piece = board[fx][fy];
        board[fx][fy] = null;
        if (board[tx][ty] != null) {
            if (board[tx][ty] == redKing) {
                redKing = null;
            }
            if (board[tx][ty] == blackKing) {
                blackKing = null;
            }
            redPieces.remove(board[tx][ty]);
            blackPieces.remove(board[tx][ty]);
        }
        Piece tar = board[tx][ty];
        if (addToStack) {
            moveStack.add(new MoveRecord(move, tar));
        }
        board[tx][ty] = piece;
        piece.at = new Location(tx, ty);
        cur = anotherPlayer();
    }

    public void applyMoves(List<Move> moves) {
        for (Move move: moves) {
            applyMove(move);
        }
    }

    public void undoMove() {
        if (moveStack.isEmpty()) return;
        MoveRecord record = moveStack.pollLast();
        Move move = record.move;
        Piece piece = record.tar;
        applyMove(new Move(move.to, move.from), false);
        board[move.to.x][move.to.y] = piece;
        if (piece != null) {
            if (piece.belongBlack()) {
                blackPieces.add(piece);
                if (piece.type == PieceType.KING) {
                    blackKing = piece;
                }
            } else {
                redPieces.add(piece);
                if (piece.type == PieceType.KING) {
                    redKing = piece;
                }
            }
        }
    }

    public List<Piece> getCurrentPieces() {
        if (cur == Player.BLACK) {
            return blackPieces;
        } else {
            return redPieces;
        }
    }

    public List<Piece> getAntiPieces() {
        if (cur == Player.BLACK) {
            return redPieces;
        } else {
            return blackPieces;
        }
    }

    /**
     * 生成左右对称的棋局
     * @return 新的棋局对象，与本对象局面左右对称
     */
    public Position symmetricalPosition() {
        String s = this.toString();
        StringBuilder sb = new StringBuilder();
        for (String row: s.split(" ")[0].split("/")) {
            sb.append(new StringBuilder(row).reverse()).append('/');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(s.substring(s.indexOf(' ')));
        return new Position(sb.toString());
    }

    /**
     * 进行下一步移动后的棋局
     * @param move 进行的移动
     * @return 返回新的棋局，本棋局进行move移动动后的棋局
     */
    public Position nextMove(Move move) {
        Position next = (Position) clone();
        next.applyMove(move);
        return next;
    }

    /**
     * 空着(更换当前走子玩家)
     */
    public void changeCur() {
        cur = anotherPlayer();
    }

    /**
     * 寻找一个棋子所有合法的可移动位置
     * @param piece 棋子
     * @return 位置列表
     */
    public List<Location> canMoveLocations(Piece piece) {
        int cx = piece.at.x;
        int cy = piece.at.y;
        List<Location> locations = new ArrayList<>();
        if (piece.type == PieceType.KING) {
            Piece pk;
            if (piece.belongBlack()) {
                if (cx - 1 >= 0) {
                    checkForAdd(locations, cx - 1, cy);
                }
                if (cx + 1 <= 2) {
                    checkForAdd(locations, cx + 1, cy);
                }
                pk = redKing;
            } else {
                if (cx - 1 >= 7) {
                    checkForAdd(locations, cx - 1, cy);
                }
                if (cx + 1 <= 9) {
                    checkForAdd(locations, cx + 1, cy);
                }
                pk = blackKing;
            }
            if (cy - 1 >= 3) {
                checkForAdd(locations, cx, cy - 1);
            }
            if (cy + 1 <= 5) {
                checkForAdd(locations, cx, cy + 1);
            }
            if (pk != null && pk.at.y == cy) {
                boolean f = false;
                int s = Math.min(cx, pk.at.x) + 1;
                int e = Math.max(cx, pk.at.x) - 1;
                for (int x = s; x <= e; x++) {
                    if (board[x][cy] != null) {
                        f = true;
                        break;
                    }
                }
                if (!f) {
                    checkForAdd(locations, pk.at.x, cy);
                }
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

    @Override
    public String toString() {
        int c = 0;
        StringBuilder sb = new StringBuilder();
        for (Piece[] row: board) {
            for (Piece piece: row) {
                if (piece == null) {
                    c++;
                } else {
                    if (c != 0) {
                        sb.append(c);
                        c = 0;
                    }
                    sb.append(piece.c);
                }
            }
            if (c != 0) {
                sb.append(c);
            }
            sb.append("/");
            c = 0;
        }
        sb.deleteCharAt(sb.length() - 1);
        if (cur == Player.BLACK) {
            sb.append(" b");
        } else {
            sb.append(" w");
        }
        sb.append(" - - 0 1");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position)) return false;
        return toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    private Player anotherPlayer() {
        if (cur == Player.BLACK) return Player.RED;
        else return Player.BLACK;
    }

    public int pieceCount() {
        return redPieces.size() + blackPieces.size();
    }

    /**
     * 判定胜负，条件是轮到自己走且能吃掉对方将
     * 上个版本是判断将帅是否存活，该版本虽然降低了平时的搜索效率，但能在提前一层搜到杀棋，对后期残局提升很大
     *
     * 很好，我又改回上上个版本了，每次检测属于无用功，对平时的效率影响也很大
     *
     * @return 当前局面的胜利者，没有胜利者为null
     */
    public Player winner() {
        if (redKing == null) {
            return Player.BLACK;
        }
        if (blackKing == null) {
            return Player.RED;
        }
        return null;
    }

    public Player currentPlayer() {
        return cur;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public List<Piece> getRedPieces() {
        return redPieces;
    }

    public List<Piece> getBlackPieces() {
        return blackPieces;
    }

    public Piece getPieceByLocation(Location location) {
        return board[location.x][location.y];
    }

    @Override
    public Object clone() {
        Position position;
        try {
            position= (Position) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException("Clone fail.");
        }
        position.cur = cur;
        position.board = new Piece[10][9];
        position.redPieces = new ArrayList<>();
        position.blackPieces = new ArrayList<>();
        position.moveStack = new LinkedList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Piece sp = board[i][j];
                if (sp == null) continue;
                Piece np = new Piece(sp.c, i, j);
                position.board[i][j] = np;
                if (np.belong == Player.BLACK) {
                    position.blackPieces.add(np);
                    if (np.type == PieceType.KING) {
                        position.blackKing = np;
                    }
                } else {
                    position.redPieces.add(np);
                    if (np.type == PieceType.KING) {
                        position.redKing = np;
                    }
                }
            }
        }
        return position;
    }

    static class MoveRecord {
        Move move;
        Piece tar;

        public MoveRecord(Move move, Piece tar) {
            this.move = move;
            this.tar = tar;
        }
    }
}
