<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Kết quả tra cứu</title>
        <style>
            body { font-family: 'Arial', sans-serif; display: flex; justify-content: center; margin-top: 100px; background-color: #f8fafc; }
            .result-container { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); text-align: center; width: 400px; }
            .back-btn { text-decoration: none; padding: 10px 20px; background-color: #1b2a7a; color: white; border-radius: 4px; display: inline-block; margin-top: 20px;}
        </style>
    </head>
    <body>
        <div class="result-container">
            <h2 style="color: #1b2a7a;">KẾT QUẢ TRA CỨU</h2>
            
            <%
                request.setCharacterEncoding("UTF-8");
                String searchWord = request.getParameter("search");
                
                Map<String, String> dic = new HashMap<>();
                dic.put("hello", "Xin chào");
                dic.put("how", "Thế nào");
                dic.put("book", "Quyển sách");
                dic.put("computer", "Máy tính");
                dic.put("student", "Sinh viên");
                
                String result = null;
                if (searchWord != null && !searchWord.trim().isEmpty()) {
                    result = dic.get(searchWord.trim().toLowerCase());
                }
            %>
            
            <%
                if (result != null) {
            %>
                <p style="font-size: 18px; color: #555;">Từ cần tra: <b style="color: #1b2a7a;"><%= searchWord %></b></p>
                <div style="background-color: #e8f5e9; padding: 15px; border-radius: 8px; margin-top: 20px;">
                    <h3 style="color: #27ae60; font-size: 24px; margin: 0;">Nghĩa là: <%= result %></h3>
                </div>
            <%
                } else {
            %>
                <div style="background-color: #ffebee; padding: 15px; border-radius: 8px; margin-top: 20px;">
                    <h3 style="color: #c0392b; margin: 0;">Không tìm thấy!</h3>
                    <p style="color: #555;">Từ khóa <b style="color: #c0392b;"><%= searchWord %></b> không có trong từ điển.</p>
                </div>
            <%
                }
            %>
            
            <a href="index.jsp" class="back-btn">Quay lại trang chủ</a>
        </div>
    </body>
</html>