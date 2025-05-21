package dataAccess;

import models.PlaceOrderPayload;
import models.TradeOrder;
import services.DBManager;
import utils.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
