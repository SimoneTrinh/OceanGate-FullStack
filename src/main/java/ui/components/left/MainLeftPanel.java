package ui.components.left;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import models.OrderBookEntry;
import models.TradeHistory;
import services.BinanceStreaming;
import services.OrderMatching;
import ui.UIConfiguration;
import ui.components.center.OrderPanel;
import ui.components.center.TradeTablePanel;
import utils.Constants;
import utils.LocalStorage;
import utils.NumberConversion;

import javax.swing.*;
import java.awt.*;
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
        setLayout(new BorderLayout());

        add(renderHeaderPanel(), BorderLayout.NORTH);

        JPanel stat = new JPanel();
        stat.setLayout(new BoxLayout(stat, BoxLayout.Y_AXIS));
        stat.add(renderOrderTable(SELL_TABLE));
        stat.add(renderOpenPrice());
        stat.add(renderOrderTable(BUY_TABLE));

        JScrollPane scrollPane = new JScrollPane(stat);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setColumnHeaderView(book.get(SELL_TABLE).getTableHeader()); // show table header, scroll panel will hide headers because conflict between box & scroll Y Axis

        add(scrollPane, BorderLayout.CENTER);
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

        table.getSelectionModel().addListSelectionListener(e -> {
            // Ignore extra messages while adjusting
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    System.out.print("Selected row " + row + ": ");
                    for (int col = 0; col < table.getColumnCount(); col++) {
                        System.out.print(table.getValueAt(row, col) + " ");
                    }
                    System.out.println();

                    if (tableID.equals(BUY_TABLE)) {
                        String orderType = OrderPanel.comboBoxes.get(OrderPanel.BUY_TYPE).getSelectedItem().toString();
                        if (orderType.equals(OrderPanel.MARKET_ORDER)) { // Market order == get price of market
                            OrderPanel.textFields.get(OrderPanel.BUY_PRICE_FIELD).setText(table.getValueAt(row, 0).toString());
                        } else if (orderType.equals(OrderPanel.LIMIT_ORDER)) { // Limit order == get amount of market
                            OrderPanel.textFields.get(OrderPanel.BUY_AMOUNT_FIELD).setText(table.getValueAt(row, 1).toString());
                        }
                    } else if (tableID.equals(SELL_TABLE)) {
                        String orderType = OrderPanel.comboBoxes.get(OrderPanel.SELL_TYPE).getSelectedItem().toString();
                        if (orderType.equals(OrderPanel.MARKET_ORDER)) {
                            OrderPanel.textFields.get(OrderPanel.SELL_PRICE_FIELD).setText(table.getValueAt(row, 0).toString());
                        } else if (orderType.equals(OrderPanel.LIMIT_ORDER)) {
                            OrderPanel.textFields.get(OrderPanel.SELL_AMOUNT_FIELD).setText(table.getValueAt(row, 1).toString());
                        }
                    }
                }


            }
        });

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

        List<OrderBookEntry> listSell = new ArrayList<>();
        JsonArray listBid = jsonMessage.getAsJsonArray("b");
        updateOrderBook(listSell, listBid, SELL_TABLE);
        Constants.BEST_ORDER.put(LocalStorage.BEST_SELL, listSell.get(0));

        List<OrderBookEntry> listBuy = new ArrayList<>();
        JsonArray listAsk = jsonMessage.getAsJsonArray("a");
        updateOrderBook(listBuy, listAsk, BUY_TABLE);
        Constants.BEST_ORDER.put(LocalStorage.BEST_BUY, listBuy.get(0));

        if(OrderPanel.placeOrderController != null){
            List<TradeHistory> listCurrentBuy = OrderPanel.placeOrderController.getAllAvailableOpenOrder("BUY");
            List<TradeHistory> listCurrentSell = OrderPanel.placeOrderController.getAllAvailableOpenOrder("SELL");
            OrderMatching om = new OrderMatching(listCurrentSell, listCurrentBuy, listSell.get(0), listBuy.get(0));
            om.matchingSellSQL();
        }


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
            listBuy.add(new OrderBookEntry(NumberConversion.convertPriceOrderBook(price), NumberConversion.convertAmountOrderBook(amount), NumberConversion.calculateTotalPrice(price, amount)));
        }
        OrderBookTableModel tableModelBuy = new OrderBookTableModel(listBuy);
        book.get(tableType).setModel(tableModelBuy);
    }
}
