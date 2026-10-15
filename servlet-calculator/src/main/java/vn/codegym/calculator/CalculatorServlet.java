package vn.codegym.calculator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/calculate")
public class CalculatorServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            double firstOperand = Double.parseDouble(request.getParameter("firstOperand"));
            double secondOperand = Double.parseDouble(request.getParameter("secondOperand"));
            String operator = request.getParameter("operator");

            Calculator calculator = new Calculator();
            double result = calculator.calculate(firstOperand, secondOperand, operator);

            request.setAttribute("firstOperand", firstOperand);
            request.setAttribute("secondOperand", secondOperand);
            request.setAttribute("operator", operator);
            request.setAttribute("result", result);
        } catch (ArithmeticException | IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Vui lòng nhập hai số hợp lệ.");
        }

        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }
}
