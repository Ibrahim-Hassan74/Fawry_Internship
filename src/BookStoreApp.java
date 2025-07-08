import enums.BookType;
import enums.FileType;
import model.*;
import repository.BookRepository;
import repositoryContract.IBookRepository;
import service.BookManager;
import service.EBookService;
import service.PaperBookService;
import service.ShowcaseBookService;
import serviceContract.IBookManager;
import serviceContract.IBookService;

import java.util.Scanner;

public class BookStoreApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final IBookRepository repository = new BookRepository();
    private static final IBookManager manager = new BookManager(repository);

    public static void run() {
        while (true) {
            delayedPrint("\nWelcome to the Book Store!");
            delayedPrint("1. Add Book");
            delayedPrint("2. Remove Book");
            delayedPrint("3. Buy Book");
            delayedPrint("4. Show All Books");
            delayedPrint("0. Exit");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addBook();
                case "2" -> removeBook();
                case "3" -> buyBook();
                case "4" -> showBooks();
                case "0" -> {
                    delayedPrint("Bye!");
                    return;
                }
                default -> delayedPrint("Invalid option.");
            }
        }
    }

    private static void addBook() {
        try {
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine().trim();

            if (manager.getBook(isbn) != null) {
                delayedPrint("A book with this ISBN already exists.");
                return;
            }

            System.out.print("Title: ");
            String title = scanner.nextLine().trim();

            System.out.print("Author: ");
            String author = scanner.nextLine().trim();

            System.out.print("Year: ");
            int year = Integer.parseInt(scanner.nextLine());
            if (year <= 0) {
                delayedPrint("Year must be positive.");
                return;
            }

            System.out.print("Price: ");
            double price = Double.parseDouble(scanner.nextLine());
            if (price < 0) {
                delayedPrint("Price can't be negative.");
                return;
            }

            System.out.print("Book Type (PAPER, EBOOK, SHOWCASE): ");
            BookType type;
            try {
                type = BookType.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                delayedPrint("Invalid book type. Must be PAPER, EBOOK, or SHOWCASE.");
                return;
            }

            Book book = null;
            IBookService service = null;

            switch (type) {
                case PAPER -> {
                    System.out.print("Stock: ");
                    int stock = Integer.parseInt(scanner.nextLine());
                    if (stock < 0) {
                        delayedPrint("Stock can't be negative.");
                        return;
                    }
                    PaperBookService paper = new PaperBookService(isbn, title, year, price, stock, repository);
                    book = paper;
                    service = paper;
                }
                case EBOOK -> {
                    System.out.print("FileType (PDF, EPUB, MOBI): ");
                    FileType fileType;
                    try {
                        fileType = FileType.valueOf(scanner.nextLine().trim().toUpperCase());
                    } catch (IllegalArgumentException ex) {
                        delayedPrint("Invalid file type. Must be PDF, EPUB, or MOBI.");
                        return;
                    }
                    EBookService ebook = new EBookService(isbn, title, year, price, fileType);
                    book = ebook;
                    service = ebook;
                }
                case SHOWCASE -> {
                    ShowcaseBookService showcase = new ShowcaseBookService(isbn, title, year, price);
                    book = showcase;
                    service = showcase;
                }
            }

            book.setType(type);
            manager.addBook(book, service);
            delayedPrint(String.format("Book [%s] \"%s\" added successfully.", isbn, title));
        } catch (NumberFormatException e) {
            delayedPrint("Invalid number input: " + e.getMessage());
        } catch (Exception e) {
            delayedPrint("Error adding book: " + e.getMessage());
        }
    }

    private static void removeBook() {
        showBooks();
        System.out.print("Enter ISBN to remove: ");
        String isbn = scanner.nextLine();

        if (manager.getBook(isbn) == null) {
            delayedPrint("Book not found.");
            return;
        }

        manager.removeBook(isbn);
        delayedPrint("Book removed.");
    }

    private static void buyBook() {
        showBooks();
        System.out.print("Enter ISBN to buy: ");
        String isbn = scanner.nextLine();

        Book book = manager.getBook(isbn);
        IBookService service = manager.getBookService(isbn);

        if (book == null || service == null) {
            delayedPrint("Book not found.");
            return;
        }

        if (!service.isPurchasable()) {
            delayedPrint("Book is not available for purchase.");
            return;
        }

        try {
            System.out.print("Enter quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Address: ");
            String address = scanner.nextLine();

            double total = service.purchase(quantity, email, address);
            delayedPrint("Purchase successful. Total: $" + total);
        } catch (Exception ex) {
            delayedPrint("Purchase failed: " + ex.getMessage());
        }
    }

    private static void showBooks() {
        delayedPrint("\n=== Inventory ===");
        for (Book book : manager.getAllBooks()) {
            delayedPrint(String.format("ISBN: %s | Title: %s | Year: %d | Price: %.2f",
                    book.getIsbn(), book.getTitle(), book.getYear(), book.getPrice()));
        }
    }

    private static void delayedPrint(String message) {
        System.out.println(message);
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
