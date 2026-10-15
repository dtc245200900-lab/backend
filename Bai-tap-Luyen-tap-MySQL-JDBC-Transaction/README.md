# Bài tập Luyện tập MySQL JDBC Transaction

Ứng dụng Java Web quản lý User có chức năng thêm User và phân quyền trong cùng JDBC transaction. DAO sử dụng `setAutoCommit(false)`, `commit()` và `rollback()`.

## Công nghệ
- Java 17, Maven WAR
- Jakarta Servlet 6 / Tomcat 10.1+
- JSP + JSTL
- MySQL 8 / MySQL Connector/J

## 1. Chuẩn bị database
Mở MySQL Workbench và chạy file `database.sql`. Script tạo database `demo`, bảng `users`, `permission`, `user_permission` và dữ liệu permission mẫu.

## 2. Cấu hình MySQL
Mở `src/main/java/com/codegym/dao/UserDAO.java`, sửa:
```java
private static final String JDBC_USERNAME = "root";
private static final String JDBC_PASSWORD = "password";
```

## 3. Build và deploy
Trong thư mục gốc dự án:
```bash
mvn clean package
```
Copy `target/user-management.war` vào `webapps` của Tomcat 10.1+ và khởi động Tomcat.

Truy cập:
`http://localhost:8080/user-management/users`

## 4. Kiểm tra transaction
1. Chọn **Thêm User**, nhập thông tin.
2. Chọn một hoặc nhiều quyền.
3. Bấm **Lưu thông tin**.
4. Kiểm tra dữ liệu:
```sql
SELECT * FROM users ORDER BY id DESC LIMIT 1;
SELECT * FROM user_permission ORDER BY user_id DESC;
```

## 5. Kiểm tra rollback
Để mô phỏng lỗi SQL, có thể tạm thay `insertPermissionSql` trong `UserDAO.java` thành câu SQL sai, ví dụ `INSERT INTO user_permission_WRONG (user_id,permission_id) VALUES (?,?)`. Thử thêm user có chọn quyền: DAO sẽ gọi `rollback()` và ném lỗi lên Servlet. Kiểm tra bảng `users`, bản ghi user vừa tạo không được giữ lại. Sau khi kiểm tra, khôi phục câu SQL đúng.

**Lưu ý:** Chỉ mô phỏng lỗi trong database thực hành; không chạy câu SQL cố tình sai trên dữ liệu quan trọng.
