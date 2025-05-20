package controller;

import dataAccess.OrderDAO;
import dataAccess.OrderHistoryDAO;
import models.TradeHistory;
import models.TradeOrder;
import ui.components.center.TradeTablePanel;

import java.sql.SQLException;
import java.util.List;

public class OrderHistoryController {
    private TradeTablePanel view;
    private OrderHistoryDAO dao;

    public OrderHistoryController(TradeTablePanel view) throws SQLException {
        this.view = view;
        this.dao = new OrderHistoryDAO();
    }

    public void loadHistoryOrders(int userId) {
        try {
            List<TradeHistory> orders = dao.getOrdersHistoryByUserId(userId);
            view.createOrdersHistoryTable(orders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
