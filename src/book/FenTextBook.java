/*
 * Author: Andliage Pox
 * Date: 2020-12-30
 */

package book;

import base.Util;
import ds.Move;
import ds.Position;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FenTextBook extends BaseBook {
    private final Map<Position, List<Item>> map = new HashMap<>();

    FenTextBook() {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader("book.txt"));
            while ((line = reader.readLine()) != null) {
                int i = line.indexOf("move");
                Move move = new Move(line.substring(i + 5, i + 9));
                String fen = line.substring(0, i);
                int score = Integer.parseInt(line.substring(i + 10));
                Position position = new Position(fen);
                map.computeIfAbsent(position, k -> new ArrayList<>()).add(new Item(score, move));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Move nextMove(Position position) {
        /* 查找的是否是对称局面 */
        boolean s = false;

        List<Item> list = map.get(position);
        if (list == null) {
            list = map.get(position.symmetricalPosition());
            if (list != null) {
                s = true;
            }
        }
        if (list == null) return null;

        Item item = list.get(Util.random(list.size()));
        Move move = s ? item.move.symmetricalMove() : item.move;
        System.out.println("info book hit");
        System.out.println("info depth 0 score " + item.score + " pv " + move);
        return move;
    }
}
