package model;

public class Patient extends Person {
    private int age;
    private String gender;

    public Patient() {}
    public Patient(int id, String name, int age, String gender) {
        super(id, name);
        this.age = age;
        this.gender = gender;
    }
    public Patient(String name, int age, String gender) {
        this(0, name, age, gender);
    }
    public int getAge() { 
        return age; 
    }
    public void setAge(int age) {
         this.age = age; 
        }

    public String getGender() {
         return gender; 
        }
    public void setGender(String gender) { 
        this.gender = gender; 
    }
    @Override
    public String toString() {
        return String.format("[%d] %s, %d, %s", id, name, age, gender);
    }
}
