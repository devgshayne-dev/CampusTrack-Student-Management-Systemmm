package com.campustrack.ui;

import com.campustrack.dao.StudentDAO;
import com.campustrack.dao.CourseDAO;
import com.campustrack.db.DBConnection;
import com.campustrack.model.Student;
import com.campustrack.model.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class MainFrame extends JFrame {
    private final StudentDAO studentDAO=new StudentDAO();
    private final CourseDAO courseDAO=new CourseDAO();
    private final DefaultTableModel studentModel=new DefaultTableModel(
            new String[]{"ID","Roll No","Name","Email","Phone","Department","Year"},0) {
        public boolean isCellEditable(int r,int c){return false;}
    };
    private final JTable studentTable=new JTable(studentModel);
    private final JLabel stats=new JLabel();

    public MainFrame(String user) {
        setTitle("CampusTrack | Student Course & Attendance Management");
        setSize(1050,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel top=new JPanel(new BorderLayout());
        JLabel title=new JLabel("  CAMPUS TRACK");
        title.setFont(new Font("SansSerif",Font.BOLD,22));
        top.add(title,BorderLayout.WEST);
        top.add(new JLabel("Logged in: "+user+"  "),BorderLayout.EAST);
        add(top,BorderLayout.NORTH);

        JTabbedPane tabs=new JTabbedPane();
        tabs.addTab("Dashboard",dashboard());
        tabs.addTab("Students",students());
        tabs.addTab("Courses",courses());
        tabs.addTab("Attendance",attendance());
        tabs.addTab("Marks",marks());
        add(tabs);

        refreshStudents();
        refreshStats();
    }

    private JPanel dashboard() {
        JPanel p=new JPanel(new GridBagLayout());
        p.add(stats);
        return p;
    }

    private JPanel students() {
        JPanel p=new JPanel(new BorderLayout(10,10));
        JPanel form=new JPanel(new GridLayout(2,7,5,5));
        JTextField roll=new JTextField(), name=new JTextField(), email=new JTextField(),
                phone=new JTextField(), dept=new JTextField(), year=new JTextField("1");
        form.add(new JLabel("Roll No")); form.add(new JLabel("Name")); form.add(new JLabel("Email"));
        form.add(new JLabel("Phone")); form.add(new JLabel("Department")); form.add(new JLabel("Year")); form.add(new JLabel(""));
        form.add(roll); form.add(name); form.add(email); form.add(phone); form.add(dept); form.add(year);
        JButton add=new JButton("Add Student"); form.add(add);
        p.add(form,BorderLayout.NORTH);

        JPanel search=new JPanel(new BorderLayout());
        JTextField q=new JTextField();
        JButton go=new JButton("Search");
        JButton all=new JButton("Refresh");
        JButton del=new JButton("Delete Selected");
        search.add(new JLabel(" Search: "),BorderLayout.WEST); search.add(q,BorderLayout.CENTER);
        JPanel actions=new JPanel(); actions.add(go); actions.add(all); actions.add(del);
        search.add(actions,BorderLayout.EAST);
        p.add(search,BorderLayout.SOUTH);
        p.add(new JScrollPane(studentTable),BorderLayout.CENTER);

        add.addActionListener(e -> {
            try {
                int y=Integer.parseInt(year.getText().trim());
                studentDAO.add(new Student(0,roll.getText().trim(),name.getText().trim(),email.getText().trim(),
                        phone.getText().trim(),dept.getText().trim(),y));
                refreshStudents(); refreshStats();
                roll.setText(""); name.setText(""); email.setText(""); phone.setText(""); dept.setText("");
            } catch(Exception ex) { JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); }
        });
        go.addActionListener(e -> refreshStudents(q.getText().trim()));
        all.addActionListener(e -> refreshStudents());
        del.addActionListener(e -> {
            int row=studentTable.getSelectedRow();
            if(row<0) return;
            int id=(int)studentModel.getValueAt(row,0);
            if(JOptionPane.showConfirmDialog(this,"Delete selected student?","Confirm",
                    JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
                try { studentDAO.delete(id); refreshStudents(); refreshStats(); }
                catch(SQLException ex){JOptionPane.showMessageDialog(this,ex.getMessage());}
            }
        });
        return p;
    }

    private JPanel courses() {
        JPanel p=new JPanel(new BorderLayout());
        DefaultTableModel m=new DefaultTableModel(new String[]{"ID","Code","Course","Teacher","Credits"},0);
        JTable t=new JTable(m);
        try {
            for(Course c:courseDAO.findAll())
                m.addRow(new Object[]{c.getId(),c.getCode(),c.getName(),c.getTeacher(),c.getCredits()});
        } catch(SQLException e) { JOptionPane.showMessageDialog(this,e.getMessage()); }
        p.add(new JScrollPane(t));
        return p;
    }

    private JPanel attendance() {
        JPanel p=new JPanel(new GridBagLayout());
        p.add(new JLabel("Attendance module: record PRESENT/ABSENT entries in the attendance table. "
                +"The database is ready for integration."));
        return p;
    }

    private JPanel marks() {
        JPanel p=new JPanel(new GridBagLayout());
        p.add(new JLabel("Marks module: store exam marks by student and course in the marks table."));
        return p;
    }

    private void refreshStudents(){refreshStudents("");}
    private void refreshStudents(String search) {
        try {
            studentModel.setRowCount(0);
            for(Student s:studentDAO.findAll(search))
                studentModel.addRow(new Object[]{s.getId(),s.getRollNo(),s.getName(),s.getEmail(),
                        s.getPhone(),s.getDepartment(),s.getYearLevel()});
        } catch(SQLException e) { JOptionPane.showMessageDialog(this,e.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE); }
    }

    private void refreshStats() {
        try(Connection c=DBConnection.getConnection();
            Statement s=c.createStatement();
            ResultSet r=s.executeQuery("SELECT (SELECT COUNT(*) FROM students) students, (SELECT COUNT(*) FROM courses) courses")) {
            if(r.next()) stats.setText("<html><h1>Welcome to CampusTrack</h1>"
                    +"<p>Students: <b>"+r.getInt("students")+"</b> &nbsp;&nbsp; "
                    +"Courses: <b>"+r.getInt("courses")+"</b></p>"
                    +"<p>Today: "+LocalDate.now()+"</p></html>");
        } catch(SQLException e) { stats.setText("Database unavailable: "+e.getMessage()); }
    }
}
