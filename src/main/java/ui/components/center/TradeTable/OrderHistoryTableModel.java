package ui.components.center.TradeTable;

import models.TradeHistory;


import javax.swing.table.AbstractTableModel;
import java.util.List;

public class OrderHistoryTableModel extends AbstractTableModel {
    private final String[] columnNames = {
            "ID", "Type", "Base", "Quote", "Price", "Amount", "Filled", "Status", "Created_At"
    };

    private final List<TradeHistory> data;

    public OrderHistoryTableModel(List<TradeHistory> tradeHistoryList) {
        data = tradeHistoryList;
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
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TradeHistory entry = data.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return entry.getId();
            }
            case 1 -> {
                return entry.getType();
            }
            case 2 -> {
                return entry.getBaseCurrency();
            }
            case 3 -> {
                return entry.getQuoteCurrency();
            }
            case 4 -> {
                return entry.getPrice();
            }
            case 5 -> {
                return entry.getAmount();
            }
            case 6 -> {
                return entry.getFilled();
            }
            case 7 -> {
                return entry.getStatus();
            }
            case 8 -> {
                return entry.getDate();
            }
            default -> {
                return 0;
            }
        }
    }
}
