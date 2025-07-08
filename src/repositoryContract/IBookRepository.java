package repositoryContract;

import model.Book;

import java.util.List;

public interface IBookRepository {
    List<Book> getAllBooks();

    void addBook(Book book);

    void removeBook(String isbn);

    void updateBook(Book updatedBook);

    Book findBook(String isbn);
}
