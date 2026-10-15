<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý User</title>
    <style>
        *{box-sizing:border-box} body{margin:0;padding:32px 16px;font-family:Arial,sans-serif;background:#f3f6fa;color:#243247}
        .container{max-width:1100px;margin:auto;padding:26px;background:#fff;border-radius:12px;box-shadow:0 8px 24px #20334a12}
        h1{text-align:center;color:#1b2a7a;margin-top:0}.toolbar{display:flex;justify-content:space-between;align-items:end;gap:14px;flex-wrap:wrap;margin:22px 0}
        .search{display:flex;gap:8px;align-items:end;flex-wrap:wrap}label{display:block;font-size:14px;font-weight:bold;margin-bottom:6px}
        input,select{padding:10px;border:1px solid #cbd5e1;border-radius:6px;font-size:14px}
        .btn{display:inline-block;border:0;border-radius:6px;padding:10px 13px;color:white;text-decoration:none;cursor:pointer;font-size:14px}
        .primary{background:#2563eb}.green{background:#16a34a}.blue{background:#0284c7}.red{background:#dc2626}.gray{background:#64748b}
        table{width:100%;border-collapse:collapse}th,td{padding:13px 12px;border-bottom:1px solid #e2e8f0;text-align:left}th{background:#1b2a7a;color:white}
        tr:hover td{background:#f8fafc}.empty{text-align:center;color:#64748b;padding:24px}
        @media(max-width:720px){.container{overflow-x:auto}table{min-width:680px}}
    </style>
</head>
<body><main class="container">
<h1>Danh sách người dùng</h1>
<div class="toolbar">
<form class="search" action="${pageContext.request.contextPath}/users" method="get">
    <div><label for="country">Tìm theo quốc gia</label>
    <input id="country" name="country" type="text" value="<c:out value='${country}'/>" placeholder="Nhập quốc gia..."></div>
    <div><label for="sort">Sắp xếp</label><select id="sort" name="sort">
        <option value="name" ${sort == 'name' ? 'selected' : ''}>Tên A → Z</option>
        <option value="id" ${sort == 'id' ? 'selected' : ''}>ID tăng dần</option>
    </select></div>
    <button class="btn primary" type="submit">Tìm kiếm</button>
    <a class="btn gray" href="${pageContext.request.contextPath}/users">Đặt lại</a>
</form>
<a class="btn green" href="${pageContext.request.contextPath}/users?action=create">+ Thêm User</a>
</div>
<table><thead><tr><th>ID</th><th>Tên</th><th>Email</th><th>Quốc gia</th><th>Hành động</th></tr></thead>
<tbody>
<c:forEach var="user" items="${listUser}">
<tr><td><c:out value="${user.id}"/></td><td><c:out value="${user.name}"/></td><td><c:out value="${user.email}"/></td><td><c:out value="${user.country}"/></td>
<td><a class="btn blue" href="${pageContext.request.contextPath}/users?action=edit&id=${user.id}">Sửa</a>
<a class="btn red" href="${pageContext.request.contextPath}/users?action=delete&id=${user.id}">Xóa</a></td></tr>
</c:forEach>
<c:if test="${empty listUser}"><tr><td class="empty" colspan="5">Không tìm thấy người dùng phù hợp.</td></tr></c:if>
</tbody></table>
</main></body></html>
