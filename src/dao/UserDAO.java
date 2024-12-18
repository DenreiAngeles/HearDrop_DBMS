package dao;

import java.sql.*;

import models.User;

public class UserDAO extends BaseDAO {

    @Override
    protected User mapResultSetToObject(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        return user;
    }

    public boolean registerUser(User user) {
        String query = "INSERT INTO User (username, password) VALUES (?, ?)";
        try {
            return add(query, user.getUsername(), user.getPassword());
        } catch (Exception e) {
            System.out.println("Error registering user. Please try again.");
            logError(e);
            return false;
        }
    }

    public User loginUser(String username, String password) {
        String query = "SELECT * FROM User WHERE username = ? AND password = ?";
        try {
            return getById(query, User.class, username, password);
        } catch (Exception e) {
            System.out.println("Error during login. Please try again.");
            logError(e);
            return null;
        }
    }

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM User WHERE username = ?";
        try {
            return getById(query, User.class, username);
        } catch (Exception e) {
            System.out.println("Error retrieving user by username.");
            logError(e);
            return null;
        }
    }

    private void logError(Exception e) {
        try (java.io.FileWriter fw = new java.io.FileWriter("errors.log", true);
             java.io.PrintWriter pw = new java.io.PrintWriter(fw)) {
            pw.println("Error: " + e.getMessage());
            e.printStackTrace(pw);
        } catch (Exception ex) {
            System.out.println("Failed to log error: " + ex.getMessage());
        }
    }
}
