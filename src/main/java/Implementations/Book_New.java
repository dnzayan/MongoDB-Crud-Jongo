package Implementations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.morphia.annotations.Entity;
import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity("Book")
public class Book_New extends Book {
    @MongoId
    private ObjectId My_id;

    @JsonCreator
    public Book_New(@JsonProperty("bookID")UUID bookID, @JsonProperty("bookName") String bookName) {
        super(bookID, bookName);
        My_id = new ObjectId();
    }

    public void setMy_id(ObjectId ma_id) {
        this.My_id = ma_id;
    }

    public ObjectId getMy_id() {
        return My_id;
    }

    @Override
    public String toString() {
        return "Book_New{" +
                "My_id=" + My_id +
                '}';
    }
}
