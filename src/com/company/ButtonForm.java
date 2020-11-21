package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ButtonForm extends JFrame{
    private JPanel panel1;
    private JComboBox comboBox1;

    public ButtonForm(){
        JFrame jFrame = new JFrame();
        setTitle("Form2");
        jFrame.getContentPane().add(panel1, BorderLayout.CENTER);
        jFrame.setSize(760, 200);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        centerWindow(jFrame);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int lastNum = comboBox1.getItemCount()-1;
        comboBox1.addItem("Other");
        ImageIcon icon = null;

        URL imageURL = MainForm.class.getResource("lion.png");
        if(imageURL != null){
            icon = new ImageIcon(imageURL);
            jFrame.setIconImage(icon.getImage());
        } else {
            JOptionPane.showMessageDialog(jFrame, "Icon image not found");
        }
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = (String)comboBox1.getSelectedItem();
                if(s=="Lion"){
                    new AnimalSpecs("Lion");                }
                else if (s=="Elephant") {
                    new AnimalSpecs("Elephant");            }
                else if (s=="Tiger") {
                    new AnimalSpecs("Tiger");               }
                else if (s=="Parrot") {
                    new AnimalSpecs("Parrot");              }
                else if (s=="Monkey") {
                    new AnimalSpecs("Monkey");              }
                else if (s=="Panda") {
                    new AnimalSpecs("Panda");               }
                else if (s=="Camel") {
                    new AnimalSpecs("Camel");               }
                else {
                    setWarningMsg("We apologise, we do not shelter other animals here! "); }
            }
        });

    }

    public void centerWindow(Window frame){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth())/2);
        int y = (int) ((dimension.getHeight() - frame.getHeight())/2);
        frame.setLocation(x,y);
    }
    public void setWarningMsg(String text){
        Toolkit.getDefaultToolkit().beep();
        JOptionPane optionPane = new JOptionPane(text,JOptionPane.WARNING_MESSAGE);
        JDialog dialog = optionPane.createDialog("Warning!");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
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
