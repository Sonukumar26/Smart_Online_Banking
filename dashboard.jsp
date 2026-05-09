<%@ page session="true" %>

<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<jsp:include page="header.jsp" />

<div style="display:flex;">

    <jsp:include page="sidebar.jsp" />

    <div style="
        flex:1;
        padding:30px;
        min-height:100vh;
        background-image:url('<%= request.getContextPath() %>/images/bank-bg.jpg');
        background-size:cover;
        background-position:center;
    ">

        <h2 style="color:#fff; margin-bottom:5px;">
            Welcome, <%= session.getAttribute("username") %>
        </h2>
        <p style="color:#eaeaea; margin-bottom:30px;">
            Here's an overview of your banking system
        </p>

        <div style="
            display:grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap:25px;
            max-width:900px;
        ">

            <!-- Total Customers -->
            <div class="card">
                <h3>Total Customers</h3>
                <p>${customers}</p>
            </div>

            <!-- Total Accounts -->
            <div class="card">
                <h3>Total Accounts</h3>
                <p>${accounts}</p>
            </div>

            <!-- Total Balance -->
            <div class="card">
                <h3>Total Balance</h3>
                <p>Rs. ${balance}</p>
            </div>

        </div>
    </div>
</div>

<style>
    .card {
        background: rgba(255, 255, 255, 0.92);
        padding: 25px;
        border-radius: 12px;
        text-align: center;
        box-shadow: 0 6px 20px rgba(0,0,0,0.15);
        transition: transform 0.3s ease, box-shadow 0.3s ease;
        cursor: pointer;
    }

    .card:hover {
        transform: translateY(-6px);
        box-shadow: 0 12px 28px rgba(0,0,0,0.25);
    }

    .card h3 {
        margin-bottom: 10px;
        color: #007bff;
        font-size: 1.1rem;
    }

    .card p {
        font-size: 1.6rem;
        font-weight: bold;
        color: #333;
    }
</style>
