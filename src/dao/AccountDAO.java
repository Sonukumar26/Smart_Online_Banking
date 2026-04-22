package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Account;
import util.DBConnection;

public class AccountDAO {

    public boolean openAccount(long accNo, int customerId, String accType) {

    String sql =
        "INSERT INTO account (acc_no, customer_id, acc_type, balance) " +
        "VALUES (?, ?, ?, ?)";

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setLong(1, accNo);
        ps.setInt(2, customerId);
        ps.setString(3, accType);
        ps.setDouble(4, 1000.0); 

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


   public List<Long> getAccountNumbersByCustomerId(int customerId) {

    List<Long> accList = new ArrayList<>();
    String sql = "SELECT acc_no FROM account WHERE customer_id = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            accList.add(rs.getLong("acc_no"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return accList;
}

public List<Account> getAccountsByCustomerId(int customerId) {
    List<Account> list = new ArrayList<>();

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(
             "SELECT * FROM account WHERE customer_id = ?")) {

        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Account acc = new Account(
                rs.getLong("acc_no"),       
                rs.getString("acc_type"),
                rs.getDouble("balance")
              
            );
            list.add(acc);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}



    public List<Account> getAllAccounts() {

        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM account";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Account acc = new Account(
                    rs.getLong("acc_no"),      
                    rs.getInt("customer_id"),
                    rs.getString("acc_type"),
                    rs.getDouble("balance"),
                    rs.getTimestamp("created_at")
                );
                list.add(acc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
 
    public List<Account> getAccountsByUsername(String username) {

    List<Account> list = new ArrayList<>();

    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM account WHERE customer_username=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Account acc = new Account(
                rs.getLong("acc_no"),
                rs.getInt("customer_id"),
                rs.getString("acc_type"),
                rs.getDouble("balance"),
                rs.getTimestamp("created_at")
            );
            list.add(acc);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

    
}
