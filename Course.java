package com.campustrack.model;

public class Course {
    private int id, credits;
    private String code, name, teacher;

    public Course(int id, String code, String name, String teacher, int credits) {
        this.id=id; this.code=code; this.name=name; this.teacher=teacher; this.credits=credits;
    }

    public int getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getTeacher() { return teacher; }
    public int getCredits() { return credits; }
}
