package controller;

import dao.CustomerDAO;
import dao.AccountDAO;
import dao.TransactionDAO;
import model.Transaction;
import util.EmailUtil;
import util.CSVUtil;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmailTransactionCSVServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // 🔐 CUSTOMER login check
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("customerLogin.jsp");
            return;
        }

        // 🔐 OTP verification check
        Boolean verified = (Boolean) session.getAttribute("emailVerified");
        String email = (String) session.getAttribute("otpEmail");

        if (verified == null || !verified || email == null) {
            response.sendRedirect("customerTransactions.jsp?error=verifyEmailFirst");
            return;
        }

        try {
            // 👤 Resolve customer by username (SAFER than email)
            String username = (String) session.getAttribute("username");
            CustomerDAO cdao = new CustomerDAO();
            int customerId = cdao.getCustomerIdByUsername(username);

            if (customerId == -1) {
                response.sendRedirect("customerTransactions.jsp?error=customerNotFound");
                return;
            }

            // 🏦 Get customer accounts
            AccountDAO adao = new AccountDAO();
            List<Long> accNos = adao.getAccountNumbersByCustomerId(customerId);

            if (accNos.isEmpty()) {
                response.sendRedirect("customerTransactions.jsp?error=noAccount");
                return;
            }

            // 💳 Get transactions
            TransactionDAO tdao = new TransactionDAO();
            List<Transaction> allTransactions = new ArrayList<>();

            for (long accNo : accNos) {
                allTransactions.addAll(
                        tdao.getTransactionsByAccountNumber(accNo)
                );
            }

            if (allTransactions.isEmpty()) {
                response.sendRedirect("customerTransactions.jsp?error=noTransactions");
                return;
            }

            // 📄 Generate CSV
            String csvPath = CSVUtil.generateTransactionCSV(allTransactions);

            // 📧 Email CSV
            EmailUtil.sendEmailWithAttachment(
                    email,
                    "Your Transaction History",
                    "Attached is your transaction history CSV.",
                    csvPath
            );

            // 🧹 SESSION CLEANUP (VERY IMPORTANT)
            session.removeAttribute("emailVerified");
            session.removeAttribute("otp");
            session.removeAttribute("otpEmail");
            session.removeAttribute("otpAttempts");

            response.sendRedirect("CustomerTransactionServlet?mail=sent");


        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("CustomerTransactionServlet?mail=fail");

        }
    }
}
