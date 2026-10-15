CREATE DATABASE IF NOT EXISTS inotes_db
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE inotes_db;

CREATE TABLE IF NOT EXISTS note_type (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS notes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    type_id INT NOT NULL,
    CONSTRAINT fk_notes_note_type FOREIGN KEY (type_id)
        REFERENCES note_type(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

INSERT INTO note_type (name, description)
SELECT 'Cá nhân', 'Ghi chú cá nhân'
WHERE NOT EXISTS (SELECT 1 FROM note_type WHERE name = 'Cá nhân');

INSERT INTO note_type (name, description)
SELECT 'Công việc', 'Ghi chú liên quan đến công việc'
WHERE NOT EXISTS (SELECT 1 FROM note_type WHERE name = 'Công việc');

INSERT INTO note_type (name, description)
SELECT 'Học tập', 'Ghi chú học tập'
WHERE NOT EXISTS (SELECT 1 FROM note_type WHERE name = 'Học tập');

INSERT INTO notes (title, content, type_id)
SELECT 'Họp giao ban đầu tuần', 'Chuẩn bị nội dung họp và cập nhật tiến độ.', id
FROM note_type WHERE name = 'Công việc'
AND NOT EXISTS (SELECT 1 FROM notes WHERE title = 'Họp giao ban đầu tuần');

INSERT INTO notes (title, content, type_id)
SELECT 'Ôn tập Design Pattern Servlet', 'Ôn lại Strategy Pattern và Servlet.', id
FROM note_type WHERE name = 'Học tập'
AND NOT EXISTS (SELECT 1 FROM notes WHERE title = 'Ôn tập Design Pattern Servlet');
