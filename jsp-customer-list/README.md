# JSP Customer List

Ứng dụng JSP hiển thị danh sách khách hàng mẫu bằng JSTL.

## Yêu cầu
- JDK 17
- Maven
- Apache Tomcat 9.x (dùng `javax.servlet` và JSTL 1.2)

## Chạy ứng dụng
1. Mở terminal tại thư mục có `pom.xml`.
2. Chạy:
   ```bash
   mvn clean package
   ```
3. Sau khi build thành công, file WAR nằm tại `target/jsp-customer-list.war`.
4. Sao chép WAR vào thư mục `webapps` của Tomcat 9, khởi động Tomcat.
5. Truy cập `http://localhost:8080/jsp-customer-list/`.

## Nội dung
- `index.jsp`: danh sách khách hàng được lưu trong biến JSTL `customers`.
- `<c:forEach>` lặp qua danh sách.
- `<c:out>` hiển thị dữ liệu an toàn hơn.
