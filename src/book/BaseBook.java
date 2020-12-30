/*
 * Author: Andliage Pox
 * Date: 2020-12-30
 */

package book;

import ds.Move;

abstract public class BaseBook implements Book {
    static class Item {
        int score;
        Move move;

        public Item(int score, Move move) {
            this.score = score;
            this.move = move;
        }
    }
}
