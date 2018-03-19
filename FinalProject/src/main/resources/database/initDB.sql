CREATE DATABASE findev;
USE findev;


#Security:
CREATE TABLE roles (
  id   BIGINT(20)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  UNIQUE (name));
CREATE TABLE users (
  id       BIGINT(20)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role_id  BIGINT(20)   NOT NULL,
  FOREIGN KEY (role_id) REFERENCES roles (id),
  UNIQUE (email));


#Entities:
CREATE TABLE positions (
  id   BIGINT(20)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  UNIQUE (name));
CREATE TABLE departments (
  id   BIGINT(20)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  UNIQUE (name));
CREATE TABLE statuses (
  id   BIGINT(20)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  UNIQUE (name));
CREATE TABLE employees (
  id            BIGINT(20)     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  firstname    VARCHAR(255)   NOT NULL,
  lastname     VARCHAR(255)   NOT NULL,
  email         VARCHAR(255)   NOT NULL,
  hourrate     DECIMAL(13, 2) NOT NULL,
  position_id   BIGINT(20)     NOT NULL,
  department_id BIGINT(20)     NOT NULL,
  status_id     BIGINT(20)     NOT NULL,
  FOREIGN KEY (position_id) REFERENCES positions (id),
  FOREIGN KEY (department_id) REFERENCES departments (id),
  FOREIGN KEY (status_id) REFERENCES statuses (id),
  UNIQUE (email));


#Events:
CREATE TABLE eventtypes (
  id    BIGINT(20)     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name  VARCHAR(255)   NOT NULL,
  coeft DECIMAL(13, 2) NOT NULL,
  UNIQUE (name));
CREATE TABLE events (
  id           BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  date         DATE       NOT NULL,
  hours        BIGINT(20) NOT NULL,
  eventtype_id BIGINT(20) NOT NULL,
  FOREIGN KEY (eventtype_id) REFERENCES eventtypes (id));
CREATE TABLE events_employees (
  event_id    BIGINT(20) NOT NULL,
  employee_id BIGINT(20) NOT NULL,
  FOREIGN KEY (event_id) REFERENCES events (id),
  FOREIGN KEY (employee_id) REFERENCES employees (id),
  UNIQUE (event_id, employee_id));



/*
CREATE TABLE employees_absence (
  id          BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  date        DATE       NOT NULL,
  employee_id BIGINT(20) NOT NULL,
  status_id   BIGINT(20) NOT NULL,
  FOREIGN KEY (employee_id) REFERENCES employees (id),
  FOREIGN KEY (status_id) REFERENCES statuses (id));
*/



