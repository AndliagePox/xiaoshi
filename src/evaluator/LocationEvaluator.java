/*
 * Author: Andliage Pox
 * Date: 2021-01-03
 */

package evaluator;

import ds.Piece;
import ds.PieceType;
import ds.Player;
import ds.Position;

/**
 * 位置分数评估器
 * 每种棋子在不同时期的不同位置有不同的分数
 */
public class LocationEvaluator extends BaseEvaluator {
    /**
     * 下面这几个数组的命名规则是：
     * LV_[MID/END]_[TYPE]_[ATK/DEF]
     * MID/END表示开中局还是残局
     * TYPE是棋子类型，其中兵将、士相可以共用一张表，它们的合法位置不重合
     * ATK/DEF表示进攻状态还是防御状态，攻防应该有不同的评分，防御时士相作用大而进攻时作用小
     */
    private static final int[][] LV_MID_PAWN_KING_ATK = {
            {9, 9, 9, 11, 13, 11, 9, 9, 9},
            {39, 49, 69, 84, 89, 84, 69, 49, 39},
            {39, 49, 64, 74, 74, 74, 64, 49, 39},
            {39, 46, 54, 59, 61, 59, 54, 46, 39},
            {29, 37, 41, 54, 59, 54, 41, 37, 29},
            {7, 0, 13, 0, 16, 0, 13, 0, 7},
            {7, 0, 7, 0, 15, 0, 7, 0, 7},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 2, 2, 2, 0, 0, 0},
            {0, 0, 0, 11, 15, 11, 0, 0, 0}
    };

    private static final int[][] LV_MID_PAWN_KING_DEF = {
            {9, 9, 9, 11, 13, 11, 9, 9, 9},
            {19, 24, 34, 42, 44, 42, 34, 24, 19},
            {19, 24, 32, 37, 37, 37, 32, 24, 19},
            {19, 23, 27, 29, 30, 29, 27, 23, 19},
            {14, 18, 20, 27, 29, 27, 20, 18, 14},
            {7, 0, 13, 0, 16, 0, 13, 0, 7},
            {7, 0, 7, 0, 15, 0, 7, 0, 7},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 2, 2, 2, 0, 0, 0},
            {0, 0, 0, 11, 15, 11, 0, 0, 0}
    };

    private static final int[][] LV_END_PAWN_KING_ATK = {
            {10, 10, 10, 15, 15, 15, 10, 10, 10},
            {50, 55, 60, 85, 100, 85, 60, 55, 50},
            {65, 70, 70, 75, 75, 75, 70, 70, 65},
            {75, 80, 80, 80, 80, 80, 80, 80, 75},
            {70, 70, 65, 70, 70, 70, 65, 70, 70},
            {45, 0, 40, 45, 45, 45, 40, 0, 45},
            {40, 0, 35, 40, 40, 40, 35, 0, 40},
            {0, 0, 0, 5, 15, 5, 0, 0, 0},
            {0, 0, 0, 3, 13, 3, 0, 0, 0},
            {0, 0, 0, 1, 11, 1, 0, 0, 0}
    };

    private static final int[][] LV_END_PAWN_KING_DEF = {
            {10, 10, 10, 15, 15, 15, 10, 10, 10},
            {10, 15, 20, 45, 60, 45, 20, 15, 10},
            {25, 30, 30, 35, 35, 35, 30, 30, 25},
            {35, 40, 40, 45, 45, 45, 40, 40, 35},
            {25, 30, 30, 35, 35, 35, 30, 30, 25},
            {25, 0, 25, 25, 25, 25, 25, 0, 25},
            {20, 0, 20, 20, 20, 20, 20, 0, 20},
            {0, 0, 5, 5, 13, 5, 5, 0, 0},
            {0, 0, 3, 3, 12, 3, 3, 0, 0},
            {0, 0, 1, 1, 11, 1, 1, 0, 0}
    };

    private static final int[][] LV_ADVISOR_BISHOP_ATK = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 20, 0, 0, 0, 20, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {18, 0, 0, 20, 23, 20, 0, 0, 18},
            {0, 0, 0, 0, 23, 0, 0, 0, 0},
            {0, 0, 20, 20, 0, 20, 20, 0, 0}
    };

    private static final int[][] LV_ADVISOR_BISHOP_DEF = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 40, 0, 0, 0, 40, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {38, 0, 0, 40, 43, 40, 0, 0, 38},
            {0, 0, 0, 0, 43, 0, 0, 0, 0},
            {0, 0, 40, 40, 0, 40, 40, 0, 0}
    };

    private static final int[][] LV_MID_KNIGHT = {
            {90, 90, 90, 96, 90, 96, 90, 90, 90},
            {90, 96, 103, 97, 94, 97, 103, 96, 90},
            {92, 98, 99, 103, 99, 103, 99, 98, 92},
            {93, 108, 100, 107, 100, 107, 100, 108, 93},
            {90, 100, 99, 103, 104, 103, 99, 100, 90},
            {90, 98, 101, 102, 103, 102, 101, 98, 90},
            {92, 94, 98, 95, 98, 95, 98, 94, 92},
            {93, 92, 94, 95, 92, 95, 94, 92, 93},
            {85, 90, 92, 93, 78, 93, 92, 90, 85},
            {88, 85, 90, 88, 90, 88, 90, 85, 88}
    };

    private static final int[][] LV_END_KNIGHT = {
            {92, 94, 96, 96, 96, 96, 96, 94, 92},
            {94, 96, 98, 98, 98, 98, 98, 96, 94},
            {96, 98, 100, 100, 100, 100, 100, 98, 96},
            {96, 98, 100, 100, 100, 100, 100, 98, 96},
            {96, 98, 100, 100, 100, 100, 100, 98, 96},
            {94, 96, 98, 98, 98, 98, 98, 96, 94},
            {94, 96, 98, 98, 98, 98, 98, 96, 94},
            {92, 94, 96, 96, 96, 96, 96, 94, 92},
            {90, 92, 94, 92, 92, 92, 94, 92, 90},
            {88, 90, 92, 90, 90, 90, 92, 90, 88}
    };

    private static final int[][] LV_MID_ROOK = {
            {206, 208, 207, 213, 214, 213, 207, 208, 206},
            {206, 212, 209, 216, 233, 216, 209, 212, 206},
            {206, 208, 207, 214, 216, 214, 207, 208, 206},
            {206, 213, 213, 216, 216, 216, 213, 213, 206},
            {208, 211, 211, 214, 215, 214, 211, 211, 208},
            {208, 212, 212, 214, 215, 214, 212, 212, 208},
            {204, 209, 204, 212, 214, 212, 204, 209, 204},
            {198, 208, 204, 212, 212, 212, 204, 208, 198},
            {200, 208, 206, 212, 200, 212, 206, 208, 200},
            {194, 206, 204, 212, 200, 212, 204, 206, 194}
    };

    private static final int[][] LV_END_ROOK = {
            {182, 182, 182, 184, 186, 184, 182, 182, 182},
            {184, 184, 184, 186, 190, 186, 184, 184, 184},
            {182, 182, 182, 184, 186, 184, 182, 182, 182},
            {180, 180, 180, 182, 184, 182, 180, 180, 180},
            {180, 180, 180, 182, 184, 182, 180, 180, 180},
            {180, 180, 180, 182, 184, 182, 180, 180, 180},
            {180, 180, 180, 182, 184, 182, 180, 180, 180},
            {180, 180, 180, 182, 184, 182, 180, 180, 180},
            {180, 180, 180, 182, 184, 182, 180, 180, 180},
            {180, 180, 180, 182, 184, 182, 180, 180, 180}
    };

    private static final int[][] LV_MID_CANNON = {
            {100, 100, 96, 91, 90, 91, 96, 100, 100},
            {98, 98, 96, 92, 89, 92, 96, 98, 98},
            {97, 97, 96, 91, 92, 91, 96, 97, 97},
            {96, 99, 99, 98, 100, 98, 99, 99, 96},
            {96, 96, 96, 96, 100, 96, 96, 96, 96},
            {95, 96, 99, 96, 100, 96, 99, 96, 95},
            {96, 96, 96, 96, 96, 96, 96, 96, 96},
            {97, 96, 100, 99, 101, 99, 100, 96, 97},
            {96, 97, 98, 98, 98, 98, 98, 97, 96},
            {96, 96, 97, 99, 99, 99, 97, 96, 96}
    };

    private static final int[][] LV_END_CANNON = {
            {100, 100, 100, 100, 100, 100, 100, 100, 100},
            {100, 100, 100, 100, 100, 100, 100, 100, 100},
            {100, 100, 100, 100, 100, 100, 100, 100, 100},
            {100, 100, 100, 102, 104, 102, 100, 100, 100},
            {100, 100, 100, 102, 104, 102, 100, 100, 100},
            {100, 100, 100, 102, 104, 102, 100, 100, 100},
            {100, 100, 100, 102, 104, 102, 100, 100, 100},
            {100, 100, 100, 102, 104, 102, 100, 100, 100},
            {100, 100, 100, 104, 106, 104, 100, 100, 100},
            {100, 100, 100, 104, 106, 104, 100, 100, 100}
    };

    /**
     * 中局/残局子力阈值
     */
    private static final int MID_END_BOUND = 12;

    /**
     * 进攻子力阈值
     */
    private static final int ATK_DEF_BOUND = 2;

    LocationEvaluator() {}

    @Override
    public int evaluate(Position position) {
        int sc = 0;
        for (Piece piece: position.getCurrentPieces()) {
            sc += locationScore(position, piece);
        }
        for (Piece piece: position.getAntiPieces()) {
            sc -= locationScore(position, piece);
        }
        return sc;
    }

    private int locationScore(Position position, Piece piece) {
        return selectTable(position, piece)[piece.belongBlack() ? 9 - piece.at.x : piece.at.x][piece.at.y];
    }

    private int[][] selectTable(Position position, Piece piece) {
        int count = 0, redAtk = 0, blackAtk = 0, selfAtk, antiAtk;

        /*
        统计本方剩余子力，车6分马炮3分其他1分，小于MID_END_BOUND认为是残局阶段
         */
        for (Piece p: position.getCurrentPieces()) {
            if (p.type == PieceType.ROOK) {
                count += 6;
            } else if (p.type == PieceType.KNIGHT) {
                count += 3;
            } else {
                count++;
            }
        }

        /*
        统计双方进攻情况，看过河子力，车马2分，炮兵1分，大于ATK_DEF_BOUND认为在进攻状态
         */
        for (Piece p: position.getRedPieces()) {
            if (p.at.x < 5) {
                if (p.type == PieceType.ROOK || p.type == PieceType.KNIGHT) {
                    redAtk += 2;
                } else if (p.type == PieceType.CANNON || p.type == PieceType.PAWN) {
                    redAtk += 1;
                }
            }
        }
        for (Piece p: position.getBlackPieces()) {
            if (p.at.x > 4) {
                if (p.type == PieceType.ROOK || p.type == PieceType.KNIGHT) {
                    blackAtk += 2;
                } else if (p.type == PieceType.CANNON || p.type == PieceType.PAWN) {
                    blackAtk += 1;
                }
            }
        }
        if (position.currentPlayer() == Player.BLACK) {
            selfAtk = blackAtk;
            antiAtk = redAtk;
        } else {
            selfAtk = redAtk;
            antiAtk = blackAtk;
        }

        if (count < MID_END_BOUND) {
            return getTable_IDEA(piece, selfAtk, antiAtk, LV_END_CANNON, LV_END_KNIGHT, LV_END_ROOK, LV_END_PAWN_KING_ATK, LV_END_PAWN_KING_DEF);
        } else {
            return getTable_IDEA(piece, selfAtk, antiAtk, LV_MID_CANNON, LV_MID_KNIGHT, LV_MID_ROOK, LV_MID_PAWN_KING_ATK, LV_MID_PAWN_KING_DEF);
        }
    }

    private int[][] getTable_IDEA(Piece piece, int selfAtk, int antiAtk, int[][] lvEndCannon, int[][] lvEndKnight, int[][] lvEndRook, int[][] lvEndPawnKingAtk, int[][] lvEndPawnKingDef) {
        if (piece.type == PieceType.CANNON) {
            return lvEndCannon;
        } else if (piece.type == PieceType.KNIGHT) {
            return lvEndKnight;
        } else if (piece.type == PieceType.ROOK) {
            return lvEndRook;
        } else if (piece.type == PieceType.KING || piece.type == PieceType.PAWN) {
            return selfAtk > ATK_DEF_BOUND ? lvEndPawnKingAtk : lvEndPawnKingDef;
        } else {
            return antiAtk > ATK_DEF_BOUND ? LV_ADVISOR_BISHOP_DEF : LV_ADVISOR_BISHOP_ATK;
        }
    }
}
