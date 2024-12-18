package menus;

import java.util.Scanner;

import service.DonorService;
import utils.DesignUtils;

public class DonorMenu extends BaseMenu {
    Scanner scanner = new Scanner(System.in);
    private DonorService donorService = new DonorService(scanner);
    private int donorId;
    private String username;

    public DonorMenu(int donorId, String username) {
        this.donorId = donorId;
        this.username = username;
    }

    @Override
    public void displayMenu() {
        DesignUtils.printHeader("donor", username);
        System.out.println("\n--- Donor Menu ---\n");
        System.out.println("1. Donate Item");
        System.out.println("2. View My Donated Items");
        System.out.println("3. Edit Donated Items");
        System.out.println("4. Remove Donated Item");
        System.out.println("5. Back to Main Menu");
    }

    @Override
    public void handleChoice(String choice) {
        switch (choice) {
            case "1":
                donorService.donateItem(donorId, username);
                break;
            case "2": 
                donorService.viewMyDonatedItems(donorId);
                break;
            case "3":
                donorService.editDonatedItem(donorId);
                break;
            case "4":
                donorService.removeDonatedItem(donorId);
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
