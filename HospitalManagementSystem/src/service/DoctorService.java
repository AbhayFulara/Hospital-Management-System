package service;

import dao.DoctorDAO;
import model.Doctor;

import java.util.List;

public class DoctorService {

    private final DoctorDAO doctorDAO = new DoctorDAO();

    public void saveDoctor(Doctor doctor) {
        doctorDAO.insertDoctor(doctor);
    }

    public void updateDoctor(Doctor doctor) {
        doctorDAO.updateDoctor(doctor);
    }

    public void deleteDoctor(int doctorId) {
        doctorDAO.deleteDoctor(doctorId);
    }

    public List<Doctor> findAll() {
        return doctorDAO.getAllDoctors();
    }
}