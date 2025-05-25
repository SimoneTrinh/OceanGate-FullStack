package dataAccess;

import models.PlaceOrderPayload;
import models.TradeHistory;
import models.TradeOrder;
import services.DBManager;
import utils.Constants;
import utils.LocalStorage;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private final Connection connection;

    public OrderDAO() {
        connection = DBManager.getConnection();
    }

    public List<TradeOrder> getOrdersByUserId(int userId) throws SQLException {
        List<TradeOrder> orders = new ArrayList<>();
        String sql = "SELECT o.id, ot.type as order_type, c1.code as base_currency, c2.code as quote_currency, o.price, o.amount, o.filled, os.status\n" +
                "FROM orders o \n" +
                "JOIN currencies c1 \n" +
                "ON o.base_currency_id = c1.id \n" +
                "JOIN currencies c2\n" +
                "ON o.quote_currency_id = c2.id \n" +
                "JOIN order_types ot \n" +
                "ON o.order_type_id = ot.id \n" +
                "JOIN order_statuses os \n" +
                "ON o.order_status_id = os.id\n" +
                "WHERE o.user_id = ?\n" +
                "AND os.status IN ('OPEN', 'PARTIALLY_FILLED')";
        ;

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            orders.add(new TradeOrder(
                    rs.getInt("id"),
                    rs.getString("order_type"),
                    rs.getString("base_currency"),
                    rs.getString("quote_currency"),
                    rs.getFloat("price"),
                    rs.getFloat("amount"),
                    rs.getFloat("filled"),
                    rs.getString("status")
            ));
        }

        return orders;
    }

    public void placeOrder(PlaceOrderPayload payload) throws SQLException {
        String sql = "INSERT INTO orders (user_id, base_currency_id, quote_currency_id, order_type_id, price, amount, filled, order_status_id)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?); -- Filled = 0.5, CLOSED\n";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, payload.getUserID());
        stmt.setInt(2, Constants.SYMBOL_MAP.get(payload.getBaseCurrency())); // 3
        stmt.setInt(3, Constants.SYMBOL_MAP.get(payload.getQuoteCurrency()));
        stmt.setInt(4, Constants.ORDER_TYPE.get(payload.getType()));
        stmt.setFloat(5, payload.getPrice());
        stmt.setFloat(6, payload.getAmount());
        stmt.setFloat(7, payload.getFilled()); // filled
        stmt.setInt(8, Constants.ORDER_STATUS.get(payload.getStatus())); // 2

        stmt.executeUpdate();

    }

    public void cancelOrder(int userID, int orderID) throws SQLException {
        String sql = "UPDATE orders SET order_status_id = ? WHERE id = ? AND user_id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, Constants.ORDER_STATUS.get("CLOSED"));
        stmt.setInt(2, orderID);
        stmt.setInt(3, userID);
        stmt.executeUpdate();
    }

    // getAllAvailableOpenOrder("BUY")
    public List<TradeHistory> getAllAvailableOpenOrder(String orderType) throws SQLException {
        List<TradeHistory> orders = new ArrayList<>();
        String sql = "SELECT\n" +
                "    o.id AS order_id,\n" +
                "    c1.code AS base_currency,\n" +
                "    c2.code AS quote_currency,\n" +
                "    o.price,\n" +
                "    o.amount,\n" +
                "    o.filled,\n" +
                "    os.status,\n" +
                "    o.created_at\n" +
                "FROM\n" +
                "    orders o\n" +
                "JOIN users u ON o.user_id = u.id\n" +
                "JOIN order_types ot ON o.order_type_id = ot.id\n" +
                "JOIN order_statuses os ON o.order_status_id = os.id\n" +
                "JOIN currencies c1 ON o.base_currency_id = c1.id\n" +
                "JOIN currencies c2 ON o.quote_currency_id = c2.id\n" +
                "WHERE\n" +
                "    ot.type = ?\n" +
                "    AND os.status IN ('OPEN', 'PARTIALLY_FILLED')\n" +
                "    AND c1.code = ?\n" + // get as current - btc - base
                "    AND c2.code = ?\n" + // get as current - usdt - quote
                "ORDER BY o.created_at ASC";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, orderType);
        stmt.setString(2, Constants.LOCAL_STORAGE.get(LocalStorage.BASE_CURRENCY));
        stmt.setString(3, Constants.LOCAL_STORAGE.get(LocalStorage.QUOTE_CURRENCY));
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            orders.add(new TradeHistory(
                    rs.getInt("order_id"),
                    "BUY",
                    rs.getString("base_currency"),
                    rs.getString("quote_currency"),
                    rs.getFloat("price"),
                    rs.getFloat("amount"),
                    rs.getFloat("filled"),
                    rs.getString("status"),
                    rs.getString("created_at")
            ));
        }

        return orders;
    }

    public void updateOrder(int orderID, float filled, int statusID) throws SQLException {
        String sql = "UPDATE orders SET filled = ?, order_status_id = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setFloat(1, filled);
        stmt.setInt(2, statusID);
        stmt.setInt(3, orderID);
        stmt.executeUpdate();
    }

    public void createTrade(int buyOrderId, int sellOrderId, float price, float amount) throws SQLException {
        String sql = "INSERT INTO trades (buy_order_id, sell_order_id, price, amount, timestamp) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, buyOrderId);
        stmt.setInt(2, sellOrderId);
        stmt.setFloat(3, price);
        stmt.setFloat(4, amount);
        stmt.executeUpdate();
    }

    public void updateMatchingWalletBalances(int orderID, float amount, float price,
                                             int baseCurrencyId, int quoteCurrencyId) throws SQLException {

        // Buyer's wallet: +base currency
        String sqlBuyerBase = "INSERT INTO wallet_balances (wallet_id, currency_id, amount) " +
                "SELECT w.id, ?, ? FROM wallets w WHERE w.user_id = (SELECT user_id FROM orders WHERE id = ?) " +
                "ON DUPLICATE KEY UPDATE amount = amount + ?";
        PreparedStatement stmt = connection.prepareStatement(sqlBuyerBase);
        stmt.setInt(1, baseCurrencyId);
        stmt.setFloat(2, amount);
        stmt.setInt(3, orderID);
        stmt.setFloat(4, amount);
        stmt.executeUpdate();


        float tradeValue = amount * price;
        // Buyer's wallet: -quote currency
        String sqlBuyerQuote = "INSERT INTO wallet_balances (wallet_id, currency_id, amount) " +
                "SELECT w.id, ?, ? FROM wallets w WHERE w.user_id = (SELECT user_id FROM orders WHERE id = ?) " +
                "ON DUPLICATE KEY UPDATE amount = amount + ?";
        PreparedStatement stmt2 = connection.prepareStatement(sqlBuyerQuote);
        stmt2.setInt(1, quoteCurrencyId);
        stmt2.setFloat(2, tradeValue);
        stmt2.setInt(3, orderID);
        stmt2.setFloat(4, tradeValue);
        stmt2.executeUpdate();

    }

    public int depositUSDToUserWallet(String userID, float amount) throws SQLException {
        String sql = "INSERT INTO wallet_balances (wallet_id, currency_id, amount)\n" +
                "VALUES (\n" +
                "    (SELECT id FROM wallets WHERE user_id = ?),\n" +
                "    (SELECT id FROM currencies WHERE code = 'USDT'),\n" +
                "    ?\n" +
                ")\n" +
                "ON DUPLICATE KEY UPDATE amount = amount + VALUES(amount);";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, userID);
        stmt.setDouble(2, amount);
        return stmt.executeUpdate();
    }

    public String getBalanceOfUser(String userID) throws SQLException {
        String sql = "SELECT wb.amount\n" +
                "FROM wallet_balances wb\n" +
                "JOIN wallets w ON wb.wallet_id = w.id\n" +
                "JOIN currencies c ON wb.currency_id = c.id\n" +
                "WHERE w.user_id = ? AND c.code = 'USDT';\n";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, userID);

        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getString("amount");
    }
}
