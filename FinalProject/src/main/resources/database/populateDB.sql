INSERT INTO roles (name)
VALUES
    ('ROLE_ADMIN'),
    ('ROLE_MODER'),
    ('ROLE_USER');

INSERT INTO users (username, password, role_id)
VALUES
    ('User1', 'pass1', 1),
    ('User2', 'pass2', 2),
    ('User3', 'pass3', 3);

INSERT INTO employees (first_name,last_name,salary) VALUES
    ('Bob', 'Brown', 100),
    ('Nick', 'Nickson', 200),
    ('Jack', 'Jackson', 300);