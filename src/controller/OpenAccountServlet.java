package controller;

import dao.AccountDAO;
import dao.CustomerDAO;
import util.AccountNumberGenerator;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class OpenAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String customerName = request.getParameter("customerName");
        String accType = request.getParameter("accType");

         CustomerDAO cdao = new CustomerDAO();
        int customerId = cdao.getCustomerIdByName(customerName);

        if (customerId == -1) {
            response.sendRedirect("openAccount.jsp?msg=customer_not_found");
            return;
        }
        
        long accNo = AccountNumberGenerator.generateAccountNumber();

        AccountDAO dao = new AccountDAO();
        boolean status = dao.openAccount(accNo, customerId, accType);

        
        if (status) {
            response.sendRedirect("openAccount.jsp?msg=success&accNo=" + accNo);
        } else {
            response.sendRedirect("openAccount.jsp?msg=error");
        }
    }
}
