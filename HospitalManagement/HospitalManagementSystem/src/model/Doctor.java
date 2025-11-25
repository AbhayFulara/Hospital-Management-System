package model;

public class Doctor extends Person {
    private String speciality;
    public Doctor() {}
    public Doctor(int id, String name, String speciality) {
        super(id, name);
        this.speciality = speciality;
    }
    public Doctor(String name, String speciality) {
        this(0, name, speciality);
    }
    public String getSpeciality() { 
        return speciality; 
    }
    public void setSpeciality(String speciality) {
        this.speciality = speciality; 
    }
    @Override
    public String toString() {
        return String.format("[%d] Dr. %s (%s)", id, name, speciality);
    }
}
