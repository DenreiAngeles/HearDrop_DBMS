package main;
import service.UserService;
import utils.*;
import db.HearDropDB;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    private static UserService userService;

    public static void main(String[] args) {
        HearDropDB.getConnection();
        userService = new UserService();
        DesignUtils.printStart();

        while (true) {
            try {
                DesignUtils.printMainMenuHeader();
                System.out.println();
                System.out.println("(1) Register");
                System.out.println("(2) Login");
                System.out.println("(3) Exit");
                System.out.print("Enter your choice: ");

                String choiceInput = scanner.nextLine();
                int choice;

                try {
                    choice = Integer.parseInt(choiceInput);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        userService.registerUser();
                        break;
                    case 2:
                        userService.loginUser();
                        break;
                    case 3:
                        System.out.println("Thank you for using HearDrop!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
                LogUtils.logError(e);
            }
        }
    }
}

