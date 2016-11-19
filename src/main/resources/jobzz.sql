CREATE DATABASE jobzz;

-- Create Tables

CREATE TABLE jobzz.employer (
  employer_id     INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email           VARCHAR(255) NOT NULL UNIQUE,
  password        VARCHAR(255) NOT NULL,
  phone_number    VARCHAR(10)  NOT NULL,
  date_of_birth   DATE         NOT NULL,
  first_name      VARCHAR(255) NOT NULL,
  last_name       VARCHAR(255) NOT NULL,
  card_number     VARCHAR(16)  NOT NULL,
  expiration_date DATE         NOT NULL,
  cvv             VARCHAR(3)   NOT NULL,
  reputation      INT          NOT NULL,
  picture         BLOB
);

CREATE TABLE jobzz.review_employer (
  review_id   INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
  employer_id INT  NOT NULL,
  employee_id INT  NOT NULL,
  point       INT  NOT NULL,
  date        DATE NOT NULL,
  comment     VARCHAR(255),
  FOREIGN KEY (employer_id) REFERENCES employer (employer_id)
);

CREATE TABLE jobzz.employer_posting (
  employer_posting_id INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  employer_id         INT          NOT NULL,
  name                VARCHAR(255) NOT NULL,
  description         VARCHAR(255) NOT NULL,
  longitude           FLOAT        NOT NULL,
  latitude            FLOAT        NOT NULL,
  job_id              INT          NOT NULL,
  start_date          DATE         NOT NULL,
  end_date            DATE         NOT NULL,
  status              INT          NOT NULL,
  FOREIGN KEY (employer_id) REFERENCES employer (employer_id)
);

CREATE TABLE jobzz.job (
  job_id      INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(255) NOT NULL
);

CREATE TABLE jobzz.employee (
  employee_id     INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email           VARCHAR(255) NOT NULL UNIQUE,
  password        VARCHAR(255) NOT NULL,
  phone_number    VARCHAR(10)  NOT NULL,
  date_of_birth   DATE         NOT NULL,
  first_name      VARCHAR(255) NOT NULL,
  last_name       VARCHAR(255) NOT NULL,
  card_number     VARCHAR(16)  NOT NULL,
  expiration_date DATE         NOT NULL,
  cvv             VARCHAR(3)   NOT NULL,
  reputation      INT          NOT NULL,
  job_id          INT          NOT NULL,
  picture         BLOB,
  FOREIGN KEY (job_id) REFERENCES job (job_id)
);

CREATE TABLE jobzz.review_employee (
  review_id   INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
  employee_id INT  NOT NULL,
  employer_id INT  NOT NULL,
  point       INT  NOT NULL,
  date        DATE NOT NULL,
  commnet     VARCHAR(255),
  FOREIGN KEY (employee_id) REFERENCES employee (employee_id)
);

CREATE TABLE jobzz.employee_posting (
  employee_posting_id INT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
  employee_id         INT        NOT NULL,
  employer_posting_id INT        NOT NULL,
  price               INT        NOT NULL,
  currency            VARCHAR(3) NOT NULL,
  date                DATE       NOT NULL,
  commnet             VARCHAR(255),
  status              INT        NOT NULL,
  FOREIGN KEY (employee_id) REFERENCES employee (employee_id),
  FOREIGN KEY (employer_posting_id) REFERENCES employer_posting (employer_posting_id)
);

-- INSERT JOBS

INSERT INTO jobzz.job VALUES (1, 'Zugrav');
INSERT INTO jobzz.job VALUES (2, 'Instalator');
INSERT INTO jobzz.job VALUES (3, 'Electrician');
INSERT INTO jobzz.job VALUES (4, 'Constructor');
INSERT INTO jobzz.job VALUES (5, 'Amenajari Interioare');