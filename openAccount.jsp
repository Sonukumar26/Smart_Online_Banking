<%@ page session="true" %>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Open Bank Account</title>

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
            background: #ffffff;
            width: 420px;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            margin-bottom: 20px;
            color: #333;
        }

        table {
            width: 100%;
        }

        td {
            padding: 8px 0;
        }

        input, select {
            width: 100%;
            padding: 8px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }

        input:focus, select:focus {
            border-color: #007bff;
            outline: none;
        }

        button {
            background: #28a745;
            color: white;
            border: none;
            padding: 10px;
            width: 100%;
            font-size: 15px;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background: #218838;
        }

        .success {
            background: #d4edda;
            color: #155724;
            padding: 12px;
            border-radius: 5px;
            margin-bottom: 15px;
        }

        .acc-no {
            font-size: 18px;
            color: #0c5460;
        }
    </style>
</head>

<body>

<%@ include file="header.jsp" %>

<div class="container">
    <%@ include file="sidebar.jsp" %>

    <div class="content">
        <div class="card">

            <h2>Open New Bank Account</h2>

            <%
                String msg = request.getParameter("msg");
                String accNo = request.getParameter("accNo");

                if ("success".equals(msg)) {
            %>
                <div class="success">
                    ✔ Account Opened Successfully!<br>
                    Account Number:
                    <div class="acc-no"><b><%= accNo %></b></div>
                </div>
            <%
                }
            %>

            <form action="OpenAccount" method="post">

                <table>
                    <tr>
                        <td>Customer Name</td>
                        <td>
                            <input type="text" name="customerName"
                                   placeholder="Enter customer full name" required>
                        </td>
                    </tr>

                    <tr>
                        <td>Account Type</td>
                        <td>
                            <select name="accType" required>
                                <option value="">-- Select Account Type --</option>
                                <option value="Saving">Saving</option>
                                <option value="Current">Current</option>
                            </select>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2">
                            <button type="submit">Open Account</button>
                        </td>
                    </tr>
                </table>

            </form>

        </div>
    </div>
</div>

</body>
</html>
