package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainMenuGUI extends JFrame{

	public MainMenuGUI(){
		
		setTitle("MAIN MENU");
		setSize(300, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(3, 1));
		
		
		JButton botonAhorcado = new JButton("AHORCADO");
		JButton botonPong = new JButton("PONG");
		JButton botonSnake = new JButton("SNAKE");
		
		ImageIcon portadaAhorcado = new ImageIcon("src/Images/portadaAhorcado.png");
		botonAhorcado.setIcon(portadaAhorcado);
		
		ImageIcon portadaPong = new ImageIcon("src/Images/portadaPong.png");
		botonPong.setIcon(portadaPong);
		
		ImageIcon portadaSnake = new ImageIcon("src/Images/portadaSnake.png");
		botonSnake.setIcon(portadaSnake);
		
		
		add(botonAhorcado);
		add(botonPong);
		add(botonSnake);
		
		botonAhorcado.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 new AhorcadoGUI();	
			}
		});
		
		botonPong.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 new PongGUI();
			}
		});
		
		botonSnake.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 new SnakeGUI();	
			}
		});
		
		setVisible(true);
		
	}
}
