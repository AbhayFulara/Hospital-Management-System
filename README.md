# Hospital-Management-System
Hospital-Management-System-Java
Project Overview

The Hospital Management System is a Java-based application that uses Swing GUI, OOP principles, JDBC, and MySQL to manage essential hospital operations.
It allows easy handling of patients, doctors, appointments, and bed allocation, with a structured architecture and database-driven storage.

This project is designed to demonstrate Object-Oriented Programming, GUI development, and database integration, making it suitable for academic and practical learning.

Project Structure:-

HospitalManagementSystem/

model/                 # Contains all data classes (Patient, Doctor, Bed, Appointment, Person)

dao/                   # Contains DAO classes that perform CRUD operations using JDBC

util/                  # Database connection utility + multithreading helpers

ui/                    # Swing GUI panels (PatientPanel, DoctorPanel, AppointmentPanel, etc.)

Main.java              # Launches the full GUI application

Features:-

-Add & manage patients

-Add & manage doctors

-Schedule appointments

-Allocate beds and track bed availability

Technologies Used:-

-Java (JDK 17+)

-Object-Oriented Programming (OOP)

-Swing GUI

-MySQL Database

-JDBC (Java Database Connectivity)

-VS Code IDE (with Java extensions)

GUI:-
<img width="2056" height="216" alt="image" src="https://github.com/user-attachments/assets/071a9daa-036a-48a9-8637-6434e99d3215" />

How to Run the Project:-

1. Install Requirements

Install JDK 17+

Install MySQL Server

Install Connector J

Install VS Code + Java extensions

2. Use Pass:-

String url = "jdbc:mysql://localhost:3306/hospital_db";
String user = "root";
String pass = "abhay";


3. Compile & Run

Open folder in VS Code

Run Main.java

The Swing GUI will launch

