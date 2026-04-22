package dao;

import util.DBConnection;
import java.sql.*;
import model.Transaction;
import java.util.*;

public class TransactionDAO {

    public boolean processTransaction(Long accNo, String txnType, double amount) {
    PreparedStatement psCheck = null;
    PreparedStatement psUpdate = null;
    PreparedStatement psTxn = null;
    ResultSet rs = null;

    try {
         Connection con = DBConnection.getConnection();
        con.setAutoCommit(false);

        System.out.println("Starting transaction for accNo=" + accNo + ", type=" + txnType + ", amount=" + amount);

        psCheck = con.prepareStatement("SELECT balance FROM account WHERE acc_no = ?");
        psCheck.setLong(1, accNo);
        rs = psCheck.executeQuery();

        if (!rs.next()) {
            System.out.println("Account not found!");
            con.rollback();
            return false;
        }

        double currentBalance = rs.getDouble("balance");
        System.out.println("Current balance: " + currentBalance);

        if ("WITHDRAW".equals(txnType) && currentBalance < amount) {
            System.out.println("Insufficient balance for withdrawal.");
            con.rollback();
            return false;
        }

        String updateQuery;
        if ("DEPOSIT".equals(txnType)) {
            updateQuery = "UPDATE account SET balance = balance + ? WHERE acc_no = ?";
        } else if ("WITHDRAW".equals(txnType)) {
            updateQuery = "UPDATE account SET balance = balance - ? WHERE acc_no = ?";
        } else {
            System.out.println("Invalid transaction type: " + txnType);
            con.rollback();
            return false;
        }

        psUpdate = con.prepareStatement(updateQuery);
        psUpdate.setDouble(1, amount);
        psUpdate.setLong(2, accNo);
        int rowsUpdated = psUpdate.executeUpdate();
        System.out.println("Rows updated: " + rowsUpdated);
        if (rowsUpdated == 0) {
            System.out.println("Failed to update account balance.");
            con.rollback();
            return false;
        }

        psTxn = con.prepareStatement("INSERT INTO transaction (acc_no, txn_type, amount) VALUES (?, ?, ?)");
        psTxn.setLong(1, accNo);
        psTxn.setString(2, txnType);
        psTxn.setDouble(3, amount);
        psTxn.executeUpdate();

        con.commit();
        System.out.println("Transaction committed successfully.");
        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

 public List<Transaction> getAllTransactions() {

    List<Transaction> list = new ArrayList<>();

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(
             "SELECT * FROM transaction ORDER BY txn_time DESC")) {

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
           Transaction t = new Transaction(
             rs.getInt("txn_id"),
                    rs.getLong("acc_no"),
                rs.getString("txn_type"),
                    rs.getDouble("amount"),
                rs.getTimestamp("txn_time")
            );

            list.add(t);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
 }
 
public List<Transaction> getTransactionsByAccountNumber(long accNo) {

    List<Transaction> list = new ArrayList<>();

    String sql = "SELECT * FROM transaction WHERE acc_no = ? ORDER BY txn_time DESC";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setLong(1, accNo);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Transaction t = new Transaction(
                rs.getInt("txn_id"),
                rs.getLong("acc_no"),
                rs.getString("txn_type"),
                rs.getDouble("amount"),
                rs.getTimestamp("txn_time")
            );
            list.add(t);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

public List<Transaction> getTransactionsByCustomer(int customerId) {

    List<Transaction> list = new ArrayList<>();

    String sql = """
        SELECT t.*
        FROM transaction t
        JOIN account a ON t.acc_no = a.acc_no
        WHERE a.customer_id = ?
    """;

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Transaction tx = new Transaction(
                rs.getInt("txn_id"),
                rs.getLong("acc_no"),
                rs.getString("txn_type"),
                rs.getDouble("amount"),
                rs.getTimestamp("txn_time")
            );
            list.add(tx);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


}