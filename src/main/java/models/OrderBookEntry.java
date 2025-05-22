package models;

public class OrderBookEntry extends BaseOrder {
    private String total;
    public OrderBookEntry(String price, String amount, String total) {
        super(price, amount);
        this.total = total;
    }

    public String getTotal() {
        return total;
    }
}
