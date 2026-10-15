CREATE DATABASE IF NOT EXISTS demo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE demo;

CREATE TABLE IF NOT EXISTS users (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(120) NOT NULL,
  email VARCHAR(120) NOT NULL,
  country VARCHAR(80) NOT NULL
) ENGINE=InnoDB;

DROP PROCEDURE IF EXISTS select_all_users;
DROP PROCEDURE IF EXISTS update_user;
DROP PROCEDURE IF EXISTS delete_user;

DELIMITER //
CREATE PROCEDURE select_all_users()
BEGIN
  SELECT id, name, email, country FROM users ORDER BY id DESC;
END //

CREATE PROCEDURE update_user(
  IN p_id INT,
  IN p_name VARCHAR(120),
  IN p_email VARCHAR(120),
  IN p_country VARCHAR(80)
)
BEGIN
  UPDATE users
  SET name = p_name, email = p_email, country = p_country
  WHERE id = p_id;
END //

CREATE PROCEDURE delete_user(IN p_id INT)
BEGIN
  DELETE FROM users WHERE id = p_id;
END //
DELIMITER ;

-- Thêm dữ liệu mẫu nếu bảng chưa có dữ liệu.
INSERT INTO users(name, email, country)
SELECT 'Nguyen Van A', 'a@example.com', 'VN'
WHERE NOT EXISTS (SELECT 1 FROM users);
