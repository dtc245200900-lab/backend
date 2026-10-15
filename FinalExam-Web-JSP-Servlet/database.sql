CREATE DATABASE IF NOT EXISTS product_management
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE product_management;

CREATE TABLE IF NOT EXISTS products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(12,2) NOT NULL DEFAULT 0,
    quantity INT NOT NULL DEFAULT 0,
    CONSTRAINT chk_products_price CHECK (price >= 0),
    CONSTRAINT chk_products_quantity CHECK (quantity >= 0)
);

INSERT INTO products (name, price, quantity)
SELECT 'Bàn phím cơ', 650000.00, 12
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Bàn phím cơ');

INSERT INTO products (name, price, quantity)
SELECT 'Chuột không dây', 250000.00, 25
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Chuột không dây');

INSERT INTO products (name, price, quantity)
SELECT 'Màn hình 24 inch', 2850000.00, 8
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Màn hình 24 inch');
