# Thực hành Thực thi SQL có sử dụng Transaction

Project minh họa JDBC Transaction với hai lệnh INSERT và một lệnh UPDATE. Mặc định bài test cố tình gán sai tham số UPDATE để kiểm tra `rollback()`. Sau khi sửa index tham số, chạy lại để quan sát `commit()`.

## Yêu cầu
- JDK 17, Maven, MySQL 8
- Tomcat 10.1 trở lên

## 1. Database
Chạy `database.sql` trong MySQL Workbench. Database sử dụng là `demo`.

## 2. Cấu hình MySQL
Trong `src/main/java/com/codegym/dao/UserDAO.java`, sửa:
```java
private static final String JDBC_USERNAME = "root";
private static final String JDBC_PASSWORD = "password";
```

## 3. Build và deploy
Ở thư mục gốc:
```bash
mvn clean package
```
Copy `target/user-management.war` vào thư mục `webapps` của Tomcat 10.1+ rồi khởi động Tomcat.

Truy cập:
`http://localhost:8080/user-management/users?action=test-use-tran`

## 4. Lần 1: Kiểm tra rollback
Mã mặc định cố tình sai ở dòng:
```java
psUpdate.setBigDecimal(2, new BigDecimal("999.99"));
```
Do tham số 1 chưa được gán, câu UPDATE ném SQLException. DAO gọi `conn.rollback()`; hai INSERT chưa commit bị hủy.

Kiểm tra:
```sql
USE demo;
SELECT * FROM Employee;
```
Bảng không có các bản ghi được thêm trong transaction vừa chạy.

## 5. Lần 2: Kiểm tra commit
Sửa dòng lỗi thành:
```java
psUpdate.setBigDecimal(1, new BigDecimal("999.99"));
```
Chạy lại `mvn clean package`, redeploy WAR và truy cập URL test. Kết quả kỳ vọng: có Quynh và Ngan; salary của Quynh là 999.99.

## Cảnh báo
Phương thức test DROP rồi tạo lại bảng Employee trước mỗi lần chạy. Chỉ sử dụng trên database thực hành, không dùng với dữ liệu quan trọng.
