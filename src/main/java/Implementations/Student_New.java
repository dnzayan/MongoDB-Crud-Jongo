package Implementations;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.morphia.annotations.Entity;
import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity("Student")
public class Student_New extends Student{
    @MongoId
    private ObjectId My_id;

    public void setMy_id(ObjectId my_id) {
        My_id = my_id;
    }

    public ObjectId getMy_id() {
        return My_id;
    }

    @JsonCreator
    public Student_New(@JsonProperty("userID")UUID userID, @JsonProperty("loginName")String loginName, @JsonProperty("books") List<Book_New> books) {
        super(userID, loginName, null);

        List<IBook> books_inter = new ArrayList<>();
        for (int i = 0 ; i< books.size(); i++)
        {
            books_inter.add(books.get(i));
        }
        setBooks(books_inter);

        My_id = new ObjectId();
    }

    @Override
    public String toString() {
        return "Student_New{" +
                "My_id=" + My_id +
                '}';
    }
}
