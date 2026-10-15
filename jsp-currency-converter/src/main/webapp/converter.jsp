<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết quả chuyển đổi tiền tệ</title>
    <style>
        * { box-sizing: border-box; }
        body {
            font-family: Arial, sans-serif; background: #f1f5f9;
            display: flex; justify-content: center; align-items: center;
            min-height: 100vh; margin: 0; padding: 20px; color: #172554;
        }
        .result-container {
            background: white; padding: 36px; border-radius: 14px;
            box-shadow: 0 8px 28px rgba(15, 23, 42, .10);
            text-align: center; width: 100%; max-width: 460px;
        }
        h1 { color: #1d4ed8; font-size: 25px; }
        .line { padding: 12px 0; border-bottom: 1px solid #e2e8f0; }
        .total {
            background: #dcfce7; color: #166534; padding: 18px;
            border-radius: 9px; margin-top: 20px; font-size: 23px; font-weight: bold;
            overflow-wrap: anywhere;
        }
        .error { background: #fee2e2; color: #991b1b; padding: 14px; border-radius: 8px; }
        .back {
            display: inline-block; margin-top: 22px; padding: 12px 20px;
            background: #1d4ed8; color: white; text-decoration: none; border-radius: 7px;
        }
        .back:hover { background: #1e40af; }
    </style>
</head>
<body>
    <main class="result-container">
        <h1>KẾT QUẢ CHUYỂN ĐỔI</h1>
        <%
            try {
                String rateText = request.getParameter("rate");
                String usdText = request.getParameter("usd");

                if (rateText == null || usdText == null
                        || rateText.trim().isEmpty() || usdText.trim().isEmpty()) {
                    throw new IllegalArgumentException("Vui lòng nhập đủ tỉ giá và lượng USD.");
                }

                double rate = Double.parseDouble(rateText);
                double usd = Double.parseDouble(usdText);

                if (!Double.isFinite(rate) || !Double.isFinite(usd)
                        || rate <= 0 || usd < 0) {
                    throw new IllegalArgumentException("Tỉ giá phải lớn hơn 0 và USD không được âm.");
                }

                double vnd = rate * usd;
                java.text.NumberFormat numberFormat =
                        java.text.NumberFormat.getNumberInstance(new java.util.Locale("vi", "VN"));
                numberFormat.setMaximumFractionDigits(2);
        %>
            <div class="line">Tỉ giá: <strong><%= numberFormat.format(rate) %> VND/USD</strong></div>
            <div class="line">Lượng USD: <strong>$<%= numberFormat.format(usd) %></strong></div>
            <div class="total">Thành tiền: <%= numberFormat.format(vnd) %> VNĐ</div>
        <%
            } catch (Exception e) {
        %>
            <div class="error">
                <strong>Lỗi xử lý!</strong>
                <p>Dữ liệu không hợp lệ. Vui lòng quay lại và nhập số hợp lệ.</p>
            </div>
        <%
            }
        %>
        <a href="index.jsp" class="back">Quay lại trang chủ</a>
    </main>
</body>
</html>
