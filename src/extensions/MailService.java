package extensions;

import service.EBookService;

public class MailService {
    public static void sendEBook(EBookService book, String email) {
        System.out.println("Sending ebook [" + book.getTitle() + "] to: " + email);
    }
}