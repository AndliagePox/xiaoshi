/*
 * Author: Andliage Pox
 * Date: 2020-12-30
 */

package book;

import base.Configuration;

abstract public class BookFactory {
    public static Book createBook() {
        String bookType = Configuration.getBookType();
        if (bookType.equals("text")) {
            return new FenTextBook();
        } else {
            throw new RuntimeException("Invalid book type [" + bookType + "].");
        }
    }
}
