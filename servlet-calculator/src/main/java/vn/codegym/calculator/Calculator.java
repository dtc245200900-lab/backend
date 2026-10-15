package vn.codegym.calculator;

public class Calculator {
    public double calculate(double firstOperand, double secondOperand, String operator) {
        switch (operator) {
            case "+":
                return firstOperand + secondOperand;
            case "-":
                return firstOperand - secondOperand;
            case "*":
                return firstOperand * secondOperand;
            case "/":
                if (secondOperand == 0) {
                    throw new ArithmeticException("Không thể chia cho 0.");
                }
                return firstOperand / secondOperand;
            default:
                throw new IllegalArgumentException("Phép toán không hợp lệ.");
        }
    }
}
