package ui.components.center;

import controller.LoadOrderController;
import controller.OrderHistoryController;
import models.TradeHistory;
import models.TradeOrder;
import ui.UIConfiguration;
import ui.components.center.TradeTable.CancelButton;
import ui.components.center.TradeTable.ButtonRenderer;
import ui.components.center.TradeTable.OrderHistoryTableModel;
import ui.components.center.TradeTable.TradeOrderTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class TradeTablePanel extends JPanel {
    private JPanel cardPanel;
    public static LoadOrderController orderController;
    public static OrderHistoryController orderHistoryController;
    private JScrollPane orderPanel;
    private JTable orderTable;
    private JScrollPane orderHistoryPanel;
    private JTable orderHistoryTable;
    private CardLayout cardLayout;

    public TradeTablePanel(LayoutManager layout) {
        super(layout);
        setPreferredSize(new Dimension(UIConfiguration.TRADE_TABLE_WIDTH, UIConfiguration.TRADE_TABLE_HEIGHT));
        setBackground(Color.green);
        try {
            orderController = new LoadOrderController(this);
            orderHistoryController = new OrderHistoryController(this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        orderController.loadOrders(2);
        orderHistoryController.loadHistoryOrders(2);

        // Main layout
        setLayout(new BorderLayout());

        // Navigation buttons to switch cards
        JPanel buttonPanel = new JPanel();
        JButton ordersButton = new JButton("Orders");
        JButton historyButton = new JButton("Order History");
        buttonPanel.add(ordersButton);
        buttonPanel.add(historyButton);
        add(buttonPanel, BorderLayout.NORTH);

        // Card Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add cards
        cardPanel.add(orderPanel, "ORDERS");
        cardPanel.add(orderHistoryPanel, "HISTORY");
        add(cardPanel, BorderLayout.CENTER);

        // Button actions
        ordersButton.addActionListener((ActionEvent e) -> cardLayout.show(cardPanel, "ORDERS"));
        historyButton.addActionListener((ActionEvent e) -> cardLayout.show(cardPanel, "HISTORY"));
    }

    public void createOrdersTable(List<TradeOrder> data) {
        TradeOrderTableModel model = new TradeOrderTableModel(data);
        orderTable = new JTable(model);
        orderTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        orderTable.getColumn("Action").setCellEditor(new CancelButton(new JCheckBox(), model));
        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        orderPanel = scrollPane;
    }

    public void setDataOrderTable(List<TradeOrder> data) {
        TradeOrderTableModel model = new TradeOrderTableModel(data);
        orderTable.setModel(model);
        orderTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        orderTable.getColumn("Action").setCellEditor(new CancelButton(new JCheckBox(), model));
    }


    public void createOrdersHistoryTable(List<TradeHistory> data) {
        OrderHistoryTableModel model = new OrderHistoryTableModel(data);
        orderHistoryTable = new JTable(model);

        JPanel stat = new JPanel();
        stat.setLayout(new BoxLayout(stat, BoxLayout.Y_AXIS));
        stat.add(orderHistoryTable);

        JScrollPane scrollPane = new JScrollPane(stat);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setColumnHeaderView(orderHistoryTable.getTableHeader());
        orderHistoryPanel = scrollPane;
    }

    public void setDataOrderHistoryTable(List<TradeHistory> data) {
        OrderHistoryTableModel model = new OrderHistoryTableModel(data);
        orderHistoryTable.setModel(model);
    }
}
