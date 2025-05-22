package utils;

import models.BaseOrder;

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

}
