package dataAccess;

import models.TradeOrder;
import services.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private final Connection connection;

    public OrderDAO(){
        connection = DBManager.getConnection();
    }

    public List<TradeOrder> getOrdersByUserId(int userId) throws SQLException {
        List<TradeOrder> orders = new ArrayList<>();
        String sql = "SELECT o.id, ot.type as order_type, c1.name as base_currency, c2.name as quote_currency, o.price, o.amount, o.filled, os.status\n" +
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
                "AND os.status IN ('OPEN', 'PARTIALLY_FILLED')";;

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
}
