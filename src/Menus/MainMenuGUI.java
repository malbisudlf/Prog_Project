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

	
	private static final long serialVersionUID = 1L;

	public MainMenuGUI(){
		
		setTitle("MAIN MENU");
		setSize(600, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(3, 1));
		setResizable(false);
		
		JButton botonAhorcado = new JButton();
		JButton botonPong = new JButton();
		JButton botonSnake = new JButton();
		
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
		
		
		

		ImageIcon portadaAhorcado = new ImageIcon(new ImageIcon("resources/images/portadaHangman.png").getImage().getScaledInstance(600, 155, java.awt.Image.SCALE_SMOOTH));
		botonAhorcado.setIcon(portadaAhorcado);
		
		ImageIcon portadaPong = new ImageIcon(new ImageIcon("resources/images/portadaPongClasico.png").getImage().getScaledInstance(500, 100, java.awt.Image.SCALE_SMOOTH));
		botonPong.setIcon(portadaPong);
		
		ImageIcon portadaSnake = new ImageIcon(new ImageIcon("resources/images/portadaSnakeVerde.png").getImage().getScaledInstance(650, 150, java.awt.Image.SCALE_SMOOTH));
		botonSnake.setIcon(portadaSnake);
		
		add(botonAhorcado);
		add(botonPong);
		add(botonSnake);
		
		botonAhorcado.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 new menuAhorcado();
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
