package dao;

import java.sql.*;
import java.util.List;

import models.Donation;

public class DonationDAO extends BaseDAO {

    @Override
    protected Donation mapResultSetToObject(ResultSet rs) throws SQLException {
        Donation donation = new Donation(
            rs.getInt("id"),
            rs.getString("item_name"),
            rs.getString("description"),
            rs.getInt("quantity"),
            rs.getString("pickup_location"),
            rs.getInt("donor_id"),
            rs.getString("donor_username"),
            rs.getString("status"),
            rs.getString("recipient_username"),
            rs.getString("pickup_datetime")
        );
    
        return donation;
    }

    public boolean addDonation(Donation donation) {
        String donationQuery = "INSERT INTO Donation (item_name, description, quantity, pickup_location) " +
                               "VALUES (?, ?, ?, ?)";
        String donorQuery = "INSERT INTO Donor (donation_id, donor_id, donor_username) " +
                            "VALUES (?, ?, ?)";
        String statusQuery = "INSERT INTO DonationStatus (donation_id, status) VALUES (?, ?)";

        try {
            boolean donationAdded = add(donationQuery, donation.getItemName(), donation.getDescription(), donation.getQuantity(), donation.getPickupLocation());
            if (donationAdded) {
                int donationId = getLastInsertId();
                boolean donorAdded = add(donorQuery, donationId, donation.getDonorId(), donation.getDonorUsername());
                boolean statusAdded = add(statusQuery, donationId, donation.getStatus());
                return donorAdded && statusAdded;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Donation getDonationById(int id) {
        String query = "SELECT d.id, d.item_name, d.description, d.quantity, d.pickup_location, " +
                       "don.donor_id, don.donor_username, " +
                       "ds.status, ds.recipient_username, ds.pickup_datetime " +
                       "FROM Donation d " +
                       "JOIN Donor don ON d.id = don.donation_id " +
                       "JOIN DonationStatus ds ON d.id = ds.donation_id " +
                       "WHERE d.id = ?";
        return getById(query, Donation.class, id);
    }

    public List<Donation> getDonationsByDonor(int donorId) {
        String query = "SELECT d.id, d.item_name, d.description, d.quantity, d.pickup_location, " +
                       "don.donor_id, don.donor_username, " +
                       "ds.status, ds.recipient_username, ds.pickup_datetime " +
                       "FROM Donation d " +
                       "JOIN Donor don ON d.id = don.donation_id " +
                       "JOIN DonationStatus ds ON d.id = ds.donation_id " +
                       "WHERE don.donor_id = ?";
        return getList(query, Donation.class, donorId);
    }

    public List<Donation> getAvailableDonations() {
        String query = "SELECT d.id, d.item_name, d.description, d.quantity, d.pickup_location, " +
                       "don.donor_id, don.donor_username, " +
                       "ds.status, ds.recipient_username, ds.pickup_datetime " +
                       "FROM Donation d " +
                       "JOIN Donor don ON d.id = don.donation_id " +
                       "JOIN DonationStatus ds ON d.id = ds.donation_id " +
                       "WHERE ds.status = 'Available'";
        return getList(query, Donation.class);
    }

    public List<Donation> getDonationsByRecipient(String recipientUsername) {
        String query = "SELECT d.id, d.item_name, d.description, d.quantity, d.pickup_location, " +
                       "don.donor_id, don.donor_username, " +
                       "ds.status, ds.recipient_username, ds.pickup_datetime " +
                       "FROM Donation d " +
                       "JOIN Donor don ON d.id = don.donation_id " +
                       "JOIN DonationStatus ds ON d.id = ds.donation_id " +
                       "WHERE ds.recipient_username = ?";
        return getList(query, Donation.class, recipientUsername);
    }

    public boolean updateDonation(Donation donation) {
        String donationQuery = "UPDATE Donation SET item_name = ?, description = ?, quantity = ?, pickup_location = ? " +
                               "WHERE id = ?";
        String donorQuery = "UPDATE Donor SET donor_id = ?, donor_username = ? WHERE donation_id = ?";
        String statusQuery = "UPDATE DonationStatus SET status = ?, recipient_username = ?, pickup_datetime = ? " +
                             "WHERE donation_id = ?";

        boolean donationUpdated = update(donationQuery, donation.getItemName(), donation.getDescription(), 
                                         donation.getQuantity(), donation.getPickupLocation(), donation.getId());

        boolean donorUpdated = update(donorQuery, donation.getDonorId(), donation.getDonorUsername(), donation.getId());

        boolean statusUpdated = update(statusQuery, donation.getStatus(), donation.getRecipientUsername(), 
                                       Timestamp.valueOf(donation.getPickupDatetime()), donation.getId());

        return donationUpdated && donorUpdated && statusUpdated;
    }

    public boolean removeDonation(int id) {
        String query = "DELETE FROM Donation WHERE id = ?";
        return remove(query, id);
    }
}
