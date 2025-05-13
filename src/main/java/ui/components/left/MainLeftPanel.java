package ui.components.left;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import services.BinanceStreaming;
import ui.UIConfiguration;
import utils.NumberConversion;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainLeftPanel extends JPanel implements BinanceStreaming.MessageListener {
    public static final Map<String, JTable> book = new HashMap<>();
    public static final Map<String, JLabel> labelComponents = new HashMap<>();
    private final String SELL_TABLE = "BidTable";
    private final String BUY_TABLE = "AskTable";
    private final String IGNORE_AMOUNT = "0.00000000";
    private final int MAX_BOOK_ENTITY = 15;
    public static final String CURRENT_PRICE_LABEL = "CurrentPriceLabel";
    public static final String CURRENT_PRICE_USD = "CurrentPriceUSD";


    public MainLeftPanel(LayoutManager layout) {
        super(layout);
        setPreferredSize(new Dimension(UIConfiguration.MAIN_LEFT_WIDTH, UIConfiguration.MAIN_LEFT_HEIGHT));
        BinanceStreaming.getInstance().addListener(this);

        JPanel stat = new JPanel();
        stat.setLayout(new BoxLayout(stat, BoxLayout.Y_AXIS));

        stat.add(renderHeaderPanel());
        stat.add(renderOrderTable(SELL_TABLE));
        stat.add(renderOpenPrice());
        stat.add(renderOrderTable(BUY_TABLE));

        JScrollPane scrollPane = new JScrollPane(stat);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);
    }

    private JPanel renderHeaderPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Order Book");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        topPanel.setPreferredSize(new Dimension(0, 20));
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.setPreferredSize(new Dimension(0, 30));
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        return topPanel;
    }

    private JTable renderOrderTable(String tableID) {
        JTable table = new JTable();
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Modern font
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        book.put(tableID, table);
        return table;
    }

    private JPanel renderOpenPrice() {
        JPanel panel = new JPanel(new FlowLayout());
        JLabel currentPrice = new JLabel("101,852.00");
        JLabel currentPriceUSD = new JLabel("101,852.00");
        panel.add(currentPrice);
        panel.add(currentPriceUSD);
        labelComponents.put(CURRENT_PRICE_LABEL, currentPrice);
        labelComponents.put(CURRENT_PRICE_USD, currentPriceUSD);
        panel.setPreferredSize(new Dimension(0, 36));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        return panel;
    }

    @Override
    public String getFlag() {
        return "depthUpdate";
    }

    @Override
    public void onMessageReceived(JsonObject jsonMessage) {
        // object mapping to panels
        System.out.println("depth");

//        System.out.println(jsonMessage.toString());

        List<OrderBookEntry> listSell = new ArrayList<>();
        JsonArray listBid = jsonMessage.getAsJsonArray("b");
        updateOrderBook(listSell, listBid, SELL_TABLE);

        List<OrderBookEntry> listBuy = new ArrayList<>();
        JsonArray listAsk = jsonMessage.getAsJsonArray("a");
        updateOrderBook(listBuy, listAsk, BUY_TABLE);

        System.out.println("List sell: " + listSell.size() + " List buy: " + listBuy.size());
    }

    private void updateOrderBook(List<OrderBookEntry> listBuy, JsonArray listAsk, String tableType) {
        for (int i = 0; i < listAsk.size(); i++) {
            JsonArray data = listAsk.get(i).getAsJsonArray();
            String price = data.get(0).getAsString();
            String amount = data.get(1).getAsString();
            if (amount.equals(IGNORE_AMOUNT)) { // ignore amount 0.00000000
                continue;
            } else if (listBuy.size() == MAX_BOOK_ENTITY) {
                break;
            }
            listBuy.add(new OrderBookEntry(NumberConversion.convertPriceOrderBook(price), NumberConversion.convertAmountOrderBook(amount), calculateTotal(price, amount)));
        }
        OrderBookTableModel tableModelBuy = new OrderBookTableModel(listBuy);
        book.get(tableType).setModel(tableModelBuy);
    }

    private String calculateTotal(String price, String amount) {
        float result = Float.parseFloat(price) * Float.parseFloat(amount);
        DecimalFormat smallFormat = new DecimalFormat("0.00");
        return smallFormat.format(result);
    }
}
