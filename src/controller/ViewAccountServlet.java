package controller;

import dao.AccountDAO;
import dao.CustomerDAO;

import model.Account;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class ViewAccountServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String customerName = request.getParameter("customerName");

        if (customerName == null || customerName.trim().isEmpty()) {
            request.setAttribute("error", "Please enter customer name");
            request.getRequestDispatcher("viewAccounts.jsp")
                   .forward(request, response);
            return;
        }

        
        CustomerDAO cdao = new CustomerDAO();
        int customerId = cdao.getCustomerIdByName(customerName);
        
        if (customerId == -1) {
            request.setAttribute("error", "Customer not found");
            request.getRequestDispatcher("viewAccounts.jsp")
                   .forward(request, response);
            return;
        }

       
        AccountDAO dao = new AccountDAO();
        List<Account> list = dao.getAccountsByCustomerId(customerId);

        request.setAttribute("accounts", list);
        request.getRequestDispatcher("viewAccounts.jsp")
               .forward(request, response);

               
    }
}
