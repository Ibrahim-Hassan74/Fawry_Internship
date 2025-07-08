package service;

import extensions.ShippingService;
import model.Book;
import repositoryContract.IBookRepository;
import serviceContract.IBookService;

public class PaperBookService extends Book implements IBookService {
    private int stock;

    private transient IBookRepository repository;

    public PaperBookService(String isbn, String title, int year, double price, int stock, IBookRepository repository) {
        super(isbn, title, year, price);
        this.stock = stock;
        this.repository = repository;
    }

    @Override
    public boolean isPurchasable() {
        return stock > 0;
    }

    @Override
    public double purchase(int quantity, String email, String address) {
        if (stock < quantity) throw new IllegalArgumentException("Not enough stock");
        stock -= quantity;
        ShippingService.sendBook(this, address);
        repository.updateBook(this);
        return getPrice() * quantity;
    }

    public int getStock() {
        return stock;
    }

    public void setRepository(IBookRepository repository) {
        this.repository = repository;
    }

}
