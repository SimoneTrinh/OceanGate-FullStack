package models;

public class OrderBookEntry {
    private String price;
    private String amount;
    private String total;


    public OrderBookEntry(String price, String amount, String total) {
        this.price = price;
        this.amount = amount;
        this.total = total;
    }

    public String getPrice() {
        return price;
    }

    public String getAmount() {
        return amount;
    }

    public String getTotal() {
        return total;
    }
}
