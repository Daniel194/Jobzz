CREATE DATABASE jobzz;

CREATE TABLE jobzz.employer(
  employer_id INT NOT NULL PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  phone_number VARCHAR(10) NOT NULL,
  date_of_birth DATE NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  card_number VARCHAR(16) NOT NULL,
  cvv VARCHAR(3) NOT NULL,
  reputation INT NOT NULL,
  picture BLOB
);