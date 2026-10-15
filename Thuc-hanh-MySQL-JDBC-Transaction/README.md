# Thực hành MySQL JDBC Transaction

Ứng dụng Java Web quản lý User, thêm User và phân quyền trong cùng một JDBC transaction. Nếu thao tác ghi vào `users` hoặc `user_permission` thất bại, chương trình gọi `rollback()` để hủy toàn bộ giao dịch.

## Công nghệ
- Java 17, Maven WAR
- Jakarta Servlet 6 / Tomcat 10.1+
- JSP + JSTL
- MySQL 8 và MySQL Connector/J

## 1. Tạo database
Mở MySQL Workbench hoặc DBeaver và chạy toàn bộ file `database.sql`.

## 2. Cấu hình kết nối
Mở `src/main/java/com/codegym/dao/UserDAO.java`, chỉnh các giá trị sau theo máy của bạn:
```java
private static final String JDBC_USERNAME = "root";
private static final String JDBC_PASSWORD = "password";
```

## 3. Build
Tại thư mục gốc dự án, chạy:
```bash
mvn clean package
```
File WAR sẽ nằm ở `target/user-management.war`.

## 4. Deploy Tomcat
Dùng Tomcat 10.1 trở lên. Copy `target/user-management.war` vào thư mục `webapps`, khởi động Tomcat rồi truy cập:
`http://localhost:8080/user-management/users`

## 5. Kiểm tra Transaction
1. Chọn **Thêm User**.
2. Nhập tên, email, quốc gia.
3. Chọn quyền **Thêm (1)** và **Xem (4)**.
4. Lưu thông tin.
5. Kiểm tra:
```sql
SELECT * FROM users ORDER BY id DESC LIMIT 1;
SELECT * FROM user_permission ORDER BY user_id DESC;
```
User và các quyền được thêm trên cùng một connection, sau `commit()` mới được lưu chính thức. Nếu một quyền không hợp lệ hoặc có lỗi SQL, transaction được rollback.

## Lưu ý
- Không dùng Tomcat 9 vì dự án dùng `jakarta.servlet` (Tomcat 10.1+).
- Đảm bảo MySQL đang chạy và database credentials đúng.
- Trước khi nộp, hãy chạy thử `mvn clean package` và kiểm tra lại kết quả trên máy.
