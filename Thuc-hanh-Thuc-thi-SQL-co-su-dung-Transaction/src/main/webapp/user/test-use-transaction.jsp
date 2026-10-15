<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><title>Test JDBC Transaction</title><style>body{font-family:Arial;max-width:800px;margin:35px auto;padding:0 18px;line-height:1.6}.notice{padding:14px;background:#eef4fb;border:1px solid #c8dcf2;border-radius:6px}.error{color:#a12622}a{display:inline-block;background:#1769aa;color:white;padding:9px 13px;text-decoration:none;border-radius:5px}</style></head><body>
<h1>Thực thi SQL có sử dụng Transaction</h1><div class="notice"><c:out value="${testMessage}"/></div>
<c:if test="${not empty testError}"><p class="error"><strong>Lỗi SQL dự kiến:</strong> <c:out value="${testError}"/></p></c:if>
<h3>Kiểm tra kết quả</h3><pre>USE demo;
SELECT * FROM Employee;</pre>
<p><strong>Lần 1 (cố tình gây lỗi):</strong> bảng Employee không còn bản ghi Quynh/Ngan được thêm trong transaction vừa chạy, do rollback.</p>
<p><strong>Lần 2 (sửa lỗi):</strong> trong UserDAO.java đổi <code>psUpdate.setBigDecimal(2, new BigDecimal("999.99"))</code> thành <code>psUpdate.setBigDecimal(1, new BigDecimal("999.99"))</code>, đóng gói lại và chạy lại. Khi đó 2 nhân viên được lưu, lương Quynh thành 999.99.</p>
<p><strong>Lưu ý:</strong> Mỗi lần chạy, bài test DROP rồi tạo lại bảng Employee trước transaction; dữ liệu Employee cũ sẽ bị xóa.</p>
<a href="${pageContext.request.contextPath}/users">Quay lại danh sách User</a></body></html>