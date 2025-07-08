package serviceContract;

import model.Book;

import java.util.List;

public interface IBookManager {
    void addBook(Book book, IBookService bookService);

    void removeBook(String isbn);

    Book getBook(String isbn);

    List<Book> getAllBooks();

    IBookService getBookService(String isbn);
}