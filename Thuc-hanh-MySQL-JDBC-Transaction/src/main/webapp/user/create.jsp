<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><title>Thêm User</title>
<style>body{font-family:Arial,sans-serif;max-width:650px;margin:35px auto;padding:0 18px}h1{color:#174ea6}.field{margin:14px 0}input[type=text],input[type=email]{box-sizing:border-box;width:100%;padding:10px;border:1px solid #bbb;border-radius:5px}button,a{background:#1769aa;color:white;padding:10px 14px;border:0;border-radius:5px;text-decoration:none;cursor:pointer}.permissions{display:flex;gap:15px;flex-wrap:wrap;margin-top:8px}</style></head><body>
<h1>Thêm User và phân quyền</h1>
<form method="post" action="${pageContext.request.contextPath}/users">
<input type="hidden" name="action" value="insert">
<div class="field"><label for="name">Họ tên</label><input id="name" type="text" name="name" required maxlength="120"></div>
<div class="field"><label for="email">Email</label><input id="email" type="email" name="email" required maxlength="120"></div>
<div class="field"><label for="country">Quốc gia</label><input id="country" type="text" name="country" required maxlength="80"></div>
<div class="field"><strong>Quyền hạn (Permissions):</strong><div class="permissions">
<label><input type="checkbox" name="permissions" value="1"> Thêm (Add)</label>
<label><input type="checkbox" name="permissions" value="2"> Sửa (Edit)</label>
<label><input type="checkbox" name="permissions" value="3"> Xóa (Delete)</label>
<label><input type="checkbox" name="permissions" value="4"> Xem (View)</label>
</div></div>
<button type="submit">Lưu thông tin</button> <a href="${pageContext.request.contextPath}/users">Quay lại</a>
</form></body></html>