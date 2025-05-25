package controller;

import dataAccess.OrderDAO;
import models.PlaceOrderPayload;
import models.TradeHistory;
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

    public List<TradeHistory> getAllAvailableOpenOrder(String orderType) {
        try {
            return dao.getAllAvailableOpenOrder(orderType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateOrder(int orderID, float filled, int statusID) {
        try {
            dao.updateOrder(orderID, filled, statusID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTrade(int buyOrderId, int sellOrderId, float price, float amount) {
        try {
            dao.createTrade(buyOrderId, sellOrderId, price, amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMatchingWalletBalances(int orderID, float amount, float price, int baseCurrencyId, int quoteCurrencyId) {
        try {
            dao.updateMatchingWalletBalances(orderID, amount, price, baseCurrencyId, quoteCurrencyId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int depositUSDT(String userID, float amount){
        try {
            return dao.depositUSDToUserWallet(userID, amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getBalanceOfUser(String userID){
        try {
            return dao.getBalanceOfUser(userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "0";
    }
}
