CREATE TABLE employees (
  id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  salary DECIMAL(13,2) NOT NULL,
)
  ENGINE=InnoDB;


CREATE TABLE roles (
  id   BIGINT(20)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
)
  ENGINE = InnoDB;


CREATE TABLE users (
  id       BIGINT(20)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role_id  BIGINT(20)   NOT NULL,
  FOREIGN KEY (role_id) REFERENCES roles (id),
  UNIQUE (role_id)
)
  ENGINE = InnoDB;


INSERT INTO roles (name) VALUES
    ('Admin'),
    ('Moderator'),
    ('Guest');
INSERT INTO users VALUES (1, 'test', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG', 1);
