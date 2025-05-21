package models;

public class PlaceOrderPayload extends BaseTrade {

    private int userID;

    public PlaceOrderPayload(int userID, String type, String baseCurrency, String quoteCurrency,
                             float price, float amount, float filled, String status) {
        super(type, baseCurrency, quoteCurrency, price, amount, filled, status);
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

}
