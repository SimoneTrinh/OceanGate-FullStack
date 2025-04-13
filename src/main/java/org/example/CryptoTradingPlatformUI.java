package org.example;

import javax.swing.*;
import java.awt.*;

public class CryptoTradingPlatformUI extends JPanel {
    public CryptoTradingPlatformUI() {
        // Set up the main frame
//        setTitle("Crypto Trading Platform");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
//        setLocationRelativeTo(null); // Center the window

        // Main layout using BorderLayout
        setLayout(new BorderLayout());

        // Top panel for logo, symbol dropdown, resolution dropdown, and user menu
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createLineBorder(Color.RED)); // Red border for visibility

        // Logo (left side of top panel)
        JLabel logoLabel = new JLabel("LOGO", SwingConstants.CENTER);
        logoLabel.setPreferredSize(new Dimension(100, 50));
        topPanel.add(logoLabel, BorderLayout.WEST);

        // Center of top panel for dropdowns
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> symbolDropdown = new JComboBox<>(new String[]{"BTC/USD", "ETH/USD", "XRP/USD"});
        JComboBox<String> resolutionDropdown = new JComboBox<>(new String[]{"1m", "5m", "15m", "1h"});
        dropdownPanel.add(new JLabel("Symbol:"));
        dropdownPanel.add(symbolDropdown);
        dropdownPanel.add(new JLabel("Resolution:"));
        dropdownPanel.add(resolutionDropdown);
        topPanel.add(dropdownPanel, BorderLayout.CENTER);

        // User menu (right side of top panel)
        JButton userMenuButton = new JButton("User Menu");
        topPanel.add(userMenuButton, BorderLayout.EAST);

        // Add top panel to the frame
        add(topPanel, BorderLayout.NORTH);

        // Center panel with a split pane for symbol info, order book, chart, and buy/sell
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setDividerLocation(200); // Initial divider position

        // Left panel for symbol info and order book (vertical split)
        JSplitPane leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        leftSplitPane.setDividerLocation(150);

        // Symbol info panel (like Binance)
        JPanel symbolInfoPanel = new JPanel(new BorderLayout());
        symbolInfoPanel.setBorder(BorderFactory.createTitledBorder("Symbol Info (Binance)"));
        JTextArea symbolInfoText = new JTextArea("Symbol: ETH/USD\nPrice: $15,362.24\n24h Change: +5%");
        symbolInfoText.setEditable(false);
        symbolInfoPanel.add(new JScrollPane(symbolInfoText), BorderLayout.CENTER);
        leftSplitPane.setTopComponent(symbolInfoPanel);

        // Order book panel
        JPanel orderBookPanel = new JPanel(new BorderLayout());
        orderBookPanel.setBorder(BorderFactory.createTitledBorder("Order Book"));
        JTextArea orderBookText = new JTextArea("Buy Orders:\n$15,350 - 2.5 ETH\n$15,300 - 1.8 ETH\n\nSell Orders:\n$15,400 - 1.2 ETH\n$15,500 - 0.9 ETH");
        orderBookText.setEditable(false);
        orderBookPanel.add(new JScrollPane(orderBookText), BorderLayout.CENTER);
        leftSplitPane.setBottomComponent(orderBookPanel);

        // Set minimum and maximum width for the left panel (Symbol Info and Order Book)
        leftSplitPane.setMinimumSize(new Dimension(150, 0)); // Min width 150px
        leftSplitPane.setMaximumSize(new Dimension(300, Integer.MAX_VALUE)); // Max width 300px
        mainSplitPane.setLeftComponent(leftSplitPane);

        // Right panel for chart and buy/sell (horizontal split)
        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        rightSplitPane.setDividerLocation(800); // Initial divider position

        // Chart panel
        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBorder(BorderFactory.createTitledBorder("Chart"));
        JLabel chartPlaceholder = new JLabel("Chart Placeholder", SwingConstants.CENTER);
        chartPanel.add(chartPlaceholder, BorderLayout.CENTER);
        chartPanel.setMinimumSize(new Dimension(500, 0)); // Min width 500px for chart
        chartPanel.setMaximumSize(new Dimension(900, Integer.MAX_VALUE)); // Max width 900px for chart
        rightSplitPane.setLeftComponent(chartPanel);

        // Buy/Sell panel
        JPanel buySellPanel = new JPanel(new BorderLayout());
        buySellPanel.setBorder(BorderFactory.createTitledBorder("Buy/Sell"));
        buySellPanel.setMinimumSize(new Dimension(200, 0)); // Min width 200px for Buy/Sell
        buySellPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE)); // Max width 400px for Buy/Sell

        // Tabs for Buy and Sell
        JTabbedPane buySellTabs = new JTabbedPane();
        buySellTabs.setBackground(new Color(0, 100, 0)); // Green for Buy tab
        buySellTabs.setForeground(Color.WHITE);

        // Buy Tab
        JPanel buyPanel = new JPanel(new GridBagLayout());
        buyPanel.setBackground(new Color(30, 30, 50)); // Dark background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Trade Type Dropdown
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        buyPanel.add(new JLabel("Trade Type", SwingConstants.LEFT), gbc);
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        JComboBox<String> tradeTypeDropdown = new JComboBox<>(new String[]{"Limit Order", "Market Order"});
        buyPanel.add(tradeTypeDropdown, gbc);

        // Price Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        buyPanel.add(new JLabel("Price"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        JTextField priceField = new JTextField("15362.24");
        buyPanel.add(priceField, gbc);
        gbc.gridx = 4;
        gbc.gridwidth = 1;
        buyPanel.add(new JLabel("USDT"), gbc);

        // Amount Field
        gbc.gridx = 0;
        gbc.gridy = 2;
        buyPanel.add(new JLabel("Amount"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        JTextField amountField = new JTextField("0.00");
        buyPanel.add(amountField, gbc);
        gbc.gridx = 4;
        gbc.gridwidth = 1;
        buyPanel.add(new JLabel("ETH"), gbc);

        // Percentage Buttons
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        buyPanel.add(new JButton("0%"), gbc);
        gbc.gridx = 1;
        buyPanel.add(new JButton("25%"), gbc);
        gbc.gridx = 2;
        buyPanel.add(new JButton("50%"), gbc);
        gbc.gridx = 3;
        buyPanel.add(new JButton("75%"), gbc);
        gbc.gridx = 4;
        buyPanel.add(new JButton("100%"), gbc);

        // Total Field
        gbc.gridx = 0;
        gbc.gridy = 4;
        buyPanel.add(new JLabel("Total"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        JTextField totalField = new JTextField("0");
        totalField.setEditable(false);
        buyPanel.add(totalField, gbc);
        gbc.gridx = 4;
        gbc.gridwidth = 1;
        buyPanel.add(new JLabel("USDT"), gbc);

        // Available Balance and Deposit Link
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JLabel availableLabel = new JLabel("AVL 2.25 USDT");
        availableLabel.setForeground(Color.CYAN);
        buyPanel.add(availableLabel, gbc);
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        JLabel depositLabel = new JLabel("<html><u>Make a Deposit</u></html>");
        depositLabel.setForeground(Color.CYAN);
        buyPanel.add(depositLabel, gbc);

        // Post Only and GTC Options
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        JCheckBox postOnlyCheckBox = new JCheckBox("Post Only");
        buyPanel.add(postOnlyCheckBox, gbc);
        gbc.gridx = 3;
        gbc.gridwidth = 2;
        JComboBox<String> gtcDropdown = new JComboBox<>(new String[]{"GTC", "IOC", "FOK"});
        buyPanel.add(gtcDropdown, gbc);

        // Buy ETH Button
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 5;
        JButton buyButton = new JButton("Buy ETH");
        buyButton.setBackground(new Color(0, 150, 0)); // Green button
        buyButton.setForeground(Color.WHITE);
        buyPanel.add(buyButton, gbc);

        buySellTabs.addTab("Buy", buyPanel);

        // Sell Tab (placeholder)
        JPanel sellPanel = new JPanel(new GridBagLayout());
        sellPanel.setBackground(new Color(30, 30, 50)); // Dark background
        JLabel sellLabel = new JLabel("Sell Panel (Similar to Buy)", SwingConstants.CENTER);
        sellPanel.add(sellLabel);
        buySellTabs.addTab("Sell", sellPanel);

        buySellPanel.add(buySellTabs, BorderLayout.CENTER);
        rightSplitPane.setRightComponent(buySellPanel);

        mainSplitPane.setRightComponent(rightSplitPane);

        // Add main split pane to the center of the frame
        add(mainSplitPane, BorderLayout.CENTER);

        // Bottom panel for open/closed orders with fixed height and scrollable content
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Open/Closed Orders"));
        bottomPanel.setPreferredSize(new Dimension(0, 150)); // Fixed height of 150 pixels

        JTabbedPane ordersTab = new JTabbedPane();

        // Open orders tab
        JTextArea openOrdersText = new JTextArea();
        openOrdersText.setEditable(false);
        for (int i = 1; i <= 20; i++) {
            openOrdersText.append("Order #" + i + ": Buy " + (i * 0.1) + " ETH @ $15," + (300 + i * 10) + "\n");
        }
        JScrollPane openOrdersScroll = new JScrollPane(openOrdersText);
        openOrdersScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        openOrdersScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        ordersTab.addTab("Open Orders", openOrdersScroll);

        // Closed orders tab
        JTextArea closedOrdersText = new JTextArea();
        closedOrdersText.setEditable(false);
        for (int i = 1; i <= 20; i++) {
            closedOrdersText.append("Order #" + i + ": Sell " + (i * 0.2) + " ETH @ $15," + (500 + i * 10) + " (Closed)\n");
        }
        JScrollPane closedOrdersScroll = new JScrollPane(closedOrdersText);
        closedOrdersScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        closedOrdersScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        ordersTab.addTab("Closed Orders", closedOrdersScroll);

        bottomPanel.add(ordersTab, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
