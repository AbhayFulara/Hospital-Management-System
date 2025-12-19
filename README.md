# Hospital-Management-System



A Java-based Hospital Management System developed using Java Swing, JDBC, and MySQL.
This desktop application provides a centralized platform to manage patients, doctors, beds, and appointments efficiently through an interactive GUI dashboard.

This project is created as a college academic project to demonstrate Object-Oriented Programming (OOP), GUI development, and database connectivity in Java.


---

# Key Features

# 1.Dashboard:-

Modern dashboard shown on application startup

Icon-based navigation for:

Patients

Doctors

Beds

Appointments


Single-window navigation using CardLayout

![dash](https://github.com/user-attachments/assets/5af4c664-84e9-43c6-8e87-b0deedb2a02a)



# 2.Patient Management:-

Add new patients

View patient records

Persistent storage using MySQL database

![patient](https://github.com/user-attachments/assets/dc64760f-7721-44cf-873c-ba95f59cf1cc)



# 3.Doctor Management:-

Add, update, and delete doctors

View doctor list

Maintain doctor notes

Search doctors by name

![doc](https://github.com/user-attachments/assets/ec94c20c-873b-4932-baeb-c0fcf16d989f)



# 4.Bed Management:-

View bed availability

Allocate beds to patients

Track occupied and available beds

![bed](https://github.com/user-attachments/assets/bf675429-f562-45a6-a86a-7e03c51bb20e)



# 5.Appointment Management:-

Schedule appointments

Link patients with doctors

Store appointment details in database

![appo](https://github.com/user-attachments/assets/ce1e8349-0452-4bb6-8658-f60aafaaf801)




---

# Technologies Used:-

Java (JDK 17+)

Java Swing (GUI)

JDBC (Java Database Connectivity)

MySQL Database

VS Code with Java Extensions





---

# Project Structure:-

hospital-management-system-java/

src/

dao/             

DAO classes (database operations)

model/            

Entity classes (Patient, Doctor, Bed, Appointment)

service/  

Business logic layer

ui/               


Swing UI panels & MainFrame

util/             

DB connection and utilities

resources/

icons/        

Dashboard icons (JPEG)

lib/                  

JDBC connector (if external)

bin/                  

Compiled class files

README.md

<img width="422" height="879" alt="Screenshot 2025-12-17 164957" src="https://github.com/user-attachments/assets/eec5a6ab-167f-4ca2-9cc4-b5247e795d90" />



---

# Database Setup

Create Database and Tables

Run the following SQL commands in MySQL:

CREATE DATABASE IF NOT EXISTS hospital_db;

USE hospital_db;

CREATE TABLE patients (

  patient_id INT AUTO_INCREMENT PRIMARY KEY,
  
  name VARCHAR(100),
  
  age INT,
  
  gender VARCHAR(10),
  
  phone VARCHAR(15),
  
  address VARCHAR(255)
  
);

CREATE TABLE doctors (

  doctor_id INT AUTO_INCREMENT PRIMARY KEY,
  
  name VARCHAR(100),
  
  specialization VARCHAR(100),
  
  notes TEXT
  
);

CREATE TABLE beds (

  bed_id INT AUTO_INCREMENT PRIMARY KEY,
  
  ward VARCHAR(50),
  
  bed_number VARCHAR(10),
  
  status VARCHAR(20),
  
  patient_id INT,
  
  FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
  
);

CREATE TABLE appointments (

  appointment_id INT AUTO_INCREMENT PRIMARY KEY,
  
  patient_id INT,
  
  doctor_id INT,
  
  appointment_date DATE,
  
  time_slot VARCHAR(10),
  
  status VARCHAR(20),
  
  FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
  
  FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)
  
);


---

# Configuration

Update your database credentials in:

src/util/DBConnection.java

Example:

String url = "jdbc:mysql://localhost:3306/hospital_db";
String user = "root";
String password = "your_mysql_password";


---

# How to Run the Project

1. Install JDK 17 or higher


2. Install MySQL Server


3. Create the database using the SQL above


4. Open the project in VS Code


5. Make sure Java extensions are installed


6. Run:



src/util/MainApp.java

The Dashboard will open first.


---

# Learning Outcomes

Java Swing GUI development

MVC design pattern

JDBC & MySQL integration

Object-Oriented Programming concepts

Event handling and layout management



---

# Future Enhancements

Login & authentication system

Role-based access control

Report generation

Improved UI animations

Cloud database integration



---


