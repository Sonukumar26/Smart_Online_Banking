<%@ page import="java.util.*, model.Account" %>
<%@ page session="true" %>

<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    List<Account> list = (List<Account>) request.getAttribute("accounts");
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html>
<head>
    <title>View Accounts</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f9f9f9;
            margin: 0;
            padding: 0;
        }
        h2 {
            color: #333;
        }
        form {
            margin-bottom: 20px;
        }
        input[type="text"] {
            padding: 8px;
            width: 250px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            padding: 8px 16px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
        }
        button:hover {
            background: #0056b3;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            box-shadow: 0 0 5px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }
        tr:hover {
            background: #f1f1f1;
        }
        p.error {
            color: #d9534f;
            font-weight: bold;
        }
        p.info {
            color: #666;
            font-style: italic;
        }
        .container {
            padding: 20px;
            flex: 1;
        }
    </style>
</head>
<body>

<%@ include file="header.jsp" %>

<div style="display:flex;">

    <%@ include file="sidebar.jsp" %>

    <div class="container">

        <h2>View Accounts</h2>

        <!-- Search Form -->
        <form action="ViewAccountServlet" method="get" onsubmit="return confirm('Search accounts for this customer?');">
            <label for="customerName">Customer Name:</label>
            <input type="text" id="customerName" name="customerName" placeholder="Enter customer name" required autofocus>
            <button type="submit">Search</button>
        </form>

        <!-- Error message -->
        <%
            if (error != null) {
        %>
            <p class="error"><%= error %></p>
        <%
            }
        %>

        <!-- Accounts Table -->
        <%
            if (list == null) {
        %>
            <p class="info">Please search for a customer to view accounts.</p>

        <%
            } else if (list.isEmpty()) {
        %>
            <p class="info">No accounts found for this customer.</p>

        <%
            } else {
        %>
            <table>
                <thead>
                    <tr>
                        <th>Account No</th>
                        <th>Account Type</th>
                        <th>Balance</th>
                        <th>Created At</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    for (Account acc : list) {
                %>
                    <tr>
                        <td><%= acc.getAccNo() %></td>
                        <td><%= acc.getAccType() %></td>
                        <td><%= acc.getBalance() %></td>
                        <td><%= acc.getCreatedAt() %></td>
                    </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        <%
            }
        %>

    </div>
</div>

</body>
</html>
