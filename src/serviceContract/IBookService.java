package serviceContract;

public interface IBookService {
    boolean isPurchasable();

    double purchase(int quantity, String email, String address);
}

