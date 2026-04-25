package controller;

import dao.DashboardDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DashboardDAO dao = new DashboardDAO();

        request.setAttribute("customers", dao.getCustomerCount());
        request.setAttribute("accounts", dao.getAccountCount());
        request.setAttribute("balance", dao.getTotalBalance());

 

        request.getRequestDispatcher("dashboard.jsp")
                .forward(request, response);
    }
}
