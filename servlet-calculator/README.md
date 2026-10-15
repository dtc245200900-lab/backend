# Servlet Calculator

Ứng dụng Java Web sử dụng Servlet và JSP để thực hiện cộng, trừ, nhân, chia.

## Yêu cầu
- JDK 17
- Maven
- Apache Tomcat 9.x

## Chạy
1. Mở terminal tại thư mục có `pom.xml`.
2. Chạy `mvn clean package`.
3. Sao chép `target/servlet-calculator.war` vào thư mục `webapps` của Tomcat 9.
4. Khởi động Tomcat và mở `http://localhost:8080/servlet-calculator/`.

## Cấu trúc
- `index.jsp`: form nhập hai số và chọn phép toán.
- `Calculator.java`: thực hiện phép tính, báo lỗi khi chia cho 0.
- `CalculatorServlet.java`: nhận dữ liệu form, gọi lớp Calculator và chuyển tiếp sang trang kết quả.
- `result.jsp`: hiển thị kết quả hoặc thông báo lỗi.
