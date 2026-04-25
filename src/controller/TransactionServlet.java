package controller;

import dao.TransactionDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class TransactionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long accNo = Long.parseLong(request.getParameter("accNo"));
        String txnType = request.getParameter("txnType");
        double amount = Double.parseDouble(request.getParameter("amount"));

        TransactionDAO dao = new TransactionDAO();
        boolean status = dao.processTransaction(accNo, txnType, amount);

        response.setContentType("text/html");

        if (status) {
        response.sendRedirect("depositWithdraw.jsp?msg=success");
        } else {
            response.sendRedirect("depositWithdraw.jsp?msg=fail");
        }

    }
}
