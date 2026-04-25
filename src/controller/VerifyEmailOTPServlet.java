package controller;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class VerifyEmailOTPServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("customerLogin.jsp");
            return;
        }

        String userOtp = request.getParameter("otp");
        if (userOtp == null || userOtp.trim().isEmpty()) {
            response.sendRedirect("customerTransactions.jsp?error=otpMissing");
            return;
        }

        String sessionOtp = (String) session.getAttribute("otp");

        Integer attempts = (Integer) session.getAttribute("otpAttempts");
        if (attempts == null) attempts = 0;

        if (attempts >= 3) {
            session.removeAttribute("otp");
            session.removeAttribute("otpAttempts");
            session.removeAttribute("otpEmail");

            response.sendRedirect("customerTransactions.jsp?verified=locked");
            return;
        }

        if (sessionOtp != null && sessionOtp.equals(userOtp)) {
            session.removeAttribute("otp");
            session.removeAttribute("otpAttempts");

            session.setAttribute("emailVerified", true);

           response.sendRedirect("CustomerTransactionServlet?verified=true");

            return;
        }

        attempts++;
        session.setAttribute("otpAttempts", attempts);

       response.sendRedirect(
    "CustomerTransactionServlet?verified=false&attempts=" + attempts
);

    }
}
