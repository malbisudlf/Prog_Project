package Menus.pong;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import GUI.PongGUI;

public class MenuPong extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public MenuPong() {
		
		setTitle("Pong");
		setSize(540, 540);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBackground(Color.WHITE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.BLACK);
		
		JLabel titulo = new JLabel("PONG", SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 50));
		panel.add(titulo, BorderLayout.NORTH);
		
		JPanel botones = new JPanel();
		botones.setLayout(new GridLayout(2,1));
		botones.setBackground(Color.WHITE);
		
		JButton PvP = new JButton("PVP");
		
		PvP.setFocusable(false);
		PvP.setBackground(Color.BLACK);
		
		PvP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new PongGUI();
				dispose();
			}
		});
		
		JButton PvC = new JButton();
		PvC.setFocusable(false);
		PvC.setBackground(Color.BLACK);
		
		PvC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(PvC, this, "Aun sin implementar", EXIT_ON_CLOSE);
			}
		});
		
		botones.add(PvP);
		botones.add(PvC);
		panel.add(botones, BorderLayout.CENTER);
		add(panel);
		setVisible(true);
		 
	}
}
