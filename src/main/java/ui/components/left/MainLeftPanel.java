package ui.components.left;

import ui.UIConfiguration;

import javax.swing.*;
import java.awt.*;

public class MainLeftPanel extends JPanel {
    public MainLeftPanel(LayoutManager layout) {
        super(layout);
        setPreferredSize(new Dimension(UIConfiguration.MAIN_LEFT_WIDTH, UIConfiguration.MAIN_LEFT_HEIGHT));

        JPanel stat = new JPanel();
        stat.setLayout(new BoxLayout(stat, BoxLayout.Y_AXIS));

        stat.add(renderHeaderPanel());
        stat.add(renderOrderTable());
        stat.add(renderOpenPrice());
        stat.add(renderOrderTable());

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

    private JTable renderOrderTable() {
        OrderBookTableModel tableModel = new OrderBookTableModel();
        JTable table = new JTable(tableModel);
        table.setRowHeight(32); // Increase row height for better readability
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Modern font
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }

    private JPanel renderOpenPrice(){
        JPanel panel = new JPanel(new FlowLayout());
        JLabel jLabel1 = new JLabel("101,852.00");
        JLabel jLabel2 = new JLabel("101,852.00");
        panel.add(jLabel1);
        panel.add(jLabel2);
        panel.setPreferredSize(new Dimension(0, 36));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        return panel;
    }
}
