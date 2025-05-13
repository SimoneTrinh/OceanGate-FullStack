package ui.components.left;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

class OrderBookTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Price (USDT)", "Amount (BTC)", "Total"};
    private final List<OrderBookEntry> data = new ArrayList<>();
    public OrderBookTableModel() {
        data.add(new OrderBookEntry(101845.11, 0.0500));
        data.add(new OrderBookEntry(101844.20, 0.00005));
        data.add(new OrderBookEntry(101844.19, 0.0039));
        data.add(new OrderBookEntry(101843.29, 0.23319));
        data.add(new OrderBookEntry(101843.00, 0.03931));
        data.add(new OrderBookEntry(101842.91, 0.06223));
        data.add(new OrderBookEntry(101840.99, 0.64829));
        data.add(new OrderBookEntry(101840.98, 0.00006));
        data.add(new OrderBookEntry(101840.87, 0.00005));
        data.add(new OrderBookEntry(101840.17, 0.0012));
        data.add(new OrderBookEntry(101840.12, 0.00006));
        data.add(new OrderBookEntry(101840.09, 0.00006));
        data.add(new OrderBookEntry(101840.08, 0.00006));
        data.add(new OrderBookEntry(101840.06, 0.00006));
        data.add(new OrderBookEntry(101840.05, 0.00006));
        data.add(new OrderBookEntry(101840.03, 0.0035));
        data.add(new OrderBookEntry(101840.02, 1.04756));
        data.add(new OrderBookEntry(101840.01, 1.68970)); // Largest sell order
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OrderBookEntry entry = data.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return entry.getPrice();
            }
            case 1 -> {
                return entry.getAmount();
            }
            case 2 -> {
                return entry.getTotal();
            }
            default -> {
                return 0;
            }
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class; // Treat all as strings for rendering purposes
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}