package controller;

import dao.AccountDAO;
import dao.CustomerDAO;
import dao.TransactionDAO;
import model.Account;
import model.Transaction;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerTransactionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // 🔐 Login check
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("customerLogin.jsp");
            return;
        }

        // 👤 Logged-in username
        String username = (String) session.getAttribute("username");
        System.out.println("Logged in username = " + username);

        // 1️⃣ username → customer_id
        CustomerDAO cdao = new CustomerDAO();
        int customerId = cdao.getCustomerIdByUsername(username);
        System.out.println("Resolved customerId = " + customerId);

        if (customerId == -1) {
            response.sendRedirect("customerLogin.jsp?error=invalidCustomer");
            return;
        }

        // 2️⃣ customer_id → accounts
        AccountDAO adao = new AccountDAO();
        List<Account> accounts = adao.getAccountsByCustomerId(customerId);
        request.setAttribute("accounts", accounts);

        // 3️⃣ acc_no → transactions
        TransactionDAO tdao = new TransactionDAO();
        List<Transaction> transactions = new ArrayList<>();

        for (Account acc : accounts) {
            transactions.addAll(
                tdao.getTransactionsByAccountNumber(acc.getAccNo())
            );
        }

        // 📤 Send to JSP
        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("customerTransactions.jsp")
               .forward(request, response);
    }
}
