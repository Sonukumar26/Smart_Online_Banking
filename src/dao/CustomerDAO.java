package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Customer;
import util.DBConnection;
import util.PasswordUtil;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class CustomerDAO {

    public boolean addCustomer(Customer customer) {

    boolean status = false;

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "INSERT INTO customer(name, email, mobile, address, username, password) VALUES (?, ?, ?, ?, ?, ?)"
         )) {

        ps.setString(1, customer.getName());
        ps.setString(2, customer.getEmail());
        ps.setString(3, customer.getMobile());
        ps.setString(4, customer.getAddress());
        ps.setString(5, customer.getUsername());
        ps.setString(6, customer.getPassword());

        status = ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}


        public List<Customer> getAllCustomers() {

        List<Customer> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM customer";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Customer c = new Customer(
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("mobile"),
                    rs.getString("address"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

     public int getCustomerIdByName(String name) {
 System.out.println("Searching customer with name: '" + name + "'");

        int id = -1;

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                                "SELECT customer_id FROM customer WHERE LOWER(TRIM(name)) = LOWER(TRIM(?))"
                            );
             ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt("customer_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in getCustomerIdByName: " + e.getMessage());

        }

        return id;
    }

    public boolean isCustomerEmailExists(String email) {

        boolean exists = false;

        String sql = "SELECT 1 FROM customer WHERE LOWER(email) = LOWER(?)";

        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            exists = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exists;
    }

    public int getCustomerIdByEmail(String email) {

    int id = -1;

    String sql = "SELECT customer_id FROM customer WHERE LOWER(email) = LOWER(?)";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            id = rs.getInt("customer_id");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return id;
}

public boolean isEmailExists(String email) {
    String sql = "SELECT 1 FROM customer WHERE email = ?";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        return rs.next();

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

public boolean isMobileExists(String mobile) {
    String sql = "SELECT 1 FROM customer WHERE mobile = ?";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, mobile);
        ResultSet rs = ps.executeQuery();
        return rs.next();

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

public Customer loginCustomer(String username, String password) {

    Customer customer = null;

    try (Connection con = DBConnection.getConnection()) {

        String sql = "SELECT * FROM customer WHERE username = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println("✅ USER FOUND IN DB");

            String dbHashedPassword = rs.getString("password");
            System.out.println("DB HASH     = " + dbHashedPassword);

            String inputHashedPassword = PasswordUtil.hash(password);
            System.out.println("INPUT HASH  = " + inputHashedPassword);

            if (PasswordUtil.verify(password, dbHashedPassword)) {
                System.out.println("✅ PASSWORD MATCH");

                customer = new Customer(
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("mobile"),
                    rs.getString("username")
                );
            } else {
                System.out.println("❌ PASSWORD MISMATCH");
            }

        } else {
            System.out.println("❌ USER NOT FOUND");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return customer;
}


public int getCustomerIdByUsername(String username) {

    int id = -1;

    try {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps =
            con.prepareStatement(
                "SELECT customer_id FROM customer WHERE username=?"
            );

        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            id = rs.getInt("customer_id");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return id;
}


}
