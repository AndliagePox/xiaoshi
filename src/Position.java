/*
 * Author: Andliage Pox
 * Date: 2020-12-21
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Position {
    Player cur;
    Piece[][] board = new Piece[10][9];

    List<Piece> redPieces = new ArrayList<>();
    List<Piece> blackPieces = new ArrayList<>();

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
                    if (piece.belong == Player.BLACK) {
                        blackPieces.add(piece);
                    } else {
                        redPieces.add(piece);
                    }
                    j++;
                }
            }
            i++;
            j = 0;
        }
    }

    public void applyMoves(List<Move> moves) {
        for (Move move: moves) {
            int fx = move.from.x;
            int fy = move.from.y;
            int tx = move.to.x;
            int ty = move.to.y;

            Piece piece = board[fx][fy];
            board[fx][fy] = null;
            if (board[tx][ty] != null) {
                redPieces.remove(board[tx][ty]);
                blackPieces.remove(board[tx][ty]);
            }
            board[tx][ty] = piece;
            piece.at = new Location(tx, ty);
            if (cur == Player.BLACK) {
                cur = Player.RED;
            } else {
                cur = Player.BLACK;
            }
        }
    }

    public Move bestMove() {
        Piece piece;
        int times = 0;
        Location from, to;
        while (times < 5) {
            if (cur == Player.BLACK) {
                piece = blackPieces.get(random(blackPieces.size()));
            } else {
                piece = redPieces.get(random(redPieces.size()));
            }
            List<Location> locations = canMoveLocations(piece);
            if (locations.size() > 0) {
                from = piece.at;
                to = locations.get(random(locations.size()));
                System.out.println("info depth 5 score -300");
                return new Move(from, to);
            }
            times++;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Piece[] row: board) {
            for (Piece piece: row) {
                if (piece == null) {
                    sb.append(' ');
                } else {
                    sb.append(piece.c);
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private List<Location> canMoveLocations(Piece piece) {
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

    private int random(int b) {
        Random random = new Random();
        return random.nextInt(b);
    }

    private void checkForAdd(List<Location> list, int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > 8) return;
        Piece piece = board[x][y];
        if (piece == null || piece.belong != cur) {
            list.add(new Location(x, y));
        }
    }
}
