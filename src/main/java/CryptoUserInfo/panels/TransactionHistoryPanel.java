package CryptoUserInfo.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class TransactionHistoryPanel extends JPanel {
    public TransactionHistoryPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Recent Transactions"));

        String[] columns = {"Coin", "Date", "Status", "Amount"};
        Object[][] data = {
                {"Bitcoin", "09.03 04:35", "Pending", 854},
                {"Tether TRC20", "09.03 04:21", "Completed", 100},
                {"Bitcoin", "08.03 11:59", "Completed", -400.38},
                {"Litecoin", "02.03 04:20", "Completed", -225.52},
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Renderer for "Status"
        table.getColumnModel().getColumn(2).setCellRenderer(new StatusCellRenderer());

        // Renderer for "Amount"
        table.getColumnModel().getColumn(3).setCellRenderer(new AmountCellRenderer());

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // Format currency and color code
    static class AmountCellRenderer extends DefaultTableCellRenderer {
        @Override
        protected void setValue(Object value) {
            double amount = Double.parseDouble(value.toString());
            setText((amount < 0 ? "-" : "+") + NumberFormat.getCurrencyInstance(Locale.US).format(Math.abs(amount)));
            setForeground(amount < 0 ? Color.RED : new Color(0, 128, 0));
        }
    }

    // Color-coded transaction status
    static class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        protected void setValue(Object value) {
            if ("Pending".equals(value)) {
                setForeground(Color.ORANGE.darker());
            } else if ("Completed".equals(value)) {
                setForeground(new Color(0, 128, 0));
            } else {
                setForeground(Color.RED);
            }
            setText(value.toString());
        }
    }
}
