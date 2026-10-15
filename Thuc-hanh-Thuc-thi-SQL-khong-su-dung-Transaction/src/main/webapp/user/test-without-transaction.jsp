<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><title>Test SQL không dùng Transaction</title><style>body{font-family:Arial;max-width:800px;margin:35px auto;padding:0 18px;line-height:1.6}.notice{padding:14px;background:#fff3cd;border:1px solid #ffe08a;border-radius:6px}a{display:inline-block;background:#1769aa;color:white;padding:9px 13px;text-decoration:none;border-radius:5px}</style></head><body>
<h1>Thực thi SQL không sử dụng Transaction</h1>
<div class="notice"><strong>Bài kiểm tra đã chạy.</strong> Chương trình cố ý gán sai tham số trong câu UPDATE. Hai câu INSERT trước đó chạy với auto-commit mặc định nên vẫn được lưu.</div>
<c:if test="${not empty testError}"><p><strong>Lỗi SQL dự kiến:</strong> <c:out value="${testError}"/></p></c:if>
<h3>Kiểm tra trong MySQL</h3><pre>USE demo;
SELECT * FROM Employee;</pre>
<p>Kết quả mong đợi: có hai bản ghi Quynh (salary 10.00) và Ngan (salary 20.00); salary của Quynh chưa đổi thành 999.99.</p>
<p><strong>Lưu ý:</strong> Bài test sẽ DROP rồi tạo lại bảng Employee nên dữ liệu cũ trong bảng Employee sẽ bị xóa.</p>
<a href="${pageContext.request.contextPath}/users">Quay lại danh sách User</a></body></html>