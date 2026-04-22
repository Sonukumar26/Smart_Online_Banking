package dao;

import util.DBConnection;
import model.Transaction;
import java.sql.*;
import java.util.*;

public class TransactionHistoryDAO {

    public List<Transaction> getTransactions(int accNo) {

        List<Transaction> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT * FROM transaction WHERE acc_no = ? ORDER BY txn_time DESC")) {

            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction();
                t.setTxnId(rs.getInt("txn_id"));
                t.setAccNo(rs.getInt("acc_no"));
                t.setTxnType(rs.getString("txn_type"));
                t.setAmount(rs.getDouble("amount"));
                t.setTxnTime(rs.getTimestamp("txn_time"));

                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
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

}
