<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ứng dụng Calculator</title>
    <style>
        * { box-sizing: border-box; }
        body { margin: 0; font-family: Arial, sans-serif; background: #f1f5f9; color: #243247; }
        .card { max-width: 520px; margin: 64px auto; padding: 28px; background: white; border-radius: 12px; box-shadow: 0 8px 26px #1e293b18; }
        h1 { margin-top: 0; text-align: center; color: #175a91; }
        label { display: block; margin: 16px 0 7px; font-weight: bold; }
        input, select { width: 100%; padding: 11px 12px; border: 1px solid #cbd5e1; border-radius: 7px; font-size: 16px; }
        button { width: 100%; margin-top: 22px; padding: 12px; border: 0; border-radius: 7px; background: #1769aa; color: white; font-size: 16px; font-weight: bold; cursor: pointer; }
        button:hover { background: #125487; }
    </style>
</head>
<body>
<main class="card">
    <h1>Máy tính đơn giản</h1>
    <form action="${pageContext.request.contextPath}/calculate" method="post">
        <label for="firstOperand">Số thứ nhất</label>
        <input type="number" step="any" id="firstOperand" name="firstOperand" required>

        <label for="operator">Phép toán</label>
        <select id="operator" name="operator" required>
            <option value="+">Cộng (+)</option>
            <option value="-">Trừ (-)</option>
            <option value="*">Nhân (×)</option>
            <option value="/">Chia (÷)</option>
        </select>

        <label for="secondOperand">Số thứ hai</label>
        <input type="number" step="any" id="secondOperand" name="secondOperand" required>

        <button type="submit">Tính kết quả</button>
    </form>
</main>
</body>
</html>
