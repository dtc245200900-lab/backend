# Bài tập Gọi MySQL Stored Procedures từ JDBC

Ứng dụng Java Web quản lý User. Dùng `CallableStatement` gọi Stored Procedure cho:
- Hiển thị danh sách users (`select_all_users`)
- Sửa user (`update_user`)
- Xóa user (`delete_user`)

## Công nghệ
Java 17, Maven WAR, JSP/Servlet, MySQL Connector/J, Tomcat 10.1+.

## 1. Chạy database
Mở MySQL Workbench và chạy toàn bộ file `database.sql`. Script tạo database `demo`, bảng `users` và ba stored procedures.

## 2. Cấu hình kết nối
Mở `src/main/java/com/codegym/dao/UserDAO.java`, sửa thông tin:
```java
private static final String JDBC_USERNAME = "root";
private static final String JDBC_PASSWORD = "password";
```

## 3. Build và deploy
Ở thư mục gốc dự án:
```bash
mvn clean package
```
Copy `target/user-management.war` vào thư mục `webapps` của Tomcat 10.1+ rồi khởi động Tomcat.

Truy cập:
`http://localhost:8080/user-management/users`

## 4. Kiểm tra
- Danh sách: được gọi bằng `CALL select_all_users()` qua `CallableStatement`.
- Sửa: chọn **Sửa**, cập nhật thông tin và lưu; DAO gọi `CALL update_user(?, ?, ?, ?)`.
- Xóa: chọn **Xóa** và xác nhận; DAO gọi `CALL delete_user(?)`.

Lưu ý: `selectUser(id)` và chức năng thêm user vẫn dùng PreparedStatement vì yêu cầu bài chỉ yêu cầu stored procedure cho danh sách, sửa và xóa.
