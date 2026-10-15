# Case Study: Ứng dụng iNotes

Ứng dụng quản lý ghi chú Java Servlet/JSP, Maven, MySQL JDBC và Strategy Pattern.

## Chức năng
- Thêm, xem, sửa, xóa ghi chú.
- Phân loại theo Cá nhân, Công việc, Học tập.
- Tìm kiếm trong tiêu đề và nội dung.
- Chuyển đổi chiến lược lưu trữ tại runtime: File TXT hoặc MySQL.
- Dữ liệu file được lưu ở `data/notes.txt` và `data/note-types.txt` tính từ thư mục chạy Tomcat.

## Yêu cầu
- JDK 17+
- Maven 3.8+
- Tomcat 10.1+
- MySQL 8+

## Cài đặt database
Chạy `database.sql` trong MySQL Workbench hoặc mysql CLI.

Nếu tài khoản MySQL không phải `root` không mật khẩu, thiết lập biến môi trường trước khi khởi động Tomcat:
- Windows CMD: `set INOTES_DB_USER=root` và `set INOTES_DB_PASSWORD=mat_khau`
- Linux/macOS: `export INOTES_DB_USER=root` và `export INOTES_DB_PASSWORD=mat_khau`
Có thể tùy chỉnh URL bằng `INOTES_DB_URL`.

## Build và chạy
```bash
mvn clean package
```
Copy `target/inotes.war` vào `TOMCAT_HOME/webapps/`, khởi động Tomcat và mở:
`http://localhost:8080/inotes/notes`

Mặc định ứng dụng dùng File TXT. Chọn nút `Dùng MySQL` trên giao diện để chuyển qua DB, hoặc `Dùng File` để chuyển về file. Hai nơi lưu trữ là độc lập; dữ liệu không tự đồng bộ khi chuyển strategy.

## Cấu trúc
- `Note`: model
- `NoteStrategy`: hợp đồng lưu trữ
- `NoteDBStrategy`: JDBC/MySQL
- `NoteFileStrategy`: file text
- `NoteManagement`: nghiệp vụ, có `setStrategy(...)`
- `NoteServlet`: controller
- `index.jsp`, `note-form.jsp`: giao diện

## Lưu ý
Bài dùng `jakarta.servlet` nên chạy với Tomcat 10.1+; không dùng Tomcat 9 nếu chưa chuyển package sang `javax.servlet`.
