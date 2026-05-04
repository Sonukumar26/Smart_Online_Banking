<%@ page import="java.util.*, model.Account" %>
<%@ page session="true" %>

<%
    if (session.getAttribute("customer") == null) {
        response.sendRedirect("customerLogin.jsp");
        return;
    }

    List<Account> accounts = (List<Account>) request.getAttribute("accounts");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Deposit / Withdraw</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f7f9fc;
            margin: 0;
            padding: 30px;
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
            text-align: center;
        }
        form {
            max-width: 420px;
            margin: auto;
            background: white;
            padding: 20px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            border-radius: 8px;
        }
        table {
            border-collapse: separate;
            border-spacing: 10px 15px;
            width: 100%;
        }
        td {
            font-weight: 600;
            color: #555;
        }
        select, input[type="number"] {
            padding: 8px 10px;
            width: 100%;
            border: 1.5px solid #ccc;
            border-radius: 5px;
            font-size: 1rem;
            box-sizing: border-box;
        }
        button {
            padding: 10px 20px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-weight: bold;
            width: 100%;
        }
        button:hover {
            background: #0056b3;
        }
        .message {
            max-width: 420px;
            margin: 10px auto;
            padding: 10px;
            text-align: center;
            font-weight: 600;
            border-radius: 5px;
        }
        .success {
            background: #d4edda;
            color: #155724;
        }
        .error {
            background: #f8d7da;
            color: #721c24;
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
    </style>
</head>

<body>

<h2>Deposit / Withdraw</h2>

<%
    String msg = request.getParameter("msg");
    if ("success".equals(msg)) {
%>
    <div class="message success">Transaction Successful</div>
<% } else if ("fail".equals(msg)) { %>
    <div class="message error">Transaction Failed</div>
<% } %>

<form action="TransactionServlet" method="post" onsubmit="return validateForm()">
    <table>
        <tr>
            <td>Account</td>
            <td>
                <select id="accNo" name="accNo" required>
                    <option value="">Select Account</option>
                    <option value="current">Current</option>
                    <option value="saving">Saving</option>
                    <% if (accounts != null) {
                        for (Account acc : accounts) { %>
                        <option value="<%= acc.getAccNo() %>"><%= acc.getAccNo() %></option>
                    <% }} %>
                </select>

            </td>
        </tr>

        <tr>
            <td>Type</td>
            <td>
                <select name="txnType" required>
                    <option value="DEPOSIT">Deposit</option>
                    <option value="WITHDRAW">Withdraw</option>
                </select>
            </td>
        </tr>

        <tr>
            <td>Amount</td>
            <td>
                <input type="number" id="amount" name="amount" min="1" step="0.01" required placeholder="Enter amount">
            </td>
        </tr>

        <tr>
            <td colspan="2" style="text-align:center;">
                <button type="submit">Submit</button>
            </td>
        </tr>
    </table>
</form>
<br>
 <a href="customerDashboard.jsp" class="back-btn"> Back to Dashboard</a>
<script>
    function validateForm() {
        const accNo = document.getElementById("accNo").value;
        const amount = document.getElementById("amount").value;

        if (!accNo) {
            alert("Please select an account");
            return false;
        }
        if (amount <= 0) {
            alert("Enter a valid amount greater than zero");
            return false;
        }
        return true;
    }
</script>

</body>
</html>
