CREATE DATABASE hospital_db;
USE hospital_db;

CREATE TABLE doctors (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  speciality VARCHAR(100)
);

CREATE TABLE patients (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  age INT,
  gender VARCHAR(10)
);

CREATE TABLE beds (
  id INT AUTO_INCREMENT PRIMARY KEY,
  bed_number VARCHAR(20) UNIQUE,
  occupied BOOLEAN DEFAULT FALSE
);

CREATE TABLE appointments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  patient_id INT,
  doctor_id INT,
  appointment_time DATETIME,
  bed_id INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO beds (bed_number, occupied) VALUES
('B-101', FALSE), ('B-102', FALSE), ('B-103', FALSE);


-- CREATE DATABASE IF NOT EXISTS hospital_db;
-- USE hospital_db;

-- CREATE TABLE IF NOT EXISTS doctors (
--   id INT AUTO_INCREMENT PRIMARY KEY,
--   name VARCHAR(100),
--   speciality VARCHAR(100)
-- );

-- CREATE TABLE IF NOT EXISTS patients (
--   id INT AUTO_INCREMENT PRIMARY KEY,
--   name VARCHAR(100),
--   age INT,
--   gender VARCHAR(10)
-- );

-- CREATE TABLE IF NOT EXISTS beds (
--   id INT AUTO_INCREMENT PRIMARY KEY,
--   bed_number VARCHAR(20) UNIQUE,
--   occupied BOOLEAN DEFAULT FALSE
-- );

-- CREATE TABLE IF NOT EXISTS appointments (
--   id INT AUTO_INCREMENT PRIMARY KEY,
--   patient_id INT,
--   doctor_id INT,
--   appointment_time DATETIME,
--   bed_id INT,
--   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--   FOREIGN KEY (patient_id) REFERENCES patients(id),
--   FOREIGN KEY (doctor_id) REFERENCES doctors(id),
--   FOREIGN KEY (bed_id) REFERENCES beds(id)
-- );

-- INSERT INTO beds (bed_number, occupied) VALUES
-- ('B-101', FALSE), ('B-102', FALSE), ('B-103', FALSE);
