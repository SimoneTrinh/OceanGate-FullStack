package ui.components.left;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

class OrderBookTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Price (USDT)", "Amount (BTC)", "Total"};
    private final List<OrderBookEntry> data;
    public OrderBookTableModel(List<OrderBookEntry> listOrderBookEntry) {
        data = listOrderBookEntry;
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