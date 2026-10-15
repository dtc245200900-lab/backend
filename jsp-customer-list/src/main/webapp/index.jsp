<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách khách hàng</title>
    <style>
        * { box-sizing: border-box; }
        body {
            margin: 0;
            padding: 32px 16px;
            font-family: Arial, sans-serif;
            background: #f3f6fa;
            color: #253247;
        }
        .container {
            max-width: 1050px;
            margin: 0 auto;
            background: #fff;
            padding: 28px;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(25, 45, 75, .08);
        }
        h1 { margin: 0 0 24px; text-align: center; color: #174a7e; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 14px 16px; border-bottom: 1px solid #e6ebf2; text-align: left; }
        th { background: #eaf2fb; color: #174a7e; }
        tbody tr:hover { background: #f8fbff; }
        .avatar {
            width: 76px; height: 76px; object-fit: cover;
            border-radius: 10px; display: block;
        }
        .empty { text-align: center; color: #68778a; padding: 24px; }
        @media (max-width: 650px) {
            .container { padding: 16px; overflow-x: auto; }
            th, td { padding: 10px; white-space: nowrap; }
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Danh sách khách hàng</h1>

    <%-- Danh sách dữ liệu mẫu --%>
    <c:set var="customers" value="${[
        {'name':'Nguyễn Văn An','birthday':'1990-05-12','address':'Hà Nội','image':'https://i.pravatar.cc/150?img=12'},
        {'name':'Trần Thị Bình','birthday':'1988-09-24','address':'Đà Nẵng','image':'https://i.pravatar.cc/150?img=47'},
        {'name':'Lê Minh Cường','birthday':'1995-02-16','address':'TP. Hồ Chí Minh','image':'https://i.pravatar.cc/150?img=13'},
        {'name':'Phạm Thu Dung','birthday':'1992-11-08','address':'Cần Thơ','image':'https://i.pravatar.cc/150?img=44'}
    ]}" />

    <table>
        <thead>
        <tr>
            <th>Ảnh</th>
            <th>Họ và tên</th>
            <th>Ngày sinh</th>
            <th>Địa chỉ</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="customer" items="${customers}">
            <tr>
                <td><img class="avatar" src="<c:out value='${customer.image}'/>" alt="Ảnh khách hàng"></td>
                <td><c:out value="${customer.name}"/></td>
                <td><c:out value="${customer.birthday}"/></td>
                <td><c:out value="${customer.address}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
