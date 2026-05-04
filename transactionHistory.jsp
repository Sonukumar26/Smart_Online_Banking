<%@ page import="java.util.*, model.Transaction" %>
<%@ page session="true" %>

<%
     if (session.getAttribute("username") == null) {
        response.sendRedirect("customerLogin.jsp");
        return;
    }

    List<Transaction> list =
            (List<Transaction>) request.getAttribute("transactions");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Transaction History</title>

    <style>
        body { font-family: Arial, sans-serif; background:#f5f8fa; margin:0; }
        h2 { margin-bottom:20px; }

        table {
            width:100%;
            border-collapse:collapse;
            background:white;
            box-shadow:0 1px 5px rgba(0,0,0,0.1);
        }
        th, td {
            border:1px solid #ddd;
            padding:10px;
            text-align:center;
        }
        th { background:#007bff; color:white; }

        button {
            padding:8px 16px;
            background:#007bff;
            color:white;
            border:none;
            border-radius:5px;
            cursor:pointer;
        }
    </style>
</head>

<body>

<%@ include file="header.jsp" %>

<div style="display:flex;">
    <%@ include file="sidebar.jsp" %>

    <div style="flex:1; padding:20px;">

        <h2>All Transactions (Admin)</h2>

        <% if (list == null || list.isEmpty()) { %>
            <p>No transactions found.</p>
        <% } else { %>

        <table>
            <tr>
                <th>Txn ID</th>
                <th>Account No</th>
                <th>Type</th>
                <th>Amount</th>
                <th>Date</th>
            </tr>

            <% for (Transaction t : list) { %>
            <tr>
                <td><%= t.getTxnId() %></td>
                <td><%= t.getAccNo() %></td>
                <td><%= t.getTxnType() %></td>
                <td><%= t.getAmount() %></td>
                <td><%= t.getTxnTime() %></td>
            </tr>
            <% } %>
        </table>

        <br>

        <!-- ✅ ADMIN EXPORT CSV ONLY -->
        <form action="ExportTransactionServlet" method="get">
            <input type="hidden" name="type" value="csv">
            <button type="submit">Export CSV</button>
        </form>

        <% } %>

    </div>
</div>

</body>
</html>
