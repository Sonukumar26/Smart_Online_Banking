package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import dao.CustomerDAO;
import model.Customer;

public class ViewCustomerController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CustomerDAO dao = new CustomerDAO();
        List<Customer> list = dao.getAllCustomers();

        request.setAttribute("customerList", list);
        request.getRequestDispatcher("viewCustomer.jsp").forward(request, response);
    }
}
