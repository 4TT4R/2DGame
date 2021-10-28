package com.ATTAR.grafic;

import com.ATTAR.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Settings {






    public Settings() {
        int x,y;
        JFrame frame = new JFrame("Game name"); // GUI gui = new GUI() as well
        // default value JFrame.HIDE_ON_CLOSE
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(800,600);
        JButton play = new JButton("play");
        play.setBounds(200,300,100,50);
        JButton exit = new JButton("exit");
        play.setBounds(200,300,100,50);
        FlowLayout layout = new FlowLayout();
        frame.setLayout(layout);
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        frame.add(panel);
        frame.add(panel1);
        JCheckBox fullscrean = new JCheckBox("Fullscrean");
        JComboBox<String> comboResolutions = new JComboBox<String>();


        comboResolutions.addItem("1024×576");
        comboResolutions.addItem("1152×648");
        comboResolutions.addItem("1280×720");
        comboResolutions.addItem("1920×1080");

        comboResolutions.addItem("800×600");
        comboResolutions.addItem("960×720");
        comboResolutions.addItem("1024×768");
        comboResolutions.addItem("1280×960");

        panel.add(play);
        panel.add(exit);

        panel.add(fullscrean);

        panel1.add(comboResolutions);
        frame.setVisible(true);
        play.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent evt) {

                        frame.setVisible(false);
                        int i = comboResolutions.getSelectedIndex();
                        Main.createWindow(fullscrean.isSelected(),
                                Integer.parseInt(comboResolutions.getItemAt(i).split("×")[0]),
                                Integer.parseInt(comboResolutions.getItemAt(i).split("×")[1]),
                                frame,i);


                    };
                });
        exit.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent evt) {
                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

                    };
                });
    }
}
