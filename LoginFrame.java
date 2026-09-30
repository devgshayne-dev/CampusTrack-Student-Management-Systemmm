package com.campustrack.ui;

import com.campustrack.db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    private final JTextField username = new JTextField();
    private final JPasswordField password = new JPasswordField();

    public LoginFrame() {
        setTitle("CampusTrack - Login");
        setSize(420,260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel p=new JPanel(new GridLayout(5,1,10,10));
        p.setBorder(BorderFactory.createEmptyBorder(25,35,25,35));
        p.add(new JLabel("Username"));
        p.add(username);
        p.add(new JLabel("Password"));
        p.add(password);
        JButton login=new JButton("Login");
        p.add(login);
        add(p);

        login.addActionListener(e -> authenticate());
        password.addActionListener(e -> authenticate());
    }

    private void authenticate() {
        String sql="SELECT role FROM users WHERE username=? AND password=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,username.getText().trim());
            p.setString(2,new String(password.getPassword()));
            ResultSet r=p.executeQuery();
            if(r.next()) {
                dispose();
                new MainFrame(username.getText().trim()).setVisible(true);
            } else JOptionPane.showMessageDialog(this,"Invalid username or password.");
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(this,"Database connection failed:\n"+ex.getMessage(),
                    "Connection Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
