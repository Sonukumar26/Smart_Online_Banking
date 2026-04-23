package controller;

import dao.CustomerDAO;
import model.Customer;
import util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class AddCustomerController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");

        CustomerDAO dao = new CustomerDAO();

        // ✅ Uniqueness checks
        if (dao.isEmailExists(email)) {
            response.sendRedirect("addCustomer.jsp?msg=emailExists");
            return;
        }

        if (dao.isMobileExists(mobile)) {
            response.sendRedirect("addCustomer.jsp?msg=mobileExists");
            return;
        }

        // 🔐 Auto-generate login credentials
        String username = email;              // standard practice
        String rawPassword = "welcome@123";   // temporary password
        String hashedPassword = PasswordUtil.hash(rawPassword);

        // ✅ Create customer object
        Customer customer = new Customer(
            name, email, mobile, address, username, hashedPassword);
                                

        // ✅ Save to DB
        boolean result = dao.addCustomer(customer);

        if (result) {
            response.sendRedirect("addCustomer.jsp?msg=success");
        } else {
            response.sendRedirect("addCustomer.jsp?msg=error");
        }
    }
}
