package ui.components.center.TradeTable;

import ui.components.center.OrderPanel;
import ui.components.center.TradeTablePanel;
import utils.Constants;
import utils.LocalStorage;

import javax.swing.*;
import java.awt.*;

public class CancelButton extends DefaultCellEditor {
    private final JButton button;
    private final TradeOrderTableModel model;
    private boolean clicked;
    private int row;

    public CancelButton(JCheckBox checkBox, TradeOrderTableModel model) {
        super(checkBox);
        this.model = model;
        button = new JButton("Cancel");
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.row = row;
        button.setText("Cancel");
        clicked = true;
        return button;
    }

    public Object getCellEditorValue() {
        if (clicked) {
            Object orderId = model.getOrderIdAtRow(row);
//            model.cancelOrderAtRow(row); // simulate cancel
            JOptionPane.showMessageDialog(button, "Cancelled Order ID: " + orderId);
            int userID = Integer.parseInt(Constants.LOCAL_STORAGE.get(LocalStorage.USER_ID));
            OrderPanel.placeOrderController.cancelOrder(userID, Integer.parseInt(orderId.toString()));
            TradeTablePanel.orderController.reLoadOrders(userID);
            TradeTablePanel.orderHistoryController.reLoadOrders(userID);
        }
        clicked = false;
        return "Cancel";
    }

    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}