package models;

public class Donation {
    private int id;
    private String itemName;
    private String description;
    private int quantity;
    private String pickupLocation;
    
    private int donorId;
    private String donorUsername;
    
    private String status;
    private String recipientUsername;
    private String pickupDatetime;

    public Donation(int id, String itemName, String description, int quantity, String pickupLocation,
                    int donorId, String donorUsername, String status, String recipientUsername, 
                    String pickupDatetime) {
        this.id = id;
        this.itemName = itemName;
        this.description = description;
        this.quantity = quantity;
        this.pickupLocation = pickupLocation;
        this.donorId = donorId;
        this.donorUsername = donorUsername;
        this.status = status;
        this.recipientUsername = recipientUsername;
        this.pickupDatetime = pickupDatetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public String getDonorUsername() {
        return donorUsername;
    }

    public void setDonorUsername(String donorUsername) {
        this.donorUsername = donorUsername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    public String getPickupDatetime() {
        return pickupDatetime;
    }

    public void setPickupDatetime(String pickupDatetime) {
        this.pickupDatetime = pickupDatetime;
    }
}


