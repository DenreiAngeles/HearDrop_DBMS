package db;

import java.sql.*;
import java.util.Scanner;

import utils.DesignUtils;
import utils.LogUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HearDropDB {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DBNAME = "HearDrop"; 
    private static String USER = "root";            //you can change this to what you use in MySQL here for ease of access, but I added a feature so you can change this via the program.
    private static String PASSWORD = "password";    //same for this (although I highly advise you change it both here now).
    private static final String INIT_FILE = "C:\\Users\\denre\\OneDrive\\Desktop\\HearDrop_DBMS\\src\\db\\init.sql"; //change this to the filepath of init.sql on your device.
    static Scanner scan = new Scanner(System.in);

    public static Connection getConnection() {
        try {
            return attemptConnection(URL + DBNAME, USER, PASSWORD);
        } catch (SQLException e) {
            LogUtils.logError(e);
            System.out.println("Error: Unable to connect to the database. Initializing setup...");
            DesignUtils.clearScreen(500);
            return null;
        }
    }

    private static Connection attemptConnection(String fullUrl, String user, String password) throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(fullUrl, user, password);
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("USE " + DBNAME);
            }
            return conn;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1045) {
                System.out.println("Error: Incorrect MySQL username or password.");
                retryConnection();
            } else if (fullUrl.equals(URL + DBNAME)) {
                System.out.println("Database not found. Initializing setup...");
                DesignUtils.clearScreen(1000);
                createDatabaseAndTables(user, password);
            } else {
                throw e;
            }
        }
        return DriverManager.getConnection(fullUrl, user, password);
    }

    private static void createDatabaseAndTables(String user, String password) {
        try (Connection conn = DriverManager.getConnection(URL, user, password);
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE DATABASE IF NOT EXISTS " + DBNAME);
            System.out.println("Database '" + DBNAME + "' created (or already exists).");

            try (Connection dbConn = DriverManager.getConnection(URL + DBNAME, user, password)) {
                executeSQLFile(dbConn, INIT_FILE);

                System.out.println("Tables created successfully!");
                System.out.println("=========================");
                System.out.println("Database setup completed!");
                System.out.println("=========================");
                System.out.print("Please Wait...\n");
                DesignUtils.clearScreen(2000);
            }
        } catch (SQLException e) {
            LogUtils.logError(e);
            System.out.println("Error: Unable to create the database or tables.");
        }
    }

    private static void executeSQLFile(Connection conn, String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("--")) {
                    sql.append(line);
                    if (line.endsWith(";")) {
                        try (Statement stmt = conn.createStatement()) {
                            stmt.execute(sql.toString());
                        }
                        sql.setLength(0);
                    }
                }
            }
            System.out.println("SQL file executed successfully.");
        } catch (IOException e) {
            System.out.println("Error: Unable to read the SQL file.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error: Unable to execute the SQL file.");
            LogUtils.logError(e);
        }
    }

    public static void retryConnection() {
        while (true) {
            DesignUtils.clearScreen(1000);
            System.out.println("\033[3mNote: Please enter the right credentials of your MySQL in order to connect to the database.\033[0m");
            System.out.println("-------------------------------------------------------------------------------------------");
            System.out.print("Enter MySQL Username (default: root): ");
            String username = scan.nextLine().trim();
            if (!username.isEmpty()) USER = username;

            System.out.print("Enter MySQL Password                : ");
            String password = scan.nextLine().trim();
            PASSWORD = password;

            try {
                attemptConnection(URL+DBNAME, USER, PASSWORD);
                System.out.println("Connection successful!");
                break;
            } catch (SQLException e) {
                LogUtils.logError(e);
                System.out.println("Connection failed. Please try again.");
            }
        }
    }
}
