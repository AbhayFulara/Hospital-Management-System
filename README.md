# Hospital-Management-System
Hospital-Management-System-Java
Project Overview

The Hospital Management System is a Java-based application that uses Swing GUI, OOP principles, JDBC, and MySQL to manage essential hospital operations.
It allows easy handling of patients, doctors, appointments, and bed allocation, with a structured architecture and database-driven storage.

This project is designed to demonstrate Object-Oriented Programming, GUI development, and database integration, making it suitable for academic and practical learning.

Project Structure
HospitalManagementSystem/
│
├── model/                 # Contains all data classes (Patient, Doctor, Bed, Appointment, Person)
│
├── dao/                   # Contains DAO classes that perform CRUD operations using JDBC
│
├── util/                  # Database connection utility + multithreading helpers
│
├── ui/                    # Swing GUI panels (PatientPanel, DoctorPanel, AppointmentPanel, etc.)
│
└── Main.java              # Launches the full GUI application

