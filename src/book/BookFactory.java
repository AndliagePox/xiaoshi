/*
 * Author: Andliage Pox
 * Date: 2020-12-30
 */

package book;

import base.Configuration;

abstract public class BookFactory {
    private static Book book = null;

    static {
        if (Configuration.enableBook()) {
            book = createBookByString(Configuration.getBookType());
        }
    }

    public static Book getBook() {
        return book;
    }

    public static Book createBookByString(String s) {
        if (s.equals("text")) {
            return new FenTextBook();
        } else {
            throw new RuntimeException("Invalid book type [" + s + "].");
        }
    }
}
