package models;

public class BaseTrade {
    private String type; // buy/sell
    private String baseCurrency;
    private String quoteCurrency;
    private float price;
    private float amount;
    private float filled;
    private String status;

    public BaseTrade(String type, String baseCurrency, String quoteCurrency,
                      float price, float amount, float filled, String status) {
        this.type = type;
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.price = price;
        this.amount = amount;
        this.filled = filled;
        this.status = status;
    }
    public String getType() {
        return type;
    }
    public String getBaseCurrency() {
        return baseCurrency;
    }
    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public float getPrice() {
        return price;
    }
    public float getAmount() {
        return amount;
    }
    public float getFilled() {
        return filled;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
}
