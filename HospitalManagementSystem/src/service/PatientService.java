package service;

import dao.PatientDAO;
import model.Patient;

import java.util.List;

public class PatientService {

    private final PatientDAO patientDAO = new PatientDAO();

    public void save(Patient p) {
        if (p.getPatientId() == 0) {
            patientDAO.addPatient(p);
        } else {
            patientDAO.updatePatient(p);
        }
    }

    public void delete(int id) {
        patientDAO.deletePatient(id);
    }

    public List<Patient> findAll() {
        return patientDAO.getAllPatients();
    }

    public List<Patient> searchByName(String name) {
        return patientDAO.searchByName(name);
    }
}

