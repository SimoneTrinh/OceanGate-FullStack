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
}
