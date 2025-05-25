package controller;

import models.User;
import services.DBManager;

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
        String sql = "INSERT INTO users (username, password, email, first_name, last_name, phone) VALUES (?, ?, ?, ?, ?, ?)";
        assert connection != null;

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getPhone());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No rows affected. User might not have been inserted.");
            }
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
