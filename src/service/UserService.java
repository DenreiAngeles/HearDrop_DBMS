package service;

import menus.*;
import models.User;
import utils.*;
import dao.UserDAO;

import java.util.Scanner;

public class UserService {
    private UserDAO userDAO;
    private Scanner scanner;

    public UserService() {
        this.userDAO = new UserDAO();
        this.scanner = new Scanner(System.in);
    }

    public void registerUser() {
        try {
            System.out.println();
            System.out.println("\033[3mNote: Username must be 1 to 15 characters long. Type 'exit' or '0' to cancel.\033[0m");
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            if (username.equalsIgnoreCase("exit") || username.equals("0")) {
                System.out.println("Registration canceled.");
                return;
            }

            while (username.isEmpty() || username.length() > 15) {
                System.out.println("Invalid username. It must be between 1 and 15 characters.");
                System.out.print("Enter username: ");
                username = scanner.nextLine();
                if (username.equalsIgnoreCase("exit") || username.equals("0")) {
                    System.out.println("Registration canceled.");
                    return;
                }
            }

            System.out.println("\033[3mNote: Password must be at least 6 characters long. Type 'exit' or '0' to cancel.\033[0m");
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (password.equalsIgnoreCase("exit") || password.equals("0")) {
                System.out.println("Registration canceled.");
                return;
            }

            while (password.isEmpty() || password.length() < 6) {
                System.out.println("Invalid password. It must be at least 6 characters.");
                System.out.print("Enter password: ");
                password = scanner.nextLine();
                if (password.equalsIgnoreCase("exit") || password.equals("0")) {
                    System.out.println("Registration canceled.");
                    return;
                }
            }

            User user = new User(username, password);

            if (userDAO.registerUser(user)) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Registration failed. Username might already be taken.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while registering. Please try again.");
            LogUtils.logError(e);
        }
    }


    public void loginUser() {
        try {
            System.out.println();
            System.out.print("Enter username (or type 'exit' or '0' to cancel): ");
            String username = scanner.nextLine();
    
            if (username.equalsIgnoreCase("exit") || username.equals("0")) {
                System.out.println("Login canceled.");
                return;
            }
    
            while (username.isEmpty()) {
                System.out.println("Username cannot be empty. Please enter your username.");
                System.out.print("Enter username: ");
                username = scanner.nextLine();
                if (username.equalsIgnoreCase("exit") || username.equals("0")) {
                    System.out.println("Login canceled.");
                    return;
                }
            }
    
            System.out.print("Enter password (or type 'exit' or '0' to cancel): ");
            String password = scanner.nextLine();
    
            if (password.equalsIgnoreCase("exit") || password.equals("0")) {
                System.out.println("Login canceled.");
                return;
            }
    
            while (password.isEmpty()) {
                System.out.println("Password cannot be empty. Please enter your password.");
                System.out.print("Enter password: ");
                password = scanner.nextLine();
                if (password.equalsIgnoreCase("exit") || password.equals("0")) {
                    System.out.println("Login cancelled.");
                    return;
                }
            }
    
            User user = userDAO.loginUser(username, password);
            if (user == null) {
                System.out.println("Invalid credentials. Please try again.");
                return;
            }
    
            System.out.println("Login successful!");
            DesignUtils.clearScreen(1000);
    
            while (true) {
                System.out.println("------------------------");
                System.out.println("Please Choose your Role:");
                System.out.println("------------------------");
                System.out.println("Type 'exit' or '0' to cancel.\n");
                System.out.println("1. Donor");
                System.out.println("2. Recipient\n");
    
                System.out.print("Enter your role: ");
                String roleInput = scanner.nextLine();
    
                if (roleInput.equalsIgnoreCase("exit") || roleInput.equals("0")) {
                    System.out.println("Role selection canceled.");
                    return;
                }
    
                int role;
                try {
                    role = Integer.parseInt(roleInput);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid role.");
                    DesignUtils.clearScreen(1000);
                    continue;
                }
    
                switch (role) {
                    case 1:
                        BaseMenu donorMenu = new DonorMenu(user.getId(), user.getUsername());
                        donorMenu.show();
                        return;
                    case 2:
                        BaseMenu recipientMenu = new RecipientMenu(user.getUsername());
                        recipientMenu.show();
                        return; 
                    default:
                        System.out.println("Invalid choice. Please enter a valid role.");
                        DesignUtils.clearScreen(1000);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred during login. Please try again.");
            LogUtils.logError(e);
        }
    }
}
    
