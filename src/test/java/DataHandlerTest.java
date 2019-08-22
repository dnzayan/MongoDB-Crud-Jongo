
import Implementations.*;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import org.jongo.MongoCollection;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class DataHandlerTest extends DataHandler{
    ////////////// STUDENT OPERATIONS
    @Test
    public void addStudent(){
        UUID st_ID = UUID.randomUUID();
        System.out.println(st_ID);
        ArrayList<Book_New> books = getAllBooks();

        List<IBook> books_inter = new ArrayList<>();
        for (int i = 0 ; i< books.size(); i++)
        {
            books_inter.add(books.get(i));
        }

        Student st = new Student(st_ID,"ST_3", books_inter);

        try{
            MongoCollection students = getJo().getCollection("Student");
            DBObject query_student = QueryBuilder.start("userID").is(st.getUserID()).get();
            Student_New st_new = students.findOne(query_student.toString()).as(Student_New.class);
            if (st_new == null){
                students.save(st);
            }
            else{
                System.out.println("Student is already in database.");
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong. " + e.toString());
        }

    }
    @Test
    public void getStudent(){
        UUID my_id = UUID.fromString("4617a04e-ee76-45ca-ae00-14c7c2b81361");
        try{
            MongoCollection students = getJo().getCollection("Student");
            DBObject query_student = QueryBuilder.start("userID").is(my_id).get();
            Student_New student_new = students.findOne(query_student.toString()).as(Student_New.class);

            if (student_new != null){
                System.out.println(student_new);
            }
            else{
                System.out.println("Student does not exist in database.");
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong. " + e.toString());
        }
    }
    @Test
    public void deleteStudent() {
        UUID my_uuid = UUID.fromString("cc02d230-0141-4efe-ba96-e0e5a32fe95c");
        try{
            MongoCollection students = getJo().getCollection("Student");
            DBObject query_student = QueryBuilder.start("userID").is(my_uuid).get();
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
    @Test
    public void getAllStudents_Test() {
        ArrayList<Student_New> new_student_list = new ArrayList<>();
        MongoCollection collection_wanted  = getJo().getCollection("Student"); // Retrieving
        Iterator<Student_New> all_students = collection_wanted.find().as(Student_New.class).iterator();


        while (all_students.hasNext())
        {
            new_student_list.add(all_students.next());
        }

        int i = 0;
        for (; i < new_student_list.size(); i++)
        {
            System.out.println(new_student_list.get(i).toString());
        }
    }
    @Test
    public void updateStudent(){
        //UUID st_ID = UUID.fromString("b2160a3f-dbd7-4ad3-b458-c86b756c1a6c");
        UUID st_ID2 = UUID.randomUUID();

        ArrayList<Book_New> books = getAllBooks();

        List<IBook> books_inter = new ArrayList<>();
        for (int i = 0 ; i< books.size(); i++)
        {
            books_inter.add(books.get(i));
        }

        try{
            MongoCollection students = getJo().getCollection("Student");
            DBObject query_student = QueryBuilder.start("userID").is(st_ID2).get();
            Student_New st_new = students.findOne(query_student.toString()).as(Student_New.class);
            if (st_new != null){
                st_new.setBooks(books_inter);
                st_new.setLoginName("ST_4");

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
    @Test
    public void getBooks_of_Student(){
        UUID my_id = UUID.fromString("72d6c3b6-cb97-4c6c-bfe5-d31ebc1efc60");
        Student_New my_st = getStudent(my_id);

        ArrayList<Book_New> books_new = new ArrayList<>();

        try {
            List<IBook> books_inter = my_st.getBooks();
            for (int i = 0; i < books_inter.size(); i++) {
                books_new.add((Book_New) books_inter.get(i));
                System.out.println(books_inter.get(i));
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong. " + e.toString());
        }
    }
    @Test
    public void addBook_to_Student(){
        DBObject query_student = QueryBuilder.start("userID").is(UUID.fromString("582cac73-8f55-478f-b4bf-cdff0383d981")).get();
        DBObject query_book = QueryBuilder.start("bookID").is(UUID.fromString("034f022a-f0b3-407b-8957-3d7c973078fb")).get();

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
    @Test
    public void deleteBook_from_Student(){
        DBObject query_student = QueryBuilder.start("userID").is(UUID.fromString("582cac73-8f55-478f-b4bf-cdff0383d981")).get();
        DBObject query_book = QueryBuilder.start("bookID").is(UUID.fromString("034f022a-f0b3-407b-8957-3d7c973078fb")).get();

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
    @Test
    public void addBook() {
        UUID my_uuid = UUID.randomUUID();
        Book book = new Book(my_uuid,"Book_5");
        //book.setBookID(UUID.fromString("6a15cf2c-e8c6-4f0a-a29e-543ac011dc95"));
        System.out.println(my_uuid);
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
    @Test
    public void getBook(){
        UUID my_id = UUID.fromString("891e831c-86a8-4a60-a151-d835b40e959a");
        try{
            MongoCollection books = getJo().getCollection("Book");
            DBObject query_book = QueryBuilder.start("bookID").is(my_id).get();
            Book_New book_new = books.findOne(query_book.toString()).as(Book_New.class);

            if (book_new != null){
                System.out.println(book_new);
            }
            else{
                System.out.println("Book does not exist in database.");
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong. " + e.toString());
        }
    }
    @Test
    public void deleteBook() {
        UUID my_uuid = UUID.fromString("2315fb31-2d8d-4639-9a87-2cbb51f328ae");
        try{
            MongoCollection books = getJo().getCollection("Book");
            DBObject query_book = QueryBuilder.start("bookID").is(my_uuid).get();
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
    @Test
    public void getAllBooks_Test() {
        ArrayList<Book_New> new_books_list = new ArrayList<>();
        MongoCollection collection_wanted  = getJo().getCollection("Book"); // Retrieving
        Iterator<Book_New> all_books = collection_wanted.find().as(Book_New.class).iterator();


        while (all_books.hasNext())
        {
            new_books_list.add(all_books.next());
        }

        int i = 0;
        for (; i < new_books_list.size(); i++)
        {
            System.out.println(new_books_list.get(i).toString());
        }
    }
    @Test
    public void updateBook(){
        UUID my_uuid = UUID.fromString("e0ead93d-4045-49b7-b7a1-527c5c60094c");

        try{
            MongoCollection books = getJo().getCollection("Book");
            DBObject query_book = QueryBuilder.start("bookID").is(my_uuid).get();
            Book_New bk = books.findOne(query_book.toString()).as(Book_New.class);
            if (bk != null){
                bk.setBookName("Book_4");

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
