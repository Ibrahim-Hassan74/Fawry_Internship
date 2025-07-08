package service;

import model.Book;
import repositoryContract.IBookRepository;
import serviceContract.IBookManager;
import serviceContract.IBookService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookManager implements IBookManager {
    private final IBookRepository repository;
    private final Map<String, IBookService> serviceMap = new HashMap<>();

    public BookManager(IBookRepository repository) {
        this.repository = repository;
        var res = repository.getAllBooks();

        for (Book book : res) {
            IBookService service = null;

            if (book instanceof PaperBookService) {
                ((PaperBookService) book).setRepository(repository);
                service = (PaperBookService) book;
            } else if (book instanceof EBookService) {
                service = (EBookService) book;
            } else if (book instanceof ShowcaseBookService) {
                service = (ShowcaseBookService) book;
            }

            if (service != null) {
                serviceMap.put(book.getIsbn().trim(), service);
            }
        }
    }


    @Override
    public void addBook(Book book, IBookService bookService) {
        repository.addBook(book);
        serviceMap.put(book.getIsbn().trim(), bookService);
    }

    @Override
    public void removeBook(String isbn) {
        repository.removeBook(isbn);
        serviceMap.remove(isbn.trim());
    }

    @Override
    public Book getBook(String isbn) {
        return repository.findBook(isbn);
    }

    @Override
    public List<Book> getAllBooks() {
        return repository.getAllBooks();
    }

    @Override
    public IBookService getBookService(String isbn) {
        return serviceMap.get(isbn.trim());
    }
}