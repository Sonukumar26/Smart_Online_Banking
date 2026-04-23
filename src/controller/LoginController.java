package controller;

import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// @WebServlet("/LoginController")
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isValidUser = userDAO.validate(username, password);

        if (isValidUser) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
        //    response.sendRedirect("dashboard.jsp");
response.sendRedirect("dashboard");

        } else {
            response.sendRedirect("index.jsp?error=Invalid username or password");
        }
    }
}
