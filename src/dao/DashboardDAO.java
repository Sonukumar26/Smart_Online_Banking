package dao;

import java.sql.*;
import util.DBConnection;

public class DashboardDAO {

    public int getCustomerCount() {
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM customer")) {

            rs.next();
            return rs.getInt(1);
        } catch (Exception e) {
            return 0;
        }
    }

    public int getAccountCount() {
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM account")) {

            rs.next();
            return rs.getInt(1);
        } catch (Exception e) {
            return 0;
        }
    }

    public double getTotalBalance() {
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT SUM(balance) FROM account")) {

            rs.next();
            return rs.getDouble(1);
        } catch (Exception e) {
            return 0;
        }
    }
}
