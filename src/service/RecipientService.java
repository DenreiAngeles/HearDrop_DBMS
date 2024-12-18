package service;

import java.util.List;
import java.util.Scanner;

import models.Donation;
import utils.DesignUtils;
import utils.LogUtils;
import dao.DonationDAO;

public class RecipientService {
    private DonationDAO donationDAO = new DonationDAO();
    private Scanner scanner;

    public RecipientService(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public void viewAvailableItems() {
        try {
            DesignUtils.clearScreen(1000);
            System.out.println("\n--- View Available Items ---");
            List<Donation> donations = donationDAO.getAvailableDonations();
    
            if (donations.isEmpty()) {
                System.out.println("No available items.\n");
            } else {
                for (Donation donation : donations) {
                    System.out.println("ID: " + donation.getId());
                    System.out.println("Item Name: " + donation.getItemName());
                    System.out.println("Description: " + donation.getDescription());
                    System.out.println("Quantity: " + donation.getQuantity());
                    System.out.println("Pickup Location: " + donation.getPickupLocation());
                    System.out.println("Donor: " + donation.getDonorUsername());
                    System.out.println("---------------------------");
                }
            }
            System.out.print("\nPress Enter to Continue...");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("An error occurred while viewing available items. Please try again.");
            LogUtils.logError(e);
        }
    }
    

    public void reserveItem(String recipientUsername) {
        try {
            DesignUtils.clearScreen(1000);
            System.out.println("\n--- Reserve Item ---");
            List<Donation> donations = donationDAO.getAvailableDonations();
    
            if (donations.isEmpty()) {
                System.out.println("No available items.\n");
                return;
            }
    
            for (Donation donation : donations) {
                System.out.println("ID: " + donation.getId());
                System.out.println("Item Name: " + donation.getItemName());
                System.out.println("Description: " + donation.getDescription());
                System.out.println("Quantity: " + donation.getQuantity());
                System.out.println("Pickup Location: " + donation.getPickupLocation());
                System.out.println("Donor: " + donation.getDonorUsername());
                System.out.println("---------------------------");
            }
    
            System.out.print("\nEnter ID of the item to reserve: ");
            String idInput = scanner.nextLine();
            if (DesignUtils.isExitInput(idInput)) {
                return;
            }
    
            int id;
            try {
                id = Integer.parseInt(idInput);
                if (id <= 0) {
                    System.out.println("ID cannot be 0. Please try again.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                return;
            }
    
            Donation donation = donationDAO.getDonationById(id);
            if (donation == null || !"Available".equals(donation.getStatus())) {
                System.out.println("Item not available for reservation.");
                return;
            }
    
            System.out.print("Enter pickup date (yyyy-MM-dd): ");
            String pickupDate = scanner.nextLine();
            if (DesignUtils.isExitInput(pickupDate)) {
                return;
            }
    
            System.out.print("Enter the pickup time (HH:mm): ");
            String pickupTime = scanner.nextLine();
            if (DesignUtils.isExitInput(pickupTime)) {
                return;
            }
    
            String fullDateTime = pickupDate + " " + pickupTime + ":00";
    
            donation.setStatus("Reserved");
            donation.setRecipientUsername(recipientUsername);
            donation.setPickupDatetime(fullDateTime);  // Store as String
    
            if (donationDAO.updateDonation(donation)) {
                System.out.println("Item reserved successfully!");
            } else {
                System.out.println("Failed to reserve item. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid item ID.");
            LogUtils.logError(e);
        } catch (Exception e) {
            System.out.println("An error occurred while reserving the item. Please try again.");
            LogUtils.logError(e);
        }
    }
    

    public void viewMyReservedItems(String recipientUsername) {
        try {
            DesignUtils.clearScreen(1000);
            System.out.println("\n--- My Reserved Items ---");
            List<Donation> donations = donationDAO.getDonationsByRecipient(recipientUsername);
    
            if (donations.isEmpty()) {
                System.out.println("No reserved items found.");
            } else {
                for (Donation donation : donations) {
                    System.out.println("ID: " + donation.getId());
                    System.out.println("Item Name: " + donation.getItemName());
                    System.out.println("Description: " + donation.getDescription());
                    System.out.println("Quantity: " + donation.getQuantity());
                    System.out.println("Pickup Location: " + donation.getPickupLocation());
                    System.out.println("Pickup Date and Time: " + donation.getPickupDatetime());
                    System.out.println("Donor: " + donation.getDonorUsername());
                    System.out.println("---------------------------");
                }
            }
            System.out.print("\nPress Enter to Continue...");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("An error occurred while viewing your reserved items. Please try again.");
            LogUtils.logError(e);
        }
    }
    

    public void removeReservedItem(String recipientUsername) {
        try {
            DesignUtils.clearScreen(1000);
            System.out.println("\n--- Remove Reserved Item ---");
            List<Donation> donations = donationDAO.getDonationsByRecipient(recipientUsername);
    
            if (donations.isEmpty()) {
                System.out.println("No reserved items found.");
                return;
            } else {
                for (Donation donation : donations) {
                    System.out.println("ID: " + donation.getId());
                    System.out.println("Item Name: " + donation.getItemName());
                    System.out.println("Description: " + donation.getDescription());
                    System.out.println("Quantity: " + donation.getQuantity());
                    System.out.println("Pickup Location: " + donation.getPickupLocation());
                    System.out.println("Pickup Date and Time: " + donation.getPickupDatetime());
                    System.out.println("Donor: " + donation.getDonorUsername());
                    System.out.println("---------------------------");
                }
            }
    
            System.out.print("\nEnter the ID of the item to remove: ");
            String idInput = scanner.nextLine();
            if (DesignUtils.isExitInput(idInput)) {
                return;
            }
            
            int id;
            try {
                id = Integer.parseInt(idInput);
                if (id <= 0) {
                    System.out.println("ID cannot be 0. Please try again.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                return;
            }
    
            Donation donation = donationDAO.getDonationById(id);
            if (donation == null) {
                System.out.println("Item not found.");
                return;
            }
    
            if (!recipientUsername.equals(donation.getRecipientUsername())) {
                System.out.println("You cannot remove an item that you did not reserve.");
                return;
            }
    
            donation.setStatus("Available");
            donation.setRecipientUsername(null);
            donation.setPickupDatetime(null);
    
            if (donationDAO.updateDonation(donation)) {
                System.out.println("Reserved item removed successfully.");
            } else {
                System.out.println("Failed to remove reserved item.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid item ID.");
            LogUtils.logError(e);
        } catch (Exception e) {
            System.out.println("An error occurred while removing the reserved item. Please try again.");
            LogUtils.logError(e);
        }
    }
}
    
