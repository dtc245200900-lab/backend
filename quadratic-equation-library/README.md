# Thư viện giải phương trình bậc hai

Dự án Maven tạo thư viện Java `com.codegym:quadratic-equation-library:1.0-SNAPSHOT`.

## Đóng gói

Cần cài Java 17+ và Maven. Từ thư mục dự án, chạy:

```bash
mvn clean package
```

JAR được tạo tại `target/quadratic-equation-library-1.0-SNAPSHOT.jar`.

## Sử dụng

```java
import com.codegym.math.QuadraticEquationSolver;

double[] roots = QuadraticEquationSolver.solve(1, -3, 2);
for (double root : roots) {
    System.out.println(root);
}
```

Phương thức trả về mảng rỗng khi phương trình không có nghiệm thực, một phần tử khi có một nghiệm thực, và hai phần tử khi có hai nghiệm thực. Nếu `a = 0`, phương thức xử lý trường hợp phương trình bậc nhất; trường hợp `a = b = c = 0` ném `IllegalArgumentException` vì có vô số nghiệm.
