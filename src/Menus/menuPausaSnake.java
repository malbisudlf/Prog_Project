package Menus;

import javax.swing.*;

import GUI.SnakeGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuPausaSnake extends JFrame{

    //BOTON REANUDAR (JButton)
    //BOTON REINICIAR (JButton)
    //BOTON VOLVER (JButton)
    
    public menuPausaSnake(){

        setTitle("SNAKE MENU PAUSA");
		setSize(300, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

        setLayout(new GridLayout(3, 1, 2, 2));

        //BOTON REANUDAR (JButton)
        JButton botonReanudar = new JButton("REANUDAR");
        JButton botonReiniciar = new JButton("REINICIAR");
        JButton botonVolver = new JButton("VOLVER");

        botonReanudar.setFocusable(false);
		botonReiniciar.setFocusable(false);
		botonVolver.setFocusable(false);

        botonReanudar.setBackground(Color.BLACK);
        botonReiniciar.setBackground(Color.BLACK);
        botonVolver.setBackground(Color.BLACK);

        botonReanudar.setForeground(Color.GREEN);
        botonReiniciar.setForeground(Color.GREEN);
        botonVolver.setForeground(Color.GREEN);

        Font font = new Font("ARIAL", Font.BOLD, 25);

        botonReanudar.setFont(font);
        botonReiniciar.setFont(font);
        botonVolver.setFont(font);

        add(botonReanudar);
        add(botonReiniciar);
        add(botonVolver);

        botonReanudar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                dispose();
            }
            
        });

        botonReiniciar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                new SnakeGUI();
                dispose();
            }
            
        });

        botonVolver.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                new menuSnake();
                dispose();
            }
            
        });

        setVisible(true);
    }
}
