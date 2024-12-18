package menus;

import java.util.Scanner;

import service.RecipientService;
import utils.DesignUtils;

public class RecipientMenu extends BaseMenu {
    Scanner scanner = new Scanner(System.in);
    private RecipientService recipientService = new RecipientService(scanner);
    private String recipientUsername;

    public RecipientMenu(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    @Override
    public void displayMenu() {
        DesignUtils.printHeader("recipient", recipientUsername);
        System.out.println("\n--- Recipient Menu ---\n");
        System.out.println("1. View Available Items");
        System.out.println("2. Reserve Item");
        System.out.println("3. View My Reserved Items");
        System.out.println("4. Remove Reserved Item");
        System.out.println("5. Back to Main Menu");
    }

    @Override
    public void handleChoice(String choice) {
        switch (choice) {
            case "1": 
                recipientService.viewAvailableItems();
                break;
            case "2": 
                recipientService.reserveItem(recipientUsername);
                break;
            case "3": 
                recipientService.viewMyReservedItems(recipientUsername);
                break;
            case "4": 
                recipientService.removeReservedItem(recipientUsername);
                break;
            default: 
                System.out.println("Invalid choice. Please try again.");
        }
    }

    @Override
    protected String getExitChoice() {
        return "5";
    }
}
