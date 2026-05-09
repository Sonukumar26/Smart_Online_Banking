<%@ page session="true" %>

<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    String msg = request.getParameter("msg");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Add Customer</title>

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
            padding: 25px;
            width: 450px;
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

        input, textarea {
            width: 100%;
            padding: 8px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }

        input:focus, textarea:focus {
            border-color: #007bff;
            outline: none;
        }

        button {
            background: #007bff;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            font-size: 15px;
        }

        button:hover {
            background: #0056b3;
        }

        .success {
            background: #d4edda;
            color: #155724;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 15px;
        }

        .error {
            background: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 15px;
        }
    </style>
</head>

<body>

<%@ include file="header.jsp" %>

<div class="container">
    <%@ include file="sidebar.jsp" %>

    <div class="content">
        <div class="card">

            <h2>Add New Customer</h2>

            <%-- Message Section --%>
            <% if ("success".equals(msg)) { %>
                <div class="success">Customer added successfully</div>
            <% } else if ("emailExists".equals(msg)) { %>
                <div class="error">Email already exists</div>
            <% } else if ("mobileExists".equals(msg)) { %>
                <div class="error">Mobile number already exists</div>
            <% } else if ("error".equals(msg)) { %>
                <div class="error">Something went wrong. Please try again.</div>
            <% } %>

            <form action="AddCustomerController" method="post">

                <table>
                    <tr>
                        <td>Full Name</td>
                        <td>
                            <input type="text" name="name"
                                   placeholder="Enter full name" required>
                        </td>
                    </tr>

                    <tr>
                        <td>Email</td>
                        <td>
                            <input type="email" name="email"
                                   placeholder="example@gmail.com" required>
                        </td>
                    </tr>

                    <tr>
                        <td>Mobile</td>
                        <td>
                            <input type="text" name="mobile"
                                   placeholder="10 digit mobile number" required>
                        </td>
                    </tr>

                    <tr>
                        <td>Address</td>
                        <td>
                            <textarea name="address" rows="3"
                                      placeholder="Customer address"></textarea>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2">
                            <button type="submit">Add Customer</button>
                        </td>
                    </tr>
                </table>

            </form>

        </div>
    </div>
</div>

</body>
</html>
