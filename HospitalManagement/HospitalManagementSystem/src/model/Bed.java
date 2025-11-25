package model;

public class Bed {
    private int id;
    private String bedNumber;
    private boolean occupied;
    public Bed() {}
    public Bed(int id, String bedNumber, boolean occupied) {
        this.id = id; this.bedNumber = bedNumber; this.occupied = occupied;
    }
    public int getId() { 
        return id; 
    }
    public String getBedNumber() {
        return bedNumber; 
    }
    public boolean isOccupied() { 
        return occupied; 
    }
    public void setOccupied(boolean occupied) { 
        this.occupied = occupied; 
    }
    @Override
    public String toString() {
        return String.format("[%d] %s - %s", id, bedNumber, (occupied ? "Occupied" : "Free"));
    }
}
