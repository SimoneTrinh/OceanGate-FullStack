package utils;

public class TradingPair {
    private String baseAsset;
    private String quoteAsset;

    public TradingPair(String baseAsset, String quoteAsset) {
        this.baseAsset = baseAsset;
        this.quoteAsset = quoteAsset;
    }

    public String getBaseAsset() {
        return baseAsset;
    }

    public String getQuoteAsset() {
        return quoteAsset;
    }

    public static TradingPair splitSymbol(String symbol) {
        if (symbol.contains("/")) {
            String[] parts = symbol.split("/");
            return new TradingPair(parts[0].toUpperCase(), parts[1].toUpperCase());
        } else {
            return new TradingPair("N/A", "N/A");

        }
    }
}
