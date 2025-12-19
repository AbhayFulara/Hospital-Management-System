# Hospital-Management-System

Hospital Management System (Java Swing)

A Java-based Hospital Management System developed using Java Swing, JDBC, and MySQL.
This desktop application provides a centralized platform to manage patients, doctors, beds, and appointments efficiently through an interactive GUI dashboard.

This project is created as a college academic project to demonstrate Object-Oriented Programming (OOP), GUI development, and database connectivity in Java.


---

Key Features

1.Dashboard

Modern dashboard shown on application startup

Icon-based navigation for:

Patients

Doctors

Beds

Appointments


Single-window navigation using CardLayout


2. Patient Management

Add new patients

View patient records

Persistent storage using MySQL database


3. Doctor Management

Add, update, and delete doctors

View doctor list

Maintain doctor notes

Search doctors by name


4. Bed Management

View bed availability

Allocate beds to patients

Track occupied and available beds


5. Appointment Management

Schedule appointments

Link patients with doctors

Store appointment details in database



---

Technologies Used

Java (JDK 17+)

Java Swing (GUI)

JDBC (Java Database Connectivity)

MySQL Database

VS Code with Java Extensions





---

Project Structure

hospital-management-system-java/
│
├── src/
│   ├── dao/              # DAO classes (database operations)
│   ├── model/            # Entity classes (Patient, Doctor, Bed, Appointment)
│   ├── service/          # Business logic layer
│   ├── ui/               # Swing UI panels & MainFrame
│   ├── util/             # DB connection and utilities
│   └── resources/
│       └── icons/        # Dashboard icons (JPEG)
│
├── lib/                  # JDBC connector (if external)
├── bin/                  # Compiled class files
├── README.md


---

Database Setup

1️⃣ Create Database and Tables

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

Configuration

Update your database credentials in:

src/util/DBConnection.java

Example:

String url = "jdbc:mysql://localhost:3306/hospital_db";
String user = "root";
String password = "your_mysql_password";


---

How to Run the Project

1. Install JDK 17 or higher


2. Install MySQL Server


3. Create the database using the SQL above


4. Open the project in VS Code


5. Make sure Java extensions are installed


6. Run:



src/util/MainApp.java

The Dashboard will open first.


---

Learning Outcomes

Java Swing GUI development

MVC design pattern

JDBC & MySQL integration

Object-Oriented Programming concepts

Event handling and layout management



---

Future Enhancements

Login & authentication system

Role-based access control

Report generation

Improved UI animations

Cloud database integration



---


