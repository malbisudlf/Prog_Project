package Menus.pong;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import GUI.PongGUI;
import Menus.MainMenuGUI;

public class MenuPong extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public MenuPong() {
		
		setTitle("PONG");
		setSize(540, 540);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBackground(Color.BLACK);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.BLACK);
		
		JLabel titulo = new JLabel("PONG", SwingConstants.CENTER);
		titulo.setForeground(Color.WHITE);
		titulo.setFont(new Font("Arial", Font.BOLD, 50));
		panel.add(titulo, BorderLayout.NORTH);
		
		JPanel botones = new JPanel();
		botones.setLayout(new GridLayout(2,2));
		botones.setBackground(Color.BLACK);
		
		Font fontBotones = new Font("Arial", Font.BOLD, 30);
		
		JButton PvP = new JButton();
		ImageIcon portadaPvP = new ImageIcon("resources/images/PvP.png");
		PvP.setIcon(portadaPvP);
		PvP.setFocusable(false);
		PvP.setBackground(Color.BLACK);
		PvP.setForeground(Color.WHITE);
		PvP.setFont(fontBotones);
		PvP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new PongGUI();
				dispose();
			}
		});
		
		JButton PvC = new JButton();
		ImageIcon portadaPvC = new ImageIcon("resources/images/PvC.png");
		PvC.setIcon(portadaPvC);
		PvC.setFocusable(false);
		PvC.setBackground(Color.BLACK);
		PvC.setForeground(Color.WHITE);
		PvC.setFont(fontBotones);
		PvC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PvC();
			}
		});
		
		JButton Skins = new JButton("Skins");
		Skins.setFocusable(false);
		Skins.setBackground(Color.BLACK);
		Skins.setForeground(Color.WHITE);
		Skins.setFont(fontBotones);
		Skins.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Skins();
			}
		});
		
		JButton Volver = new JButton("Volver");
		Volver.setFocusable(false);
		Volver.setBackground(Color.BLACK);
		Volver.setForeground(Color.WHITE);
		Volver.setFont(fontBotones);
		Volver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new MainMenuGUI();
				dispose();
			}
		});
		
		botones.add(PvP);
		botones.add(PvC);
		botones.add(Skins);
		botones.add(Volver);
		panel.add(botones, BorderLayout.CENTER);
		add(panel);
		setVisible(true);
	}
	
	private void PvC() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, "Aun sin implementar");
	}
	
	private void Skins() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, "Aun sin implementar");
	}
	
}
