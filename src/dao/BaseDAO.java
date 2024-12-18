package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.HearDropDB;

public abstract class BaseDAO {
    private Connection connection;

    public BaseDAO() {
        connection = HearDropDB.getConnection();
    }

    protected abstract Object mapResultSetToObject(ResultSet rs) throws SQLException;

    protected Connection getConnection() {
        return connection;
    }

    public int getLastInsertId() throws SQLException {
        String query = "SELECT LAST_INSERT_ID()";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("Failed to retrieve last insert ID.");
    }

    public boolean add(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, params);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error: Unable to execute the add operation.");
            logError("Add operation error", e);
        }
        return false;
    }

    public <T> T getById(String query, Class<T> clazz, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, params);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return clazz.cast(mapResultSetToObject(rs));
            } else {
                System.out.println("No record found for the given criteria.");
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve the record.");
            logError("GetById operation error", e);
        }
        return null;
    }

    public <T> List<T> getList(String query, Class<T> clazz, Object... params) {
        List<T> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, params);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(clazz.cast(mapResultSetToObject(rs)));
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve the list of records.");
            logError("GetList operation error", e);
        }
        return list;
    }

    public boolean update(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, params);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error: Unable to execute the update operation.");
            logError("Update operation error", e);
        }
        return false;
    }

    public boolean remove(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, params);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error: Unable to execute the remove operation.");
            logError("Remove operation error", e);
        }
        return false;
    }

    private void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }

    

    private void logError(String context, Exception e) {
        try (java.io.FileWriter fw = new java.io.FileWriter("errors.log", true);
             java.io.PrintWriter pw = new java.io.PrintWriter(fw)) {
            pw.println("Error Context: " + context);
            pw.println("Message: " + e.getMessage());
            e.printStackTrace(pw);
            pw.println("------------------------------------");
        } catch (Exception ex) {
            System.out.println("Error: Unable to write to the log file.");
        }
    }
}
