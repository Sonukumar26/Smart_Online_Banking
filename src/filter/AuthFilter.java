package filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        String uri = req.getRequestURI();

        /* ---------------- PUBLIC PAGES ---------------- */
        if (uri.endsWith("index.jsp")
                || uri.endsWith("customerLogin.jsp")
                || uri.endsWith("LoginController")
                || uri.endsWith("CustomerLoginServlet")
                || uri.contains("/css/")
                || uri.contains("/js/")
                || uri.contains("/images/")) {

            chain.doFilter(request, response);
            return;
        }

        /* ---------------- ADMIN PAGES ---------------- */
        boolean isAdminPage =
                uri.contains("AddCustomer")
             || uri.contains("OpenAccount")
             || uri.contains("ViewCustomer")
             || uri.contains("ViewAccount")
             || uri.contains("dashboard");

        if (isAdminPage) {
            if (session != null && session.getAttribute("username") != null) {
                chain.doFilter(request, response); // admin allowed
            } else {
                res.sendRedirect(req.getContextPath() + "/index.jsp");
            }
            return;
        }

        /* ---------------- CUSTOMER PAGES ---------------- */
        boolean isCustomerPage =
                uri.contains("customer")
             || uri.contains("CustomerAccount")
             || uri.contains("customerDashboard");

        if (isCustomerPage) {
            if (session != null && session.getAttribute("username") != null) {
                chain.doFilter(request, response); // customer allowed
            } else {
                res.sendRedirect(req.getContextPath() + "/customerLogin.jsp");
            }
            return;
        }

        /* ---------------- DEFAULT RULE ---------------- */
        if (session != null &&
           (session.getAttribute("username") != null
         || session.getAttribute("customer") != null)) {

            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }
}
