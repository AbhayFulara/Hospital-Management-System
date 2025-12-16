package model;

public class Doctor {
    private int doctorId;
    private String name;
    private String specialization;
    private String notes;

    public Doctor() {}

    public Doctor(int doctorId, String name, String specialization, String notes) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.notes = notes;
    }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return name + " (" + specialization + ")";
    }
}
