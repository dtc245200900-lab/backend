# Thực hành đóng gói thư viện Java

## Đóng gói thư viện
Từ thư mục `math-lib`, chạy:

```bash
mvn clean package
```

JAR được tạo tại `math-lib/target/math-lib-1.0-SNAPSHOT.jar`. Bản sao sử dụng trong ứng dụng đã được đặt tại `calculator-app/lib/math-lib.jar`.

## Build ứng dụng
Từ thư mục `calculator-app`, chạy:

```bash
mvn clean package
```

## Chạy ứng dụng
Từ thư mục `calculator-app`, chạy:

```bash
java -cp "target/classes;lib/math-lib.jar" com.codegym.Main
```

Trên macOS/Linux, thay dấu chấm phẩy trong classpath bằng dấu hai chấm.
