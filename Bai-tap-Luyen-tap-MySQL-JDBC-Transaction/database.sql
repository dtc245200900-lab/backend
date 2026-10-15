CREATE DATABASE IF NOT EXISTS demo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE demo;

CREATE TABLE IF NOT EXISTS users (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(120) NOT NULL,
  email VARCHAR(120) NOT NULL,
  country VARCHAR(80) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS permission (
  id INT NOT NULL PRIMARY KEY,
  name VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user_permission (
  user_id INT NOT NULL,
  permission_id INT NOT NULL,
  PRIMARY KEY(user_id, permission_id),
  CONSTRAINT fk_up_user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_up_permission FOREIGN KEY(permission_id) REFERENCES permission(id)
) ENGINE=InnoDB;

INSERT INTO permission(id, name) VALUES
(1, 'add'), (2, 'edit'), (3, 'delete'), (4, 'view')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO users(name, email, country)
SELECT 'Nguyen Van A', 'a@example.com', 'VN'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email='a@example.com');
