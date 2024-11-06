package Menus.snake;

import javax.swing.*;

import GUI.*;
import Menus.MainMenuGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuSnake extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public static String userName;
	
	public menuSnake(String userName) {
		
		menuSnake.userName = userName;
		
		setTitle("SNAKE MENU");
		setSize(600, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setBackground(Color.BLACK);
		
		//MAIN PANEL
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.BLACK);
		
		
		//LABEL CON EL TITULO Y USUARIO
		JLabel titulo = new JLabel("			SNAKE    Usuario: " + userName, SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 30));
		titulo.setBackground(Color.BLACK);
        titulo.setForeground(Color.WHITE);
        
        mainPanel.add(titulo, BorderLayout.NORTH);
        
		//PANEL CON LOS BOTONES
		JPanel botones = new JPanel();
		botones.setLayout(new GridLayout(2, 2, 10, 10)); //JUGAR, LEADERBOARD, SKINS, VOLVER
		botones.setBackground(Color.BLACK);
		
		//Boton para empezar a jugar
		JButton botonJugar = buttonWithIcon("resources/images/row-1-column-1.png", "JUGAR");
		
		botonJugar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new SnakeGUI(userName);
				dispose();
			}
		});
		
		
		//Boton para consultar leaderboard
		JButton botonLeaderboard = buttonWithIcon("resources/images/row-1-column-2.png", "LEADERBOARD");
		botonLeaderboard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showLeaderboard();
			}
		});
		
		
		
		//Boton para tienda de skins
		JButton botonSkins = buttonWithIcon("resources/images/row-2-column-1.png", "SKINS");
		botonSkins.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showSkins();
				
			}
		});
		
		//Boton para volver al MainMenuGUI
		JButton botonVolverMenu = buttonWithIcon("resources/images/row-2-column-2.png", "VOLVER");
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
	
	//Metodo leaderboard (Aun no implementado)
	private void showLeaderboard() {
		JOptionPane.showMessageDialog(this, "NO IMPLEMENTADO AUN");
	}
	
	
	
	//Metodo skins (Aun no implementado)
	private void showSkins() {
        JOptionPane.showMessageDialog(this, "Skins aún no implementado");
    }
	
	//Metodo para crear un bootn con una imagen de fondo
	
	
	private JButton buttonWithIcon(String imagePath, String text) {
		
        JButton button = new JButton(text);
        
        button.setIcon(new ImageIcon(imagePath));
        
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        
        button.setFont(new Font("MV BOLI", Font.BOLD, 30));
        button.setForeground(Color.WHITE);
        
        button.setFocusable(false);
        button.setBorderPainted(true);
        return button;
	}
}
