<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><title>Quản lý User - Transaction</title>
<style>body{font-family:Arial;max-width:1000px;margin:35px auto;padding:0 18px}h1{color:#174ea6}a{background:#1769aa;color:white;padding:9px 13px;text-decoration:none;border-radius:5px;display:inline-block}table{border-collapse:collapse;width:100%;margin-top:20px}th,td{padding:11px;border:1px solid #ddd;text-align:left}th{background:#eef4fb}.msg{padding:10px;background:#e8f5e9;margin:14px 0}</style></head><body>
<h1>Quản lý User</h1>
<c:if test="${param.created == '1'}"><div class="msg">Thêm User và phân quyền thành công trong cùng transaction.</div></c:if>
<c:if test="${param.updated == '1'}"><div class="msg">Cập nhật User thành công.</div></c:if>
<c:if test="${param.deleted == '1'}"><div class="msg">Xóa User thành công.</div></c:if>
<a href="${pageContext.request.contextPath}/users?action=create">+ Thêm User</a>
<table><thead><tr><th>ID</th><th>Họ tên</th><th>Email</th><th>Quốc gia</th><th>Thao tác</th></tr></thead><tbody>
<c:forEach var="u" items="${users}"><tr><td>${u.id}</td><td><c:out value="${u.name}"/></td><td><c:out value="${u.email}"/></td><td><c:out value="${u.country}"/></td><td><a href="${pageContext.request.contextPath}/users?action=edit&id=${u.id}">Sửa</a> <a href="${pageContext.request.contextPath}/users?action=delete&id=${u.id}" onclick="return confirm('Bạn có chắc muốn xóa user này?')">Xóa</a></td></tr></c:forEach>
<c:if test="${empty users}"><tr><td colspan="5">Chưa có người dùng.</td></tr></c:if></tbody></table></body></html>