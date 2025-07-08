package service;

import enums.FileType;
import extensions.MailService;
import model.Book;
import serviceContract.IBookService;

public class EBookService extends Book implements IBookService {
    private FileType fileType;

    public EBookService(String isbn, String title, int year, double price, FileType fileType) {
        super(isbn, title, year, price);
        this.fileType = fileType;
    }


    @Override
    public boolean isPurchasable() {
        return true;
    }

    @Override
    public double purchase(int quantity, String email, String address) {
        if (quantity != 1) throw new IllegalArgumentException("Only one copy of ebook can be purchased");
        MailService.sendEBook(this, email);
        return getPrice();
    }

    public FileType getFileType() {
        return fileType;
    }
}