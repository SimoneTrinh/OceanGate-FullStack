package models;

public class BaseOrder {
    private String price;
    private String amount;

    public BaseOrder(String price, String amount) {
        this.price = price;
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public String getAmount() {
        return amount;
    }
}
