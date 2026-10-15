<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chuyển đổi tiền tệ USD - VND</title>
    <style>
        * { box-sizing: border-box; }
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #eff6ff, #f8fafc);
            display: flex; justify-content: center; align-items: center;
            min-height: 100vh; margin: 0; padding: 20px; color: #172554;
        }
        .converter-container {
            background: white; padding: 36px; border-radius: 14px;
            box-shadow: 0 8px 28px rgba(15, 23, 42, .10);
            text-align: center; width: 100%; max-width: 420px;
        }
        .icon { font-size: 42px; margin-bottom: 8px; }
        h1 { color: #1d4ed8; font-size: 25px; margin: 0 0 10px; }
        p { color: #64748b; line-height: 1.5; }
        label { display: block; text-align: left; margin: 18px 0 8px; font-weight: bold; }
        input {
            width: 100%; padding: 12px; border: 1px solid #cbd5e1;
            border-radius: 7px; font-size: 16px;
        }
        input:focus { outline: 2px solid #93c5fd; border-color: #2563eb; }
        button {
            width: 100%; padding: 13px; margin-top: 24px; border: 0;
            border-radius: 7px; background: #1d4ed8; color: white;
            font-size: 16px; font-weight: bold; cursor: pointer;
        }
        button:hover { background: #1e40af; }
        .hint { font-size: 13px; margin-top: 16px; }
    </style>
</head>
<body>
    <main class="converter-container">
        <div class="icon" aria-hidden="true">💱</div>
        <h1>USD to VND Converter</h1>
        <p>Nhập tỉ giá và số USD để tính số tiền Việt Nam đồng tương ứng.</p>

        <form action="converter.jsp" method="POST">
            <label for="rate">Tỉ giá (VND/USD)</label>
            <input type="number" id="rate" name="rate" value="25000"
                   min="0.01" step="any" required>

            <label for="usd">Lượng USD cần đổi</label>
            <input type="number" id="usd" name="usd"
                   min="0" step="any" placeholder="Ví dụ: 100" required>

            <button type="submit">Chuyển đổi</button>
        </form>
        <p class="hint">Ví dụ: 100 USD × 25.000 = 2.500.000 VNĐ</p>
    </main>
</body>
</html>
