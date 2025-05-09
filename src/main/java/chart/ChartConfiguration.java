package chart;

public class ChartConfiguration {
    public static final String CHART_ENDPOINT = "http://localhost";
    public static final int CHART_PORT = 8080;
    public static final String CHART_INDEX_HTML = "/index.html";
    public static final String CHART_QUERY_SYMBOL = "symbol=";
    public static final String CHART_QUERY_INTERVAL = "interval=";
    public static final String CHART_QUERY_THEME = "theme=";

    public static final String CHART_BASE_URL = CHART_ENDPOINT + ":" + CHART_PORT + CHART_INDEX_HTML;


    /* Example:
    symbol = btcusdt
    interval = ["1", "15", "60", "240", "1D"];
    theme = dark / light
    * */
    public static String generateQuery(String symbol, String interval, String theme){
        String querySymbol = ChartConfiguration.CHART_QUERY_SYMBOL + symbol.toUpperCase();
        String queryInterval = ChartConfiguration.CHART_QUERY_INTERVAL + interval;
        String queryTheme = ChartConfiguration.CHART_QUERY_THEME + theme;
        return "?" + querySymbol + "&" + queryInterval + "&" + queryTheme;
    }

}
