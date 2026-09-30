package com.campustrack.dao;

import com.campustrack.db.DBConnection;
import com.campustrack.model.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    public List<Course> findAll() throws SQLException {
        List<Course> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();
            PreparedStatement p=c.prepareStatement("SELECT * FROM courses ORDER BY course_id DESC");
            ResultSet r=p.executeQuery()) {
            while(r.next()) list.add(new Course(r.getInt("course_id"),r.getString("course_code"),
                    r.getString("course_name"),r.getString("teacher"),r.getInt("credits")));
        }
        return list;
    }
}
