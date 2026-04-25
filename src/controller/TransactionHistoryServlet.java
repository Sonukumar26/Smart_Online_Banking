package controller;

import dao.TransactionHistoryDAO;
import model.Transaction;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class TransactionHistoryServlet extends HttpServlet {

   protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {


    TransactionHistoryDAO dao = new TransactionHistoryDAO();
    List<Transaction> list = dao.getAllTransactions();

    request.setAttribute("transactions", list);
    request.getRequestDispatcher("transactionHistory.jsp")
           .forward(request, response);
}

}
