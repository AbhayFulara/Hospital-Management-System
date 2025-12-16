package model;

public class Bed {
    private int bedId;
    private String ward;
    private String bedNumber;
    private String status;
    private Integer patientId; // null if free

    public Bed() {}

    public Bed(int bedId, String ward, String bedNumber,
               String status, Integer patientId) {
        this.bedId = bedId;
        this.ward = ward;
        this.bedNumber = bedNumber;
        this.status = status;
        this.patientId = patientId;
    }

    public int getBedId() { return bedId; }
    public void setBedId(int bedId) { this.bedId = bedId; }

    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }

    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getPatientId() { return patientId; }
    public void setPatientId(Integer patientId) { this.patientId = patientId; }

    @Override
    public String toString() {
        return ward + "-" + bedNumber + " [" + status + "]";
    }
}
