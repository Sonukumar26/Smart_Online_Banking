<%@ page session="true" %>

<%
    if (session.getAttribute("customer") == null) {
        response.sendRedirect("customerLogin.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Customer Dashboard</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #667eea, #764ba2);
            margin: 0;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .dashboard {
            background: #ffffff;
            padding: 30px;
            border-radius: 12px;
            width: 350px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.2);
            text-align: center;
            animation: fadeIn 0.6s ease;
        }

        h2 {
            margin-bottom: 20px;
            color: #333;
        }

        ul {
            list-style: none;
            padding: 0;
        }

        li {
            margin: 15px 0;
        }

        a {
            display: block;
            padding: 12px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: bold;
            transition: background 0.3s ease, transform 0.2s ease;
        }

        a:hover {
            background: #0056b3;
            transform: translateY(-2px);
        }

        .logout {
            background: #dc3545;
        }

        .logout:hover {
            background: #b52a37;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>

<body>

<div class="dashboard">
    <h2>Welcome Customer</h2>

    <ul>
        <li><a href="CustomerAccountServlet">My Accounts</a></li>
        <li><a href="CustomerTransactionServlet">My Transactions</a></li>
   
        <li><a href="depositWithdraw.jsp">Deposit / Withdraw</a></li>
        <li><a href="CustomerLogoutServlet" class="logout">Logout</a></li>
    </ul>
</div>

</body>
</html>
