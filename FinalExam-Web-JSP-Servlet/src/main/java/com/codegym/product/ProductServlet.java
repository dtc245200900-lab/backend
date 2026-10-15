package com.codegym.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        try {
            if ("edit".equals(action) || "view".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Product product = productDAO.findById(id);
                if (product == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sản phẩm.");
                    return;
                }
                request.setAttribute("product", product);
                request.setAttribute("readonly", "view".equals(action));
                request.getRequestDispatcher("/product-form.jsp").forward(request, response);
                return;
            }
            request.setAttribute("products", productDAO.findAll());
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("products", java.util.List.of());
        }
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        try {
            if ("delete".equals(action)) {
                productDAO.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect(request.getContextPath() + "/products?message=Đã%20xóa%20sản%20phẩm");
                return;
            }
            String name = request.getParameter("name");
            String priceText = request.getParameter("price");
            String quantityText = request.getParameter("quantity");
            if (name == null || name.isBlank()) throw new IllegalArgumentException("Tên sản phẩm không được để trống.");
            BigDecimal price = new BigDecimal(priceText);
            int quantity = Integer.parseInt(quantityText);
            if (price.signum() < 0) throw new IllegalArgumentException("Giá không được âm.");
            if (quantity < 0) throw new IllegalArgumentException("Số lượng không được âm.");

            String idText = request.getParameter("id");
            Product p = new Product(idText == null || idText.isBlank() ? 0 : Integer.parseInt(idText),
                    name.trim(), price, quantity);
            if ("update".equals(action)) productDAO.update(p);
            else productDAO.insert(p);
            response.sendRedirect(request.getContextPath() + "/products?message=Đã%20lưu%20sản%20phẩm");
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("product", new Product(
                    parseId(request.getParameter("id")),
                    request.getParameter("name"),
                    parsePrice(request.getParameter("price")),
                    parseQuantity(request.getParameter("quantity"))));
            request.setAttribute("readonly", false);
            request.getRequestDispatcher("/product-form.jsp").forward(request, response);
        }
    }

    private int parseId(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return 0; }
    }
    private BigDecimal parsePrice(String s) {
        try { return new BigDecimal(s); } catch (Exception e) { return BigDecimal.ZERO; }
    }
    private int parseQuantity(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return 0; }
    }
}
