package models;

public class TradeHistory extends TradeOrder {
    private String date;

    public TradeHistory(int orderID, String type, String baseCurrency, String quoteCurrency,
                        float price, float amount, float filled, String status, String date) {
        super(orderID, type, baseCurrency, quoteCurrency, price, amount, filled, status);
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
