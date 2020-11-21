package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.*;

public class RegisterForm extends JFrame {
    JFrame jFrame = new JFrame();
    private JPanel panel1;
    private JTextField usernamelbl;
    private JTextField zoonamelbl;
    private JPasswordField passwordlbl;
    private JButton registerButton;
    private JTextField animalslbl;

    public RegisterForm(){
        JFrame jFrame = new JFrame("Register a new Zoo Keeper");
        setTitle("Form2");
        setPreferredSize(new Dimension(400,200));
        jFrame.getContentPane().add(panel1, BorderLayout.CENTER);
        jFrame.setSize(360, 400);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = null;
        URL imageURL = MainForm.class.getResource("lion.png");
        if(imageURL != null){
            icon = new ImageIcon(imageURL);
            jFrame.setIconImage(icon.getImage());
        } else {
            JOptionPane.showMessageDialog(jFrame, "Icon image not found");
        }
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterForm.this.addUser();
                JOptionPane.showMessageDialog(null, "User Added!");
                new MainForm();
            }
        });
    }

    public void addUser(){
        try {
            Connection con2 = DriverManager.getConnection("jdbc:sqlite:zoo.db");
                        Statement statement2 = con2.createStatement();
            String createSQL = "CREATE TABLE IF NOT EXISTS zoo (" +
                    "ZNAME TEXT NOT NULL," +
                    "ZOONAME," +
                    "ZPASS TEXT," +
                    "ZNO INTEGER," +
                    "PRIMARY KEY (ZNAME))";
            statement2.executeUpdate(createSQL);

            String query = "INSERT OR IGNORE INTO zoo(ZNAME,ZOONAME,ZPASS,ZNO) VALUES(?,?,?,?)";
            PreparedStatement pst = con2.prepareStatement(query);
            pst.setString(1,usernamelbl.getText());
            pst.setString(2,zoonamelbl.getText());
            pst.setString(3,new String(passwordlbl.getPassword()));
            pst.setInt(4,Integer.parseInt(animalslbl.getText()));
            pst.execute();
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
