package controller;

import dataAccess.OrderDAO;
import models.TradeOrder;
import ui.components.center.TradeTablePanel;

import java.sql.SQLException;
import java.util.List;

public class OrderController {

    private TradeTablePanel view;
    private OrderDAO dao;

    public OrderController(TradeTablePanel view) throws SQLException {
        this.view = view;
        this.dao = new OrderDAO();
    }

    public void loadOrders(int userId) {
        try {
            List<TradeOrder> orders = dao.getOrdersByUserId(userId);
            view.createOrdersTable(orders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
