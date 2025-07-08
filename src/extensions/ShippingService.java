package extensions;

import service.PaperBookService;

public class ShippingService {
    public static void sendBook(PaperBookService book, String address) {
        System.out.println("Shipping paper book [" + book.getTitle() + "] to: " + address);
    }
}