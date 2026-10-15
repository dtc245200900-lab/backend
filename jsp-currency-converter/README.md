# JSP Currency Converter

Bài thực hành chuyển đổi USD sang VND bằng JSP, Maven và Tomcat 10.1+.

## Cấu trúc
- `pom.xml`: cấu hình Maven.
- `src/main/webapp/index.jsp`: form nhập tỷ giá và lượng USD.
- `src/main/webapp/converter.jsp`: xử lý dữ liệu bằng JSP Scriptlet và hiển thị bằng Expression.
- `src/main/webapp/WEB-INF/web.xml`: cấu hình ứng dụng.

## Cách chạy
1. Cài Java 17, Maven và Tomcat 10.1+.
2. Mở terminal ở thư mục có `pom.xml`.
3. Chạy `mvn clean package`.
4. Sao chép `target/jsp-currency-converter.war` vào thư mục `webapps` của Tomcat.
5. Khởi động Tomcat, mở `http://localhost:8080/jsp-currency-converter/`.

Công thức: `VND = USD × Rate`.
