package Implementations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.morphia.annotations.Entity;

import java.util.List;
import java.util.UUID;


public class Student extends IStudent {
    UUID userID;
    public String loginName;
    List<IBook> books;

    public Student(UUID userID, String loginName, List<IBook> books) {
        this.userID = userID;
        this.loginName = loginName;
        this.books = books;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public List<IBook> getBooks() {
        return books;
    }

    public void setBooks(List<IBook> books) {
        this.books = books;
    }
}
