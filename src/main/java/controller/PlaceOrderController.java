package controller;

import dataAccess.OrderDAO;
import models.PlaceOrderPayload;
import models.TradeOrder;
import ui.components.center.OrderPanel;
import ui.components.center.TradeTablePanel;

import java.sql.SQLException;
import java.util.List;

public class PlaceOrderController {
    private OrderPanel view;
    private OrderDAO dao;

    public PlaceOrderController(OrderPanel view) throws SQLException {
        this.view = view;
        this.dao = new OrderDAO();
    }

    public void placeOrder(PlaceOrderPayload orderPayload) {
        try {
            dao.placeOrder(orderPayload);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelOrder(int userID, int orderID) {
        try {
            dao.cancelOrder(userID, orderID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
