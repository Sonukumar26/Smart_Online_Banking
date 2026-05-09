<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>

<%@ page session="true" %>
<!-- <%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%> -->

<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Welcome - Smart Online Banking</title>
</head>
<body>
   <h2>Welcome, <%= session.getAttribute("username") %></h2>

    <a href="LogoutController">Logout</a>

</body>
</html>
