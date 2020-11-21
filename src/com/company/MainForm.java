package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.Position;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class MainForm extends JFrame {
    private JPanel panel1;
    private JTextField usernamelbl;
    private JPasswordField passwordlbl;
    private JButton button1;
    private JLabel registerlbl;

    public MainForm(){
        JFrame frame = new JFrame("ZOO Keeper Software");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = null;

        URL imageURL = MainForm.class.getResource("lion.png");
        if(imageURL != null){
            icon = new ImageIcon(imageURL);
            frame.setIconImage(icon.getImage());
        } else {
            JOptionPane.showMessageDialog(frame, "Icon image not found");
        }

        panel1.setBorder(new LineBorder(Color.BLUE));
        frame.getContentPane().add(panel1, BorderLayout.CENTER);
        frame.setSize(560, 250);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainForm.this.logUser();
            }
        });

        Font font = registerlbl.getFont();
        Map<TextAttribute, Object> underline = new HashMap<>(font.getAttributes());
        underline.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        Map<TextAttribute, Object> noUnderline = new HashMap<>(font.getAttributes());
        noUnderline.put(TextAttribute.UNDERLINE, -1);

        registerlbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerlbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RegisterForm();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerlbl.setFont(font.deriveFont(underline));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerlbl.setFont(font.deriveFont(noUnderline));
//                registerlbl.setFont(new Font(font.getName(), font.getStyle(), font.getSize()));
            }
        });
    }
    void logUser(){
        String username=usernamelbl.getText();
        String password=new String(passwordlbl.getPassword());
        String loginSQL = "SELECT ZNAME "+
                "FROM zoo "+
                "WHERE ZNAME=? "+
                "AND ZPASS=?";
        try(Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(loginSQL)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, new String(passwordlbl.getPassword()));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                new ButtonForm();
            } else {
                JOptionPane.showMessageDialog(null, "User Not Found!\nPlease try registering first");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    static Connection connect(){
        String url = "jdbc:sqlite:zoo.db";
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

}
