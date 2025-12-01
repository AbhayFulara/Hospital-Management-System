package service;

import dao.DoctorDAO;
import model.Doctor;

import java.util.List;

public class DoctorService {

    private final DoctorDAO doctorDAO = new DoctorDAO();

    public List<Doctor> findAll() {
        return doctorDAO.getAllDoctors();
    }

    public void updateNotes(int doctorId, String notes) {
        doctorDAO.updateDoctorNotes(doctorId, notes);
    }
}
