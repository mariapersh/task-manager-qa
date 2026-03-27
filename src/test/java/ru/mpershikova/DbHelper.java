package ru.mpershikova;

import java.sql.*;

public class DbHelper {
    private static final String DB_URL = "jdbc:postgresql://localhost:5435/task_manager";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static boolean userExists(long userId) throws SQLException {
        String sql = "SELECT id FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public static ResultSet getUserByEmail(String email) throws SQLException {
        Connection conn = getConnection();
        String sql = "SELECT * FROM users WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        return stmt.executeQuery();
    }

    public static ResultSet getAnyUser() throws SQLException {
        Connection conn = getConnection();
        String sql = "SELECT * FROM users LIMIT 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        return stmt.executeQuery();
    }

    public static long getUsersCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                return 0;
            }
        }
    }
}
