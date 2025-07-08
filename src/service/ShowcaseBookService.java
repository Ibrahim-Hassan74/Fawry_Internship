package service;

import model.Book;
import serviceContract.IBookService;

public class ShowcaseBookService extends Book implements IBookService {
    public ShowcaseBookService(String isbn, String title, int year, double price) {
        super(isbn, title, year, price);
    }

    @Override
    public boolean isPurchasable() {
        return false;
    }

    @Override
    public double purchase(int quantity, String email, String address) {
        throw new UnsupportedOperationException("Showcase book is not for sale");
    }
}