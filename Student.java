package com.campustrack.model;

public class Student {
    private int id;
    private String rollNo, name, email, phone, department;
    private int yearLevel;

    public Student(int id, String rollNo, String name, String email, String phone,
                   String department, int yearLevel) {
        this.id = id; this.rollNo = rollNo; this.name = name; this.email = email;
        this.phone = phone; this.department = department; this.yearLevel = yearLevel;
    }

    public int getId() { return id; }
    public String getRollNo() { return rollNo; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getDepartment() { return department; }
    public int getYearLevel() { return yearLevel; }
}
