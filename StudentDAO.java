package com.campustrack.dao;

import com.campustrack.db.DBConnection;
import com.campustrack.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public List<Student> findAll(String search) throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE roll_no LIKE ? OR name LIKE ? OR department LIKE ? ORDER BY student_id DESC";
        try (Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)) {
            String s="%"+search+"%";
            p.setString(1,s); p.setString(2,s); p.setString(3,s);
            ResultSet r=p.executeQuery();
            while(r.next()) list.add(new Student(r.getInt("student_id"),r.getString("roll_no"),
                    r.getString("name"),r.getString("email"),r.getString("phone"),
                    r.getString("department"),r.getInt("year_level")));
        }
        return list;
    }

    public void add(Student s) throws SQLException {
        String sql="INSERT INTO students(roll_no,name,email,phone,department,year_level) VALUES(?,?,?,?,?,?)";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,s.getRollNo()); p.setString(2,s.getName()); p.setString(3,s.getEmail());
            p.setString(4,s.getPhone()); p.setString(5,s.getDepartment()); p.setInt(6,s.getYearLevel());
            p.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(
                "DELETE FROM students WHERE student_id=?")) { p.setInt(1,id); p.executeUpdate(); }
    }
}
