# User Management - Stored Procedures

Ứng dụng JSP/Servlet/JDBC/MySQL có CRUD, tìm kiếm theo country, sắp xếp theo name và sử dụng Stored Procedure cho:
- Tìm User theo ID: `get_user_by_id`
- Thêm User: `insert_user`

## Chuẩn bị
1. Dùng JDK 17, Maven, MySQL 8.x và Tomcat 10.1+.
2. Chạy file `database.sql` trong MySQL Workbench/DBeaver để tạo database, bảng, dữ liệu mẫu và hai Stored Procedure.
3. Mở `src/main/java/com/codegym/dao/UserDAO.java`, sửa `JDBC_USERNAME` và `JDBC_PASSWORD` theo cấu hình máy.

## Build và deploy
Tại thư mục chứa `pom.xml`:
```bash
mvn clean package
```
Sao chép `target/user-management.war` vào thư mục `webapps` của Tomcat 10.1+ và truy cập:
`http://localhost:8080/user-management/users`

## Điểm cập nhật theo bài
- `UserDAO.getUserById(int id)` gọi `{CALL get_user_by_id(?)}` bằng `CallableStatement`.
- `UserDAO.insertUserStore(User user)` gọi `{CALL insert_user(?, ?, ?)}` bằng `CallableStatement`.
- `UserServlet.showEditForm()` sử dụng `getUserById()`.
- `UserServlet.doPost()` sử dụng `insertUserStore()` khi thêm mới.
- Giữ tìm kiếm theo country, sắp xếp theo name và các chức năng CRUD khác.
