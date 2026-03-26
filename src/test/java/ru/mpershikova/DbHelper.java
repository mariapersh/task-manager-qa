package ru.mpershikova;

import java.sql.*;

public class DbHelper {
    private static final String DB_URL = "jdbc:postgresql://localhost:5435/task_manager";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Из UserGetTest: проверка, что пользователь существует
    public static boolean userExists(long userId) throws SQLException {
        String sql = "SELECT id FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public static ResultSet getUserByIdTest(long userId) throws SQLException {
        Connection conn = getConnection();
        String sql = "SELECT * FROM users WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, userId);
        return stmt.executeQuery();
    }

    public static ResultSet getUserByEmail(String email) throws SQLException {
        Connection conn = getConnection();
        String sql = "SELECT * FROM users WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        return stmt.executeQuery();
    }

    public static long getAnyUserId() throws SQLException {
        String sql = "SELECT id FROM users LIMIT 1";
        try (Connection conn = getConnection();
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){

            if (rs.next()) {
                return rs.getLong("id");
            } else {
                throw new SQLException("В БД нет пользователей");
            }
        }
    }
}
