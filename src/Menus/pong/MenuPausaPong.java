package Menus.pong;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Menus.MainMenuGUI;

public class MenuPausaPong extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public MenuPausaPong() {
		
		setTitle("Pausado");
		setSize(360, 540);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBackground(Color.WHITE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.BLACK);
		
		JLabel titulo = new JLabel("PAUSADO", SwingConstants.CENTER);
		titulo.setForeground(Color.WHITE);
		titulo.setFont(new Font("Arial", Font.BOLD, 50));
		panel.add(titulo, BorderLayout.NORTH);
		
		JPanel botones = new JPanel();
		botones.setLayout(new GridLayout(4,1));
		botones.setBackground(Color.BLACK);
		
		Font fontBotones = new Font("Arial", Font.BOLD, 30);
		
		JButton Reanudar = new JButton("Volver");
		Reanudar.setFocusable(false);
		Reanudar.setBackground(Color.BLACK);
		Reanudar.setForeground(Color.WHITE);
		Reanudar.setFont(fontBotones);
		Reanudar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		JButton Controles = new JButton("Controles");
		Controles.setFocusable(false);
		Controles.setBackground(Color.BLACK);
		Controles.setForeground(Color.WHITE);
		Controles.setFont(fontBotones);
		Controles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ShowControles();
			}
		});
		
		JButton Restart = new JButton("Restart");
		Restart.setFocusable(false);
		Restart.setBackground(Color.BLACK);
		Restart.setForeground(Color.WHITE);
		Restart.setFont(fontBotones);
		Restart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		JButton MainMenu = new JButton("Menu Principal");
		MainMenu.setFocusable(false);
		MainMenu.setBackground(Color.BLACK);
		MainMenu.setForeground(Color.WHITE);
		MainMenu.setFont(fontBotones);
		MainMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new MainMenuGUI();
				dispose();
			}
		});
		
		botones.add(Reanudar);
		botones.add(Controles);
		botones.add(Restart);
		botones.add(MainMenu);
		panel.add(botones, BorderLayout.CENTER);
		add(panel);
		setVisible(true);
	}
	
	private void ShowControles() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, "Jugador 1 : W,S\nJugador 2 : Flechas");
	}
}
