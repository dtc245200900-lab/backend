<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý User</title>
</head>
<body>
    <h1>Danh sách User</h1>

    <form method="get" action="${pageContext.request.contextPath}/users">
        <label for="country">Tìm theo quốc gia:</label>
        <input id="country" name="country" type="search" value="<c:out value='${requestScope.country}'/>" />
        <button type="submit">Tìm kiếm</button>
        <a href="${pageContext.request.contextPath}/users">Xóa bộ lọc</a>
    </form>

    <p>Kết quả được sắp xếp theo tên (A–Z).</p>
    <table>
        <thead>
            <tr><th>ID</th><th>Tên</th><th>Email</th><th>Quốc gia</th></tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${requestScope.listUser}">
                <tr>
                    <td><c:out value="${user.id}" /></td>
                    <td><c:out value="${user.name}" /></td>
                    <td><c:out value="${user.email}" /></td>
                    <td><c:out value="${user.country}" /></td>
                </tr>
            </c:forEach>
            <c:if test="${empty requestScope.listUser}">
                <tr><td colspan="4">Không tìm thấy User phù hợp.</td></tr>
            </c:if>
        </tbody>
    </table>
</body>
</html>
