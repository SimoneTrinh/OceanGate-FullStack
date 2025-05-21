package ui.components.center;

import ui.UIConfiguration;
import ui.components.top.TopNavPanel;
import utils.NumberConversion;
import utils.TradingPair;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Objects;

public class OrderPanel extends JPanel {
    public static final HashMap<String, JTextField> textFields = new HashMap<>();
    public static final HashMap<String, JButton> buttons = new HashMap<>();
    public static final HashMap<String, JComboBox<String>> comboBoxes = new HashMap<>();
    public static final String BUY_TYPE = "BUY_TYPE";
    public static final String SELL_TYPE = "SELL_TYPE";
    public static final String MARKET_ORDER = "Market Order";
    public static final String LIMIT_ORDER = "Limit Order";
    private String BUY_PANEL = "BUY_PANEL";
    private String SELL_PANEL = "SELL_PANEL";
    public static final String BUY_PRICE_FIELD = "BUY_PRICE_FIELD";
    public static final String SELL_PRICE_FIELD = "SELL_PRICE_FIELD";
    public static final String BUY_AMOUNT_FIELD = "BUY_AMOUNT_FIELD";
    public static final String SELL_AMOUNT_FIELD = "SELL_AMOUNT_FIELD";
    private String BUY_TOTAL_PRICE_FIELD = "BUY_TOTAL_PRICE_FIELD";
    private String SELL_TOTAL_PRICE_FIELD = "SELL_TOTAL_PRICE_FIELD";
    private String PLACE_BUY_BTN = "PLACE_BUY_BTN";
    private String PLACE_SELL_BTN = "PLACE_SELL_BTN";
    private static JDialog dialog; // workaround for focus


    public OrderPanel(LayoutManager layout) {
        super(layout);
        setPreferredSize(new Dimension(UIConfiguration.ORDER_MENU_MAX_WIDTH, UIConfiguration.ORDER_MENU_HEIGHT));
        setMaximumSize(new Dimension(UIConfiguration.ORDER_MENU_MAX_WIDTH, UIConfiguration.ORDER_MENU_HEIGHT));
        setMinimumSize(new Dimension(UIConfiguration.ORDER_MENU_MIN_WIDTH, UIConfiguration.ORDER_MENU_HEIGHT));
        setBackground(Color.ORANGE);

        // Main layout
        setLayout(new BorderLayout());

        // Navigation buttons to switch cards
        JPanel navPanel = new JPanel();
        JButton buyTabButton = new JButton("Buy");
        JButton sellTabButton = new JButton("Sell");
        navPanel.add(buyTabButton);
        navPanel.add(sellTabButton);
        add(navPanel, BorderLayout.NORTH);

        // CardLayout panel
        JPanel cardPanel = new JPanel(new CardLayout());

        // Add cards
        cardPanel.add(renderBuySellPanel(BUY_PANEL), "BUY");
        cardPanel.add(renderBuySellPanel(SELL_PANEL), "SELL");

        add(cardPanel, BorderLayout.CENTER);

        // Card switch logic
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        buyTabButton.addActionListener(e -> cl.show(cardPanel, "BUY"));
        sellTabButton.addActionListener(e -> cl.show(cardPanel, "SELL"));

        // logic switch dropdown
        TopNavPanel.symbolDropdown.addActionListener(e -> {
            String symbol = TopNavPanel.symbolDropdown.getSelectedItem().toString();
            TradingPair pair = TradingPair.splitSymbol(symbol);
            buttons.get(PLACE_BUY_BTN).setText("Buy " + pair.getBaseAsset());
            buttons.get(PLACE_SELL_BTN).setText("Sell " + pair.getBaseAsset());
        });

    }

    private JPanel renderBuySellPanel(String panelType) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        if (panelType.equals(BUY_PANEL)) {
            renderBuySelComponentPanel(panel, BUY_TYPE, BUY_PRICE_FIELD, BUY_AMOUNT_FIELD, BUY_TOTAL_PRICE_FIELD, PLACE_BUY_BTN);
        } else if (panelType.equals(SELL_PANEL)) {
            renderBuySelComponentPanel(panel, SELL_TYPE, SELL_PRICE_FIELD, SELL_AMOUNT_FIELD, SELL_TOTAL_PRICE_FIELD, PLACE_SELL_BTN);
        }
        return panel;
    }

    private void renderBuySelComponentPanel(JPanel panel, String buyType, String buyPriceField, String buyAmountField, String buyTotalPriceField, String placeBuyBtn) {
        panel.add(renderTradeTypeDropDown(buyType));
        panel.add(renderPriceBox(buyPriceField));
        panel.add(renderAmountBox(buyAmountField));
        panel.add(renderPercentageButtons());
        panel.add(renderTotalPrice(buyTotalPriceField));
        panel.add(renderBalanceComponent());
        panel.add(renderPlaceOrderButton(placeBuyBtn));
    }

    private JPanel renderTradeTypeDropDown(String tradeTypeID) {
        JComboBox<String> tradeTypeDropdown = new JComboBox<>(new String[]{LIMIT_ORDER, MARKET_ORDER});
        comboBoxes.put(tradeTypeID, tradeTypeDropdown);
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        jPanel.add(new JLabel("Trade Type"));
        jPanel.add(tradeTypeDropdown);
        return jPanel;
    }

    private JPanel renderPriceBox(String textFieldID) {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        jPanel.add(new JLabel("Price"));
        JTextField priceField = new JTextField("0.00", 10);
        priceField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showInputDialog();
                dialog.dispose();
            }
        });


        priceField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                onTextChanged();
            }

            public void removeUpdate(DocumentEvent e) {
                onTextChanged();
            }

            public void changedUpdate(DocumentEvent e) {
                onTextChanged();
            }

            private void onTextChanged() {
                if (!Objects.equals(priceField.getText(), "")) {
                    if (textFieldID.equals(BUY_PRICE_FIELD)) {
                        String price = priceField.getText();
                        String amount = textFields.get(BUY_AMOUNT_FIELD).getText();
                        String total = NumberConversion.calculateTotalPrice(price, amount);
                        textFields.get(BUY_TOTAL_PRICE_FIELD).setText(total);
                    } else if (textFieldID.equals(SELL_PRICE_FIELD)) {
                        String price = priceField.getText();
                        String amount = textFields.get(SELL_AMOUNT_FIELD).getText();
                        String total = NumberConversion.calculateTotalPrice(price, amount);
                        textFields.get(SELL_TOTAL_PRICE_FIELD).setText(total);
                    }
                }
            }
        });

        textFields.put(textFieldID, priceField);
        jPanel.add(priceField);
        jPanel.add(new JLabel("USDT"));
        return jPanel;
    }

    private JPanel renderAmountBox(String textFieldID) {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        jPanel.add(new JLabel("Amount"));
        JTextField amountField = new JTextField("0.00", 10);
        amountField.setEditable(true);
        amountField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                onTextChanged();
            }

            public void removeUpdate(DocumentEvent e) {
                onTextChanged();
            }

            public void changedUpdate(DocumentEvent e) {
                onTextChanged();
            }

            private void onTextChanged() {
                if (!Objects.equals(amountField.getText(), "")) {
                    if (textFieldID.equals(BUY_AMOUNT_FIELD)) {
                        String price = textFields.get(BUY_PRICE_FIELD).getText();
                        String amount = amountField.getText();
                        String total = NumberConversion.calculateTotalPrice(price, amount);
                        textFields.get(BUY_TOTAL_PRICE_FIELD).setText(total);
                    } else if (textFieldID.equals(SELL_AMOUNT_FIELD)) {
                        String price = textFields.get(SELL_PRICE_FIELD).getText();
                        String amount = amountField.getText();
                        String total = NumberConversion.calculateTotalPrice(price, amount);
                        textFields.get(SELL_TOTAL_PRICE_FIELD).setText(total);
                    }
                }
            }
        });
        textFields.put(textFieldID, amountField);
        jPanel.add(amountField);
        return jPanel;
    }

    private JPanel renderPercentageButtons() {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        jPanel.add(new JButton("0%"));
        jPanel.add(new JButton("25%"));
        jPanel.add(new JButton("50%"));
        jPanel.add(new JButton("75%"));
        jPanel.add(new JButton("100%"));
        return jPanel;
    }

    private JPanel renderTotalPrice(String textFieldID) {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        jPanel.add(new JLabel("Total"));
        JTextField totalField = new JTextField("0.00", 10);
        totalField.setEditable(false);
        jPanel.add(totalField);
        textFields.put(textFieldID, totalField);
        jPanel.add(new JLabel("USDT"));
        return jPanel;
    }

    private JPanel renderBalanceComponent() {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel availableLabel = new JLabel("Available Balance: 2.25 USDT");
        availableLabel.setForeground(Color.CYAN);
        JLabel depositLabel = new JLabel("Make a Deposit");
        depositLabel.setForeground(Color.CYAN);
        jPanel.add(availableLabel);
        jPanel.add(Box.createHorizontalStrut(15));
        jPanel.add(depositLabel);
        return jPanel;
    }

    private JPanel renderPlaceOrderButton(String buttonID) {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        jPanel.setOpaque(false);
        JButton orderButton = new JButton("Sell BTC");

        if (buttonID.equals(PLACE_BUY_BTN)) {
            orderButton.setText("Buy BTC");
        }
        buttons.put(buttonID, orderButton);
        orderButton.setBackground(new Color(0, 150, 0));
        orderButton.setForeground(Color.WHITE);
        orderButton.setPreferredSize(new Dimension(400, 30));
        jPanel.add(orderButton);
        return jPanel;
    }

    public static void showInputDialog() {
        JOptionPane optionPane = new JOptionPane(
                "Enter your name:",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION,
                null,
                null,
                null
        );
        optionPane.setWantsInput(true);
        dialog = optionPane.createDialog(null, "Custom Input Dialog");
        dialog.setModal(false); // workaround non-blocking because cefBrowser will steal focus
        dialog.setVisible(true);

    }
}
