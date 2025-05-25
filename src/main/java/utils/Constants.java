package utils;

import models.BaseOrder;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static HashMap<String, String> LOCAL_STORAGE = new HashMap<>();
    public static HashMap<String, Integer> SYMBOL_MAP = new HashMap<>() {{
        put("BTC", 1);
        put("ETH", 2);
        put("USDT", 3);
        put("XRP", 4);
        put("BNB", 5);
    }};

    public static HashMap<String, Integer> ORDER_TYPE = new HashMap<>() {{
        put("BUY", 1);
        put("SELL", 2);
    }};

    public static HashMap<String, Integer> ORDER_STATUS = new HashMap<>() {{
        put("OPEN", 1);
        put("CLOSED", 2);
        put("PARTIALLY_FILLED", 3);
    }};

    public static HashMap<String, BaseOrder> BEST_ORDER = new HashMap<>();

    public static Color COLOR_GRAY = new Color(132, 142, 156);
    public static Color COLOR_RED = new Color(246, 70, 93);
    public static Color COLOR_GREEN = new Color(46, 189, 133);
    public static Color BTN_COLOR_DEFAULT = new Color(30, 35, 41);
}
