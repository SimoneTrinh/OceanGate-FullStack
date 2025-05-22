package models;

public class TradeOrder extends BaseTrade {
    private int orderID;

    public TradeOrder(int orderID, String type, String baseCurrency, String quoteCurrency,
                      float price, float amount, float filled, String status) {
        super(type, baseCurrency, quoteCurrency, price, amount, filled, status);
        this.orderID = orderID;
    }
    public int getOrderID() {
        return orderID;
    }
}
