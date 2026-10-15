# Bài kiểm tra cuối module — Quản lý sản phẩm JSP & Servlet

## Chức năng
- Hiển thị danh sách sản phẩm (ID, tên, giá, số lượng).
- Thêm, xem, sửa, xóa; xóa có hộp thoại xác nhận.
- MySQL qua JDBC và `PreparedStatement`.
- Kiểm tra tên không rỗng, giá/số lượng không âm.
- Maven WAR, phù hợp Tomcat 10.1+.

## Yêu cầu
- JDK 17+, Maven 3.8+, MySQL 8+, Apache Tomcat 10.1+.

## Cách chạy
1. Mở MySQL Workbench và chạy `database.sql`.
2. Nếu tài khoản MySQL không phải `root` không mật khẩu, đặt biến môi trường:
   - Windows CMD: `set PRODUCT_DB_USER=root` và `set PRODUCT_DB_PASSWORD=mat_khau`
   - Linux/macOS: `export PRODUCT_DB_USER=root` và `export PRODUCT_DB_PASSWORD=mat_khau`
   - Có thể tùy chỉnh URL qua `PRODUCT_DB_URL`.
3. Mở Terminal tại thư mục có `pom.xml`, chạy:
   ```bash
   mvn clean package
   ```
4. Sao chép `target/FinalExam.war` vào `TOMCAT_HOME/webapps/`, khởi động Tomcat.
5. Mở `http://localhost:8080/FinalExam/products`.

## Cấu trúc chính
- `Product.java`: model.
- `DBConnection.java`: kết nối JDBC.
- `ProductDAO.java`: các câu lệnh CRUD.
- `ProductServlet.java`: controller.
- `products.jsp`: danh sách sản phẩm.
- `product-form.jsp`: biểu mẫu thêm/sửa/xem.
- `database.sql`: CSDL và dữ liệu mẫu.

## Đặt tên file nộp
Đổi tên ZIP theo yêu cầu lớp: `[Mã_số_sinh_viên]_FinalExam.zip`.
Ví dụ minh họa: `123456_FinalExam.zip` — thay bằng mã số thật của bạn.
