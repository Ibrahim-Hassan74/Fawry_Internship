package repository;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import enums.BookType;
import enums.FileType;
import model.Book;
import repositoryContract.IBookRepository;
import service.EBookService;
import service.PaperBookService;
import service.ShowcaseBookService;
import utils.JsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookRepository implements IBookRepository {
    private static final String PATH = "src/data/books.json";
    private List<Book> books;

    public BookRepository() {
        loadBooks();
    }

    private void loadBooks() {
        Type listType = new TypeToken<List<JsonObject>>() {
        }.getType();
        List<JsonObject> rawBooks = JsonHelper.readJson(PATH, listType);
        books = new ArrayList<>();

        if (rawBooks == null) return;

        for (JsonObject obj : rawBooks) {
            String type = obj.get("type").getAsString();

            String isbn = obj.get("isbn").getAsString();
            String title = obj.get("title").getAsString();
            int year = obj.get("year").getAsInt();
            double price = obj.get("price").getAsDouble();

            Book book = null;

            switch (BookType.valueOf(type)) {
                case PAPER -> {
                    int stock = obj.get("stock").getAsInt();
                    book = new PaperBookService(isbn, title, year, price, stock, this);
                }
                case EBOOK -> {
                    FileType fileType = FileType.valueOf(obj.get("fileType").getAsString());
                    book = new EBookService(isbn, title, year, price, fileType);
                }
                case SHOWCASE -> {
                    book = new ShowcaseBookService(isbn, title, year, price);
                }
            }

            book.setType(BookType.valueOf(type));
            books.add(book);
        }
    }


    private void saveBooks() {
        JsonHelper.writeJson(PATH, books);
    }

    @Override
    public List<Book> getAllBooks() {
        if (books == null) loadBooks();
        return books;
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
        saveBooks();
    }

    @Override
    public void removeBook(String isbn) {
        books.removeIf(b -> b.getIsbn().equals(isbn));
        saveBooks();
    }

    @Override
    public Book findBook(String isbn) {
        String newIsbn = isbn.trim();
//        System.out.println(newIsbn + " " + books.size());
        return books.stream()
                .filter(b -> b.getIsbn().trim().equalsIgnoreCase(newIsbn))
                .findFirst()
                .orElse(null);
    }
//    public Book findBook(String isbn) {
//        String newIsbn = isbn.trim();
//        for (Book b : books) {
//            System.out.println("Comparing: '" + b.getIsbn().trim() + "' with '" + newIsbn + "'");
//            if (b.getIsbn().trim().equalsIgnoreCase(newIsbn)) {
//                System.out.println("Match found!");
//                return b;
//            }
//        }
//        System.out.println("No match found.");
//        return null;
//    }

    @Override
    public void updateBook(Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(updatedBook.getIsbn())) {
                books.set(i, updatedBook);
                saveBooks();
                return;
            }
        }
    }

}
