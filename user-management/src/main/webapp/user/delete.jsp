<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>Xóa User</title>
<style>body{font-family:Arial,sans-serif;background:#f3f6fa;margin:0;padding:40px 16px;color:#243247}.card{max-width:520px;margin:auto;background:white;padding:28px;border-radius:12px;box-shadow:0 8px 24px #20334a12}h1{text-align:center;color:#b91c1c}.info{background:#f8fafc;padding:16px;border-radius:8px;line-height:1.8}button,a{display:inline-block;padding:11px 15px;border:0;border-radius:6px;text-decoration:none;cursor:pointer;margin-top:20px}button{background:#dc2626;color:white}a{background:#64748b;color:white}</style>
</head><body><main class="card"><h1>Xác nhận xóa User</h1><p>Bạn có chắc muốn xóa người dùng này không?</p>
<div class="info"><strong>Tên:</strong> <c:out value="${user.name}"/><br><strong>Email:</strong> <c:out value="${user.email}"/><br><strong>Quốc gia:</strong> <c:out value="${user.country}"/></div>
<form action="${pageContext.request.contextPath}/users?action=delete" method="post"><input type="hidden" name="id" value="<c:out value='${user.id}'/>"><button type="submit">Xác nhận xóa</button><a href="${pageContext.request.contextPath}/users">Hủy</a></form>
</main></body></html>
