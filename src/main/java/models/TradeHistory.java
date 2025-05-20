package models;

public class TradeHistory extends TradeOrder {
    private String date;

    public TradeHistory(int id, String type, String baseCurrency, String quoteCurrency,
                        float price, float amount, float filled, String status, String date) {
        super(id, type, baseCurrency, quoteCurrency, price, amount, filled, status);
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
