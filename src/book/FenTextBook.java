/*
 * Author: Andliage Pox
 * Date: 2020-12-30
 */

package book;

import ds.Move;
import ds.Position;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FenTextBook extends BaseBook {
    private final Map<Position, Item> map = new HashMap<>();

    public FenTextBook() {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader("book.txt"));
            while ((line = reader.readLine()) != null) {
                int i = line.indexOf("move");
                Move move = new Move(line.substring(i + 5, i + 9));
                String fen = line.substring(0, i);
                int score = Integer.parseInt(line.substring(i + 10));
                map.put(new Position(fen), new Item(score, move));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Move nextMove(Position position) {
        Item item = map.get(position);
        if (item != null) {
            System.out.println("info book hit");
            System.out.println("info depth 0 score " + item.score + " pv " + item.move);
            return item.move;
        } else {
            return null;
        }
    }
}
