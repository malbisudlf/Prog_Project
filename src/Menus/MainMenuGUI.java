package Menus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import GUI.AhorcadoGUI;
import GUI.PongGUI;
import GUI.SnakeGUI;

public class MainMenuGUI extends JFrame{

	public MainMenuGUI(){
		
		setTitle("MAIN MENU");
		setSize(400, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(3, 1));
		
		JButton botonAhorcado = new JButton("AHORCADO");
		JButton botonPong = new JButton("PONG");
		JButton botonSnake = new JButton("SNAKE");
		
		botonAhorcado.setFocusable(false);
		botonPong.setFocusable(false);
		botonSnake.setFocusable(false);
		
		Font fontPortada = new Font("MV BOLI", Font.BOLD, 50);
		botonAhorcado.setFont(fontPortada);
		botonPong.setFont(fontPortada);
		botonSnake.setFont(fontPortada);
		
		botonAhorcado.setBackground(Color.black);
		botonPong.setBackground(Color.black);
		botonSnake.setBackground(Color.black);
		
		botonAhorcado.setForeground(Color.green);
		botonPong.setForeground(Color.green);
		botonSnake.setForeground(Color.green);
		
		
		/*
		ImageIcon portadaAhorcado = new ImageIcon("src/Images/portadaAhorcado.png");
		botonAhorcado.setIcon(portadaAhorcado);
		
		ImageIcon portadaPong = new ImageIcon("src/Images/portadaPong.png");
		botonPong.setIcon(portadaPong);
		
		ImageIcon portadaSnake = new ImageIcon("src/Images/portadaSnake.png");
		botonSnake.setIcon(portadaSnake);
		*/
		
		
		add(botonAhorcado);
		add(botonPong);
		add(botonSnake);
		
		botonAhorcado.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 new AhorcadoGUI();
				 dispose();
			}
		});
		
		botonPong.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 new PongGUI();
				 //dispose();
			}
		});
		
		botonSnake.addActionListener(new ActionListener() {
			
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
