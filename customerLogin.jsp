<!DOCTYPE html>
<html>
<head>
    <title>Customer Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f7f8;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .login-container {
            background: white;
            padding: 30px 40px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
            width: 320px;
        }
        h2 {
            margin-bottom: 25px;
            color: #333;
            text-align: center;
        }
        label {
            font-weight: 600;
            display: block;
            margin-bottom: 6px;
            color: #555;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px 12px;
            margin-bottom: 15px;
            border-radius: 5px;
            border: 1.5px solid #ccc;
            font-size: 1rem;
            transition: border-color 0.3s ease;
        }
        input[type="text"]:focus, input[type="password"]:focus {
            border-color: #007bff;
            outline: none;
        }
        .toggle-password {
            font-size: 0.9rem;
            cursor: pointer;
            color: #007bff;
            user-select: none;
            margin-bottom: 15px;
            display: inline-block;
        }
        button {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            border: none;
            color: white;
            font-weight: 600;
            border-radius: 5px;
            font-size: 1.1rem;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        button:hover {
            background-color: #0056b3;
        }
        .error-msg {
            color: red;
            font-weight: 600;
            margin-bottom: 15px;
            text-align: center;
        }
        .info-msg {
            color: #333;
            font-size: 0.9rem;
            margin-bottom: 20px;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="login-container">
    <h2>Customer Login</h2>

    <form id="loginForm" action="CustomerLoginServlet" method="post" novalidate>
        <label for="username">Username / Email</label>
        <input type="text" id="username" name="username" placeholder="Enter username or email" required>
        <div id="usernameError" class="error-msg" style="display:none;"></div>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" placeholder="Enter your password" required>
        <div class="toggle-password" onclick="togglePassword()">Show Password</div>

        <button type="submit">Login</button>

       <%
    String errorMsg = request.getParameter("error");
    if (errorMsg != null) {
%>
    <div class="error-msg">
        <%= errorMsg %>
    </div>
<%
    }
%>

    </form>
</div>

<script>
    const usernameInput = document.getElementById('username');
    const usernameError = document.getElementById('usernameError');
    const loginForm = document.getElementById('loginForm');
    const passwordInput = document.getElementById('password');
    const togglePasswordText = document.querySelector('.toggle-password');

    // Toggle password visibility
    function togglePassword() {
        if (passwordInput.type === "password") {
            passwordInput.type = "text";
            togglePasswordText.textContent = "Hide Password";
        } else {
            passwordInput.type = "password";
            togglePasswordText.textContent = "Show Password";
        }
    }

    // Simple validation on username/email
    function validateUsername() {
        const val = usernameInput.value.trim();
        if (val.length === 0) {
            usernameError.textContent = "Username or Email is required.";
            usernameError.style.display = "block";
            return false;
        }

        // If it contains '@', validate email format roughly
        if (val.includes("@")) {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(val)) {
                usernameError.textContent = "Please enter a valid email address.";
                usernameError.style.display = "block";
                return false;
            }
        }

        usernameError.style.display = "none";
        return true;
    }

    usernameInput.addEventListener('input', validateUsername);

    // On form submit validate before sending
    loginForm.addEventListener('submit', function(event) {
        if (!validateUsername()) {
            event.preventDefault();
            usernameInput.focus();
        }
    });
</script>

</body>
</html>
