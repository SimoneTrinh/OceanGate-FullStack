package ui.components.left;

public class OrderBookEntry {
    private float price;
    private float amount;
    private float total;

    public OrderBookEntry(float price, float amount) {
        this.price = price;
        this.amount = amount;
        this.total = this.price * this.amount;
    }

    public OrderBookEntry(double price, double amount) {
        this.price = (float) price;
        this.amount = (float) amount;
        this.total = this.price * this.amount;
    }

    public float getPrice() {
        return price;
    }

    public float getAmount() {
        return amount;
    }

    public float getTotal() {
        return total;
    }
}
