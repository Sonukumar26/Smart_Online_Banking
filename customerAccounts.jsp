<%@ page import="java.util.*, model.Account" %>
<%@ page session="true" %>

<%
    if (session.getAttribute("customer") == null) {
        response.sendRedirect("customerLogin.jsp");
        return;
    }

    List<Account> list = (List<Account>) request.getAttribute("accounts");
%>

<!DOCTYPE html>
<html>
<head>
    <title>My Accounts</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(120deg, #43cea2, #185a9d);
            margin: 0;
            padding: 30px;
        }

        .container {
            max-width: 800px;
            margin: auto;
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.2);
            animation: fadeIn 0.6s ease;
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            padding: 12px;
            text-align: center;
        }

        th {
            background: #007bff;
            color: white;
        }

        tr {
            transition: background 0.3s ease;
        }

        tr:hover {
            background: #f2f7ff;
        }

        .balance {
            font-weight: bold;
            color: #28a745;
        }

        .no-data {
            text-align: center;
            color: #dc3545;
            margin-top: 20px;
            font-weight: bold;
        }

        .back-btn {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 18px;
            background: #6c757d;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            transition: background 0.3s;
        }

        .back-btn:hover {
            background: #545b62;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>

<body>

<div class="container">
    <h2> My Bank Accounts</h2>

    <% if (list == null || list.isEmpty()) { %>
        <div class="no-data">No accounts found.</div>
    <% } else { %>

    <table>
        <tr>
            <th>Account No</th>
            <th>Account Type</th>
            <th>Balance (Rs.)</th>
        </tr>

        <% for (Account acc : list) { %>
        <tr>
            <td><%= acc.getAccNo() %></td>
            <td><%= acc.getAccType() %></td>
            <td class="balance"><%= acc.getBalance() %></td>
        </tr>
        <% } %>
    </table>

    <% } %>

    <a href="customerDashboard.jsp" class="back-btn"> Back to Dashboard</a>
</div>

</body>
</html>
