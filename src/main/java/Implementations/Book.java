package Implementations;

import java.util.UUID;

public class Book extends IBook {
    private UUID bookID;
    public String bookName;


    public Book(UUID bookID, String bookName) {
        this.bookID = bookID;
        this.bookName = bookName;
    }

    public UUID getBookID() {
        return bookID;
    }

    public void setBookID(UUID bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
