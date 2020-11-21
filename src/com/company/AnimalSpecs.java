package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;

public class AnimalSpecs extends JFrame {
    private JPanel panel1;
    private JTextField avgbody;
    private JTextField avgweight;
    private JTextArea desc;
    private JLabel picture;
    private JTextField textField1;
    private JLabel numberlbl;

    public AnimalSpecs(String id){
        JFrame jFrame = new JFrame("Register");
        setTitle("Form2");
        jFrame.getContentPane().add(panel1, BorderLayout.CENTER);
        jFrame.setSize(630, 485);
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
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setBorder(BorderFactory.createCompoundBorder(picture.getBorder(),BorderFactory.createEmptyBorder(5,5,5,5)));
        desc.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        getData(id);

    }

    private void getData(String id){
        String sql = "SELECT NAME, AVGBODYLENGTH, AVGWEIGHT, DESC, PIC, MASS " +
                "FROM zooInfo " +
                "WHERE NAME='"+id+"'";
        try(Connection connection = ButtonForm.connect();
            Statement ps = connection.createStatement()){
            ResultSet rs = ps.executeQuery(sql);
            if(rs.next()){
                textField1.setText(rs.getString("NAME"));
                avgbody.setText(rs.getString("AVGBODYLENGTH"));
                avgweight.setText(rs.getString("AVGWEIGHT"));
                desc.setText(rs.getString("DESC"));
                loadImage("images/"+rs.getString("PIC"));
                numberlbl.setText(String.valueOf(rs.getInt("MASS")));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadImage(String path){
        picture.setIcon(null);
        try {
            BufferedImage image = ImageIO.read(new File(path));
            Image resized = image.getScaledInstance(picture.getWidth(),picture.getHeight(),
                    Image.SCALE_SMOOTH);
            picture.setIcon(new ImageIcon(resized));
            picture.revalidate();
            picture.repaint();
            picture.update(picture.getGraphics());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
