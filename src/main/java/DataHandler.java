import Implementations.*;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;


import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataHandler extends Database {

    ////////////// STUDENT OPERATIONS
    public void addStudent(Student student){


    }
    public Student_New getStudent(UUID my_id){
        try{
            MongoCollection students = getJo().getCollection("Student");
            DBObject query_student = QueryBuilder.start("userID").is(my_id).get();
            Student_New student_new = students.findOne(query_student.toString()).as(Student_New.class);

            if (student_new != null){
                return student_new;
            }
            else{
                System.out.println("Student does not exist in database.");
                return null;
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong. " + e.toString());
            return null;
        }
    }
    public void deleteStudent(UUID student_ID) {
        try{
            MongoCollection students = getJo().getCollection("Student");
            DBObject query_student = QueryBuilder.start("userID").is(student_ID).get();
            Student_New st = students.findOne(query_student.toString()).as(Student_New.class);
            if (st != null){
                students.remove(st.getMy_id());
            }
            else{
                System.out.println("Student does not exist in database.");
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong. " + e.toString());
        }
    }
    public ArrayList<Student_New> getAllStudents() {
        ArrayList<Student_New> new_student_list = new ArrayList<>();
        MongoCollection collection_wanted  = getJo().getCollection("Student"); // Retrieving
        Iterator<Student_New> all_students = collection_wanted.find().as(Student_New.class).iterator();

        while (all_students.hasNext())
        {
            new_student_list.add(all_students.next());
        }
        return new_student_list;
    }
    public void updateStudent(Student student){
        try{
            MongoCollection students = getJo().getCollection("Student");
            DBObject query_student = QueryBuilder.start("userID").is(student.getUserID()).get();
            Student_New st_new = students.findOne(query_student.toString()).as(Student_New.class);

            if (st_new != null){
                st_new.setBooks(student.getBooks());
                st_new.setLoginName(student.getLoginName());

                students.save(st_new);
            }
            else{
                System.out.println("Student does not exist in database.");
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong. " + e.toString());
        }
    }
    public ArrayList<Book_New> getBooks_of_Student(UUID my_id){
        Student_New my_st = getStudent(my_id);
        ArrayList<Book_New> books_new = new ArrayList<>();

        try {
            List<IBook> books_inter = my_st.getBooks();
            for (int i = 0; i < books_inter.size(); i++) {
                books_new.add((Book_New) books_inter.get(i));
            }
            return books_new;
        }
        catch (Exception e) {
            System.out.println("Something went wrong. " + e.toString());
            return null;
        }
    }
    public void addBook_to_Student(Student student, Book book){
        DBObject query_student = QueryBuilder.start("userID").is(student.getUserID()).get();
        DBObject query_book = QueryBuilder.start("bookID").is(book.getBookID()).get();

        MongoCollection students = getJo().getCollection("Student");
        MongoCollection books = getJo().getCollection("Book");
        try{
            Book_New our_book = books.findOne(query_book.toString()).as(Book_New.class);
            if (our_book == null)
            {
                throw new NullPointerException();
            }
            try{
                Student_New our_human = students.findOne(query_student.toString()).as(Student_New.class);
                if (our_human == null)
                {
                    throw new NullPointerException();
                }
                List<IBook> book_inter = our_human.getBooks();

                boolean human_has = false;
                int i = 0;
                for ( ; i < book_inter.size() ; i++)
                {
                    if (our_book.getMy_id().equals(((Book_New) book_inter.get(i)).getMy_id())) {
                        human_has = true;
                        break;
                    }
                }
                if (!human_has)
                {
                    book_inter.add(our_book);
                    our_human.setBooks(book_inter);
                    students.save(our_human);
                }
            }
            catch (Exception e)
            {
                System.out.println("Student or Student's StudentID is not in the database. To add this Book to the Student please add the Student to the database first. " +e.toString());
            }
        }
        catch (Exception e)
        {
            System.out.println("Book or Book's BookID is not in the database. To add this Book to the Student please add the Book to the database first. " +e.toString());
        }
    }
    public void deleteBook_from_Student(Student student, Book book){
        DBObject query_student = QueryBuilder.start("userID").is(student.getUserID()).get();
        DBObject query_book = QueryBuilder.start("bookID").is(book.getBookID()).get();

        MongoCollection students = getJo().getCollection("Student");
        MongoCollection books = getJo().getCollection("Book");
        try{
            Book_New our_book = books.findOne(query_book.toString()).as(Book_New.class);
            if (our_book == null)
            {
                throw new NullPointerException();
            }
            try{
                Student_New our_human = students.findOne(query_student.toString()).as(Student_New.class);
                if (our_human == null)
                {
                    throw new NullPointerException();
                }
                List<IBook> book_inter = our_human.getBooks();

                boolean human_has = false;
                int i = 0;
                for ( ; i < book_inter.size() ; i++)
                {
                    if (our_book.getMy_id().equals(((Book_New) book_inter.get(i)).getMy_id())) {
                        human_has = true;
                        break;
                    }
                }
                if (human_has)
                {
                    book_inter.remove(i);
                    our_human.setBooks(book_inter);
                    students.save(our_human);
                }
            }
            catch (Exception e)
            {
                System.out.println("Student or Student's StudentID is not in the database. To remove this Book from Student, please add the Student to the database first. " +e.toString());
            }
        }
        catch (Exception e)
        {
            System.out.println("Book or Book's BookID is not in the database. To remove this Book from Student, please add the Book to the database first. " +e.toString());
        }
    }

    ////////////// BOOK OPERATIONS
    public void addBook(Book book) {
        try{
            MongoCollection books = getJo().getCollection("Book");
            DBObject query_book = QueryBuilder.start("bookID").is(book.getBookID()).get();
            Book_New bk = books.findOne(query_book.toString()).as(Book_New.class);
            if (bk == null){
                books.save(book);
            }
            else{
                System.out.println("Book is already in database.");
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong. " + e.toString());
        }
    }
    public Book_New getBook(UUID my_id){
        try{
            MongoCollection books = getJo().getCollection("Book");
            DBObject query_book = QueryBuilder.start("bookID").is(my_id).get();
            Book_New book_new = books.findOne(query_book.toString()).as(Book_New.class);

            if (book_new != null){
                return book_new;
            }
            else{
                System.out.println("Book does not exist in database.");
                return null;
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong. " + e.toString());
            return null;
        }
    }
    public void deleteBook(UUID book_ID) {
        try{
            MongoCollection books = getJo().getCollection("Book");
            DBObject query_book = QueryBuilder.start("bookID").is(book_ID).get();
            Book_New bk = books.findOne(query_book.toString()).as(Book_New.class);
            if (bk != null){
                books.remove(bk.getMy_id());
            }
            else{
                System.out.println("Book does not exist in database.");
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong. " + e.toString());
        }
    }
    public ArrayList<Book_New> getAllBooks() {
        ArrayList<Book_New> new_books_list = new ArrayList<>();
        MongoCollection collection_wanted  = getJo().getCollection("Book"); // Retrieving
        Iterator<Book_New> all_books = collection_wanted.find().as(Book_New.class).iterator();

        while (all_books.hasNext())
        {
            new_books_list.add(all_books.next());
        }

        return new_books_list;
    }
    public void updateBook_of_Humans(Book_New bk_new) {
        MongoCollection students = getJo().getCollection("Student");

        ArrayList<Student_New> students_new = getAllStudents();
        for (int i = 0; i < students_new.size(); i++){
            ArrayList<Book_New> books_new = getBooks_of_Student(students_new.get(i).getUserID());

            for (int j = 0; j < books_new.size(); j++){
                if(bk_new.getMy_id().equals(books_new.get(j).getMy_id()))
                {
                    books_new.get(j).setBookName(bk_new.getBookName());
                }
            }

            List<IBook> books_inter = new ArrayList<>();
            for (int m = 0 ; m < books_new.size(); m++)
            {
                books_inter.add(books_new.get(m));
            }

            students_new.get(i).setBooks(books_inter);
            students.save(students_new.get(i));
        }
    }
    public void updateBook(Book book){
        try{
            MongoCollection books = getJo().getCollection("Book");
            DBObject query_book = QueryBuilder.start("bookID").is(book.getBookID()).get();
            Book_New bk = books.findOne(query_book.toString()).as(Book_New.class);
            if (bk != null){
                bk.setBookName(book.getBookName());

                updateBook_of_Humans(bk);
                books.save(bk);
            }
            else{
                System.out.println("Book does not exist in database.");
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong. " + e.toString());
        }
    }




}
