package Menus.snake;

import javax.swing.*;

import GUI.*;
import Menus.MainMenuGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuSnake extends JFrame{
	
	private String userName;
	
	public menuSnake(String userName) {
		
		this.userName = userName; 
		
		setTitle("SNAKE MENU");
		setSize(600, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		
		setBackground(Color.BLACK);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.BLACK);
		
		JLabel titulo = new JLabel("			SNAKE    Usuario: " + userName, SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 30));
        mainPanel.add(titulo, BorderLayout.NORTH);
        titulo.setBackground(Color.BLACK);
        titulo.setForeground(Color.WHITE);
		
		JPanel botones = new JPanel();
		botones.setLayout(new GridLayout(2, 2)); //JUGAR, LEADERBOARD, SKINS, VOLVER
		botones.setBackground(Color.BLACK);
		
		Font fontBotones = new Font("MV BOLI", Font.BOLD, 30);
		
		//Boton para empezar a jugar
		
		JButton botonJugar = new JButton("JUGAR");
		
		botonJugar.setFocusable(false);
		botonJugar.setBackground(Color.BLACK);
		botonJugar.setForeground(Color.WHITE);
		botonJugar.setFont(fontBotones);
		
		botonJugar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new SnakeGUI();
				dispose();
			}
		});
		
		
		//Boton para consultar leaderboard
		
		JButton botonLeaderboard = new JButton("LEADERBOARD");
		
		botonLeaderboard.setFocusable(false);
		botonLeaderboard.setBackground(Color.BLACK);
		botonLeaderboard.setForeground(Color.WHITE);
		botonLeaderboard.setFont(fontBotones);
		
		botonLeaderboard.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showLeaderboard();
			}
		});
		
		
		
		//Boton para tienda de skins
		
		JButton botonSkins = new JButton("SKINS");
		
		botonSkins.setFocusable(false);
		botonSkins.setBackground(Color.BLACK);
		botonSkins.setForeground(Color.WHITE);
		botonSkins.setFont(fontBotones);
		
		botonSkins.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showSkins();
				
			}
		});
		
		
		//Boton para volver al MainMenuGUI
		
		JButton botonVolverMenu = new JButton("VOLVER");
		
		botonVolverMenu.setFocusable(false);
		botonVolverMenu.setBackground(Color.BLACK);
		botonVolverMenu.setForeground(Color.WHITE);
		botonVolverMenu.setFont(fontBotones);


		
		botonVolverMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new MainMenuGUI();
				dispose();
			}
		});
        
		
		
		
		//Aladir botones
		botones.add(botonJugar);
		botones.add(botonLeaderboard);
		botones.add(botonSkins);
		botones.add(botonVolverMenu);
		
		
		
		
		mainPanel.add(botones, BorderLayout.CENTER);
		
		add(mainPanel);
		
		
		setVisible(true);
		
	}
	
	
	//Metodo leaderboard (no implementado)
	private void showLeaderboard() {
		JOptionPane.showMessageDialog(this, "NO IMPLEMENTADO AUN");
	}
	
	
	
	//Metodo skins (no implementado)
	private void showSkins() {
        JOptionPane.showMessageDialog(this, "Skins aún no implementado");
    }

}