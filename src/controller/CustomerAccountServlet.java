package controller;

import dao.AccountDAO;
import dao.CustomerDAO;
import model.Account;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class CustomerAccountServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
System.out.println("SESSION ID (account): " + session.getId());

        // 🔐 Login check
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("customerLogin.jsp");
            return;
        }

        // 👤 Logged-in customer username from session
        String username = (String) session.getAttribute("username");
        System.out.println("Logged in username = " + username);

        // 🔁 Resolve username → customer_id
        CustomerDAO cdao = new CustomerDAO();
        int customerId = cdao.getCustomerIdByUsername(username);
        System.out.println("Resolved customerId = " + customerId);

        if (customerId == -1) {
            response.sendRedirect("customerLogin.jsp?error=invalidCustomer");
            return;
        }

        // 🏦 Fetch customer accounts
        AccountDAO adao = new AccountDAO();
        List<Account> accounts = adao.getAccountsByCustomerId(customerId);
        System.out.println("Accounts found = " + accounts.size());

        // 📤 Send to JSP
        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("customerAccounts.jsp")
               .forward(request, response);
    }
}
