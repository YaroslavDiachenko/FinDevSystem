

#Security:
INSERT INTO roles (name) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_MODER'),
    ('ROLE_USER');
INSERT INTO users (email, password, role_id) VALUES
    ('admin', 'pass',  1),
    ('moder', 'pass',  2),
    ('user',  'pass',  3);


#Entities:
INSERT INTO positions (name) VALUES
    ('Physicist'),
    ('Astrophysicist'),
    ('Aerospace engineer');
INSERT INTO departments (name) VALUES
    ('Physics'),
    ('Earth and Planetary Sciences'),
    ('Systems and Control Engineering');
INSERT INTO statuses (name) VALUES
    ('Free'),
    ('Busy'),
    ('On sick leave'),
    ('On vacation');
INSERT INTO employees (firstname,lastname,email,hourrate,position_id,department_id,status_id) VALUES
    ('Leonard', 'Hofstadter',   'hofstadter@mail',    10, 1, 1, 1),
    ('Sheldon', 'Cooper',       'cooper@mail',        15, 1, 1, 2),
    ('Howard',  'Wolowitz',     'wolowitz@mail',      20, 3, 3, 4),
    ('Raj',     'Koothrappali', 'koothrappali@mail',  15, 2, 2, 2);


#Events:
INSERT INTO eventtypes (name,coeft) VALUES
    ('Working day',     1),
    ('Technical study', 0.8),
    ('Presentation',    0.6);
INSERT INTO events (date,hours,eventtype_id) VALUES
    ('2018-01-02', 8, 1),
    ('2018-01-04', 6, 1),
    ('2018-01-06', 8, 2),
    ('2018-01-08', 7, 1),
    ('2018-01-10', 5, 3),
    ('2018-01-10', 5, 1),
    ('2018-01-10', 5, 1);
INSERT INTO events_employees VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (2, 4),
    (3, 1),
    (3, 3),
    (4, 2),
    (4, 3),
    (5, 1),
    (5, 2),
    (6, 3),
    (6, 4),
    (7, 1),
    (7, 4);