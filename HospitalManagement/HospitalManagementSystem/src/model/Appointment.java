package model;
import java.time.LocalDateTime;
public class Appointment {
    private int id;
    private int patientId;
    private int doctorId;
    private int bedId;
    private LocalDateTime appointmentTime;
    public Appointment() {}
    public Appointment(int id, int patientId, int doctorId, int bedId, LocalDateTime appointmentTime) {
        this.id = id; 
        this.patientId = patientId; 
        this.doctorId = doctorId; 
        this.bedId = bedId; 
        this.appointmentTime = appointmentTime;
    }
    public Appointment(int patientId, int doctorId, int bedId, LocalDateTime appointmentTime) {
        this(0, patientId, doctorId, bedId, appointmentTime);
    }
    public int getId() {
         return id; 
    }
    public int getPatientId() {
         return patientId; 
    }
    public int getDoctorId() {
         return doctorId; 
    }
    public int getBedId() { 
         return bedId; 
    }
    public LocalDateTime getAppointmentTime() { 
         return appointmentTime; 
    }
}
