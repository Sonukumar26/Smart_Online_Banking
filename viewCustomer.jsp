<%@ page session="true" %>
<%@ page import="java.util.*, model.Customer" %>

<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>View Customers</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f8;
            margin: 0;
        }

        .container {
            display: flex;
        }

        .content {
            flex: 1;
            padding: 30px;
        }

        .card {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            margin-bottom: 20px;
            color: #333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background: #007bff;
            color: white;
            padding: 10px;
            text-align: left;
        }

        td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background: #f1f1f1;
        }

        .empty {
            color: #777;
            padding: 15px;
            text-align: center;
        }
    </style>
</head>

<body>

<%@ include file="header.jsp" %>

<div class="container">
    <%@ include file="sidebar.jsp" %>

    <div class="content">
        <div class="card">
            <h2>Customer List</h2>

            <table>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Mobile</th>
                    <th>Address</th>
                </tr>

                <%
                    List<Customer> customers =
                            (List<Customer>) request.getAttribute("customerList");

                    if (customers != null && !customers.isEmpty()) {
                        for (Customer c : customers) {
                %>
                <tr>
                    <td><%= c.getName() %></td>
                    <td><%= c.getEmail() %></td>
                    <td><%= c.getMobile() %></td>
                    <td><%= c.getAddress() %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="4" class="empty">
                        No customers found
                    </td>
                </tr>
                <%
                    }
                %>
            </table>

        </div>
    </div>
</div>

</body>
</html>
