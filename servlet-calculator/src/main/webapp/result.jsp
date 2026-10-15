<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Kết quả phép tính</title>
    <style>
        * { box-sizing: border-box; }
        body { margin: 0; font-family: Arial, sans-serif; background: #f1f5f9; color: #243247; }
        .card { max-width: 560px; margin: 64px auto; padding: 28px; background: white; border-radius: 12px; box-shadow: 0 8px 26px #1e293b18; }
        h1 { margin-top: 0; color: #175a91; text-align: center; }
        .result { padding: 18px; background: #eaf7ee; border-radius: 8px; text-align: center; font-size: 20px; overflow-wrap: anywhere; }
        .error { padding: 16px; background: #fff0f0; color: #b42318; border-radius: 8px; text-align: center; }
        a { display: block; margin-top: 22px; text-align: center; color: #1769aa; text-decoration: none; }
    </style>
</head>
<body>
<main class="card">
    <h1>Kết quả phép tính</h1>
    <% if (request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
    <% } else { %>
        <div class="result">
            <%= request.getAttribute("firstOperand") %>
            <%= request.getAttribute("operator") %>
            <%= request.getAttribute("secondOperand") %>
            = <strong><%= request.getAttribute("result") %></strong>
        </div>
    <% } %>
    <a href="<%= request.getContextPath() %>/index.jsp">← Quay lại máy tính</a>
</main>
</body>
</html>
