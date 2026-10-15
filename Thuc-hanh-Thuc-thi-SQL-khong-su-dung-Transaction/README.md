# Thực hành Thực thi SQL không sử dụng Transaction

Project minh họa JDBC auto-commit bằng cách tạo lại bảng `Employee`, chèn hai nhân viên rồi cố tình gây lỗi ở câu `UPDATE`. Không tắt auto-commit và không gọi rollback nên hai câu `INSERT` vẫn được lưu.

## Cần có
- JDK 17
- Maven
- MySQL 8
- Tomcat 10.1 trở lên

## 1. Cơ sở dữ liệu
Chạy `database.sql` trong MySQL Workbench. Ứng dụng dùng database `demo`.

## 2. Cấu hình tài khoản MySQL
Mở `src/main/java/com/codegym/dao/UserDAO.java`, sửa:
```java
private static final String JDBC_USERNAME = "root";
private static final String JDBC_PASSWORD = "password";
```

## 3. Đóng gói và chạy
Từ thư mục dự án:
```bash
mvn clean package
```
Copy `target/user-management.war` vào thư mục `webapps` của Tomcat 10.1+ rồi khởi động Tomcat.

Truy cập:
`http://localhost:8080/user-management/users`

Bấm **Chạy bài test không dùng Transaction** hoặc truy cập trực tiếp:
`http://localhost:8080/user-management/users?action=test-without-tran`

## 4. Kiểm tra kết quả
Chạy:
```sql
USE demo;
SELECT * FROM Employee;
```
Kỳ vọng có `Quynh` với salary `10.00` và `Ngan` với salary `20.00`. Câu UPDATE không chạy vì cố tình thiếu tham số thứ nhất.

## Cảnh báo
Mỗi lần chạy bài test, code thực hiện `DROP TABLE IF EXISTS Employee` rồi tạo lại bảng; mọi dữ liệu cũ trong bảng Employee sẽ bị xóa. Chỉ chạy trong database thực hành.

## Ghi chú
Lỗi `No value specified for parameter 1` được kỳ vọng khi chạy `executeUpdate()` với tham số `salary` chưa gán. Cụ thể driver có thể phát hiện lỗi tại `executeUpdate()`. `insertUpdateWithoutTransaction()` khai báo `throws SQLException`; controller bắt lỗi có chủ đích để hiển thị trang kết quả.
