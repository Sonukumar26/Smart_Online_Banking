package controller;

import dao.CustomerDAO;
import model.Customer;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class CustomerLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("CustomerLoginServlet doPost called");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        CustomerDAO dao = new CustomerDAO();
        Customer customer = dao.loginCustomer(username, password);

        System.out.println("USERNAME = " + username);
        System.out.println("PASSWORD = " + password);
        System.out.println("Customer = " + customer);

        if (customer != null) {
            HttpSession session = request.getSession();  // create session if not exists
            session.setAttribute("customer", customer);
            session.setAttribute("username", username);
            System.out.println("SESSION ID (login): " + request.getSession().getId());

            response.sendRedirect("customerDashboard.jsp");
        } else {
            response.sendRedirect("customerLogin.jsp?error=invalid");
        }
    }
}
