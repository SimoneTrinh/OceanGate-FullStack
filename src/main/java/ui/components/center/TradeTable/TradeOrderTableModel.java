package ui.components.center.TradeTable;

import models.TradeOrder;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TradeOrderTableModel extends AbstractTableModel {

    private final String[] columnNames = {
            "ID", "Type", "Base", "Quote", "Price", "Amount", "Filled", "Status", "Action"
    };

    private final List<TradeOrder> data;

    public TradeOrderTableModel(List<TradeOrder> tradeOrderList) {
        data = tradeOrderList;
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
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TradeOrder entry = data.get(rowIndex);
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
            default -> {
                return 0;
            }
        }
//        return data.get(row)[col];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Only "Cancel" column is editable
        return columnIndex == 8;
    }

//    @Override
//    public void setValueAt(Object value, int row, int col) {
//        data.get(row)[col] = value;
//        fireTableCellUpdated(row, col);
//    }

    public Object getOrderIdAtRow(int row) {
        return data.get(row).getId();
    }

    public void cancelOrderAtRow(int row) {
        // Example logic: mark status as CANCELLED
        data.get(row).setStatus("CANCELLED");;
        fireTableRowsUpdated(row, row);
    }
}