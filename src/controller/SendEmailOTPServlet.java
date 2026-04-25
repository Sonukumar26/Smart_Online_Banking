package controller;

import dao.CustomerDAO;
import util.EmailUtil;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class SendEmailOTPServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("customerLogin.jsp");
            return;
        }

        String email = request.getParameter("email");
        if (email == null || email.trim().isEmpty()) {
            response.sendRedirect("customerTransactions.jsp?error=emailMissing");
            return;
        }

        CustomerDAO dao = new CustomerDAO();
        if (!dao.isCustomerEmailExists(email)) {
            response.sendRedirect("customerTransactions.jsp?error=emailNotFound");
            return;
        }

        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);

        session.setAttribute("otp", otp);
        session.setAttribute("otpEmail", email);
        session.removeAttribute("otpAttempts");

        boolean sent = EmailUtil.sendSimpleEmail(
                email,
                "OTP Verification",
                "Your OTP is: " + otp
        );

      if (sent) {
    response.sendRedirect("CustomerTransactionServlet?otp=sent");
} else {
    response.sendRedirect("CustomerTransactionServlet?otp=fail");
}

    }
}
