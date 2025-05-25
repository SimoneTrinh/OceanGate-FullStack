package dataAccess;

import models.User;
import services.DBManager;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;

public class UserStore {
    private static final Connection connection = DBManager.getConnection();

    public static Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static boolean validateLogin(String username, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        try {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void addUser(User user) throws SQLException {
        String insertUserSql = "INSERT INTO users (username, password, email, first_name, last_name, phone) VALUES (?, ?, ?, ?, ?, ?)";
        String insertWalletSql = "INSERT INTO wallets (user_id) VALUES (?)";
        String insertWalletBalanceSql = "INSERT INTO wallet_balances (wallet_id, currency_id, amount) VALUES (?, ?, ?)";

        assert connection != null;

        try (
                PreparedStatement userStmt = connection.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS);
        ) {
            connection.setAutoCommit(false); // Bắt đầu transaction

            // Step 1: Insert user
            userStmt.setString(1, user.getUsername());
            userStmt.setString(2, user.getPassword());
            userStmt.setString(3, user.getEmail());
            userStmt.setString(4, user.getFirstName());
            userStmt.setString(5, user.getLastName());
            userStmt.setString(6, user.getPhone());

            int affectedRows = userStmt.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Creating user failed, no rows affected.");

            int userId;
            try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            // Insert wallet for user
            int walletId;
            try (PreparedStatement walletStmt = connection.prepareStatement(insertWalletSql, Statement.RETURN_GENERATED_KEYS)) {
                walletStmt.setInt(1, userId);
                walletStmt.executeUpdate();

                try (ResultSet walletKeys = walletStmt.getGeneratedKeys()) {
                    if (walletKeys.next()) {
                        walletId = walletKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating wallet failed, no ID obtained.");
                    }
                }
            }

            // Insert wallet balance for currency_id = 3 (USDT)
            try (PreparedStatement balanceStmt = connection.prepareStatement(insertWalletBalanceSql)) {
                balanceStmt.setInt(1, walletId);
                balanceStmt.setInt(2, 3); // currency_id = 3
                balanceStmt.setBigDecimal(3, BigDecimal.ZERO);
                balanceStmt.executeUpdate();
            }

            connection.commit(); // Commit all if successful
        } catch (SQLException e) {
            connection.rollback(); // Rollback on any error
            throw e;
        } finally {
            connection.setAutoCommit(true); // Restore autocommit
        }
    }

    public static void updateUser(User user) {
        String sql = "UPDATE users SET password = ?, email = ?, first_name = ?, last_name = ?, phone = ? WHERE username = ?";
        try {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, user.getPassword());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getFirstName());
                stmt.setString(4, user.getLastName());
                stmt.setString(5, user.getPhone());
                stmt.setString(6, user.getUsername());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("phone")
        );
    }
}
