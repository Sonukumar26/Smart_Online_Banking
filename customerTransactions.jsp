<%@ page import="java.util.*, model.Transaction" %>
<%@ page session="true" %>

<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("customerLogin.jsp");
        return;
    }

    List<Transaction> list =
        (List<Transaction>) request.getAttribute("transactions");

    String mailStatus  = request.getParameter("mail");
    String otpSent     = request.getParameter("otp");
    String otpVerified = request.getParameter("verified");
    String attempts    = request.getParameter("attempts");

    if (otpVerified == null) otpVerified = "false";
%>

<!DOCTYPE html>
<html>
<head>
    <title>My Transactions</title>
    <style>
        body { font-family: Arial; background:#f4f6f9; }
        table {
            width:100%;
            border-collapse:collapse;
            background:white;
        }
        th, td {
            border:1px solid #ddd;
            padding:10px;
            text-align:center;
        }
        th { background:#007bff; color:white; }
        button {
            padding:8px 14px;
            background:#007bff;
            color:white;
            border:none;
            border-radius:5px;
        }
        button:disabled {
            background:#999;
            cursor:not-allowed;
        }
        .success { color:green; }
        .error { color:red; }
        .back-btn { display: inline-block; margin-top: 20px; padding: 10px 18px; background: #6c757d; color: white; text-decoration: none; border-radius: 8px; transition: background 0.3s; } .back-btn:hover { background: #545b62; }
    </style>
</head>

<body>

<h2>My Transaction History</h2>

<!-- STATUS MESSAGES -->
<% if ("sent".equals(mailStatus)) { %>
    <p class="success">Transaction CSV emailed successfully</p>
<% } else if ("fail".equals(mailStatus)) { %>
    <p class="error">Failed to email CSV</p>
<% } %>

<% if ("sent".equals(otpSent)) { %>
    <p class="success">OTP sent to your email</p>
<% } else if ("fail".equals(otpSent)) { %>
    <p class="error">Failed to send OTP</p>
<% } %>

<% if ("true".equals(otpVerified)) { %>
    <p class="success">Email verified successfully</p>
<% } else if ("false".equals(otpVerified) && otpSent != null) { %>
    <p class="error">OTP verification failed</p>
<% } %>

<% if (attempts != null) { %>
    <p class="error">Invalid OTP. Attempt <%= attempts %> of 3</p>
<% } %>

<!-- TRANSACTION TABLE -->
<% if (list == null || list.isEmpty()) { %>
    <p>No transactions found.</p>
<% } else { %>

<table>
    <tr>
        <th>Account</th>
        <th>Type</th>
        <th>Amount</th>
        <th>Date</th>
    </tr>

    <% for (Transaction t : list) { %>
    <tr>
        <td><%= t.getAccNo() %></td>
        <td><%= t.getTxnType() %></td>
        <td>₹ <%= t.getAmount() %></td>
        <td><%= t.getTxnTime() %></td>
    </tr>
    <% } %>
</table>

<% } %>

<br>

<!-- SEND OTP -->
<form action="SendEmailOTPServlet" method="post" onsubmit="return validateEmail();">
    Email:
    <input type="email" id="email" name="email" required>
    <button type="submit">Send OTP</button>
</form>

<br>

<!-- VERIFY OTP -->
<form action="VerifyEmailOTPServlet" method="post" onsubmit="return validateOTP();">
    OTP:
    <input type="text" id="otp" name="otp" required>
    <button type="submit">Verify OTP</button>
</form>

<br>

<!-- EMAIL CSV -->
<form action="EmailTransactionCSVServlet" method="post">
    <input type="hidden" id="emailHidden" name="email">
    <button id="emailCSVBtn" disabled>Email CSV</button>
</form>
<br>
 <a href="customerDashboard.jsp" class="back-btn"> Back to Dashboard</a>
<script>
    /* =========================
       SEARCH + SORT (OLD LOGIC)
       ========================= */

    function filterTable() {
        let input = document.getElementById("searchInput").value.toLowerCase();
        let rows = document.querySelectorAll("#txnTable tbody tr");

        rows.forEach(row => {
            let text = row.innerText.toLowerCase();
            row.style.display = text.includes(input) ? "" : "none";
        });
    }

    function sortTable(colIndex) {
        let table = document.getElementById("txnTable");
        let rows = Array.from(table.rows).slice(1);
        let asc = table.getAttribute("data-sort") !== "asc";

        rows.sort((a, b) => {
            let A = a.cells[colIndex].innerText.trim();
            let B = b.cells[colIndex].innerText.trim();
            return asc ? A.localeCompare(B) : B.localeCompare(A);
        });

        rows.forEach(r => table.tBodies[0].appendChild(r));
        table.setAttribute("data-sort", asc ? "asc" : "desc");
    }

    /* =========================
       EMAIL + OTP (NEW LOGIC)
       ========================= */

    const otpVerified = "<%= otpVerified %>";

    function validateEmail() {
        const email = document.getElementById("email").value.trim();
        if (!email) {
            alert("Enter a valid email");
            return false;
        }
        document.getElementById("emailHidden").value = email;
        return true;
    }

    function validateOTP() {
        const otp = document.getElementById("otp").value.trim();
        if (!otp) {
            alert("Enter OTP");
            return false;
        }
        return true;
    }

    document.addEventListener("DOMContentLoaded", function () {
        if (otpVerified === "true") {
            const btn = document.getElementById("emailCSVBtn");
            if (btn) {
                btn.disabled = false;
            }
        }
    });
</script>

</body>
</html>
