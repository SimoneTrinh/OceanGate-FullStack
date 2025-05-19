package utils;

import java.text.DecimalFormat;

public class NumberConversion {
    public static String convertStringToFloat(String str) {
        float value = Float.parseFloat(str);

        if (value < 1 && value > -1) {
            // Always show leading 0 for numbers < 1
            DecimalFormat smallFormat = new DecimalFormat("0.00");
            return smallFormat.format(value);
        } else {
            // Format with comma for larger numbers
            DecimalFormat regularFormat = new DecimalFormat("#,###.00");
            return regularFormat.format(value);
        }
    }

    public static String convertPriceOrderBook(String str) {
        float value = Float.parseFloat(str);
        DecimalFormat smallFormat = new DecimalFormat("0.00");
        return smallFormat.format(value);
    }

    public static String convertAmountOrderBook(String str) {
        float value = Float.parseFloat(str);
        DecimalFormat smallFormat = new DecimalFormat("0.00000");
        return smallFormat.format(value);
    }

    public static String calculateTotalPrice(String price, String amount) {
        float result = Float.parseFloat(price) * Float.parseFloat(amount);
        DecimalFormat smallFormat = new DecimalFormat("0.00");
        return smallFormat.format(result);
    }
}
