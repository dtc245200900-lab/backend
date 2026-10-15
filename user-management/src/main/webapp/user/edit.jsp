<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>Sửa User</title>
<style>body{font-family:Arial,sans-serif;background:#f3f6fa;margin:0;padding:40px 16px;color:#243247}.card{max-width:520px;margin:auto;background:white;padding:28px;border-radius:12px;box-shadow:0 8px 24px #20334a12}h1{text-align:center;color:#1b2a7a}label{display:block;font-weight:bold;margin:16px 0 6px}input{width:100%;padding:11px;border:1px solid #cbd5e1;border-radius:6px;box-sizing:border-box}button,a{display:inline-block;padding:11px 15px;border:0;border-radius:6px;text-decoration:none;cursor:pointer;margin-top:20px}button{background:#2563eb;color:white}a{background:#64748b;color:white}</style>
</head><body><main class="card"><h1>Cập nhật User</h1>
<form action="${pageContext.request.contextPath}/users?action=edit" method="post">
<input type="hidden" name="id" value="<c:out value='${user.id}'/>">
<label for="name">Họ tên</label><input id="name" name="name" required maxlength="120" value="<c:out value='${user.name}'/>">
<label for="email">Email</label><input id="email" name="email" type="email" required maxlength="220" value="<c:out value='${user.email}'/>">
<label for="country">Quốc gia</label><input id="country" name="country" maxlength="120" value="<c:out value='${user.country}'/>">
<button type="submit">Lưu thay đổi</button><a href="${pageContext.request.contextPath}/users">Hủy</a>
</form></main></body></html>
