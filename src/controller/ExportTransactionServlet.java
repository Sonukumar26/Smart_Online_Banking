package controller;

import dao.TransactionDAO;
import model.Transaction;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;

public class ExportTransactionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String type = request.getParameter("type");
        TransactionDAO dao = new TransactionDAO();
        List<Transaction> list = dao.getAllTransactions();

        if ("csv".equalsIgnoreCase(type)) {
            exportCSV(list, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unsupported export type");
        }
    }

    private void exportCSV(List<Transaction> list, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition",
                "attachment; filename=transactions.csv");

        PrintWriter out = response.getWriter();
        out.println("Transaction ID,Account No,Type,Amount,TimeStamp");

        for (Transaction t : list) {
            out.println(
                t.getTxnId() + "," +
                t.getAccNo() + "," +
                t.getTxnType() + "," +
                t.getAmount() + "," +
                t.getTxnTime()
            );
        }
        out.flush();
    }

}
