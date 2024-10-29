package Menus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import GUI.AhorcadoGUI;

public class menuAhorcado extends JFrame{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public menuAhorcado() {
			
			setTitle("MENU AHORCADO");
			setSize(600, 500);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			
			
			setBackground(Color.BLACK);
			
			JPanel mainpanel = new JPanel();
			mainpanel.setLayout(new BorderLayout());
			mainpanel.setBackground(Color.BLACK);
			
			JLabel titulo = new JLabel("AHORCADO", SwingConstants.CENTER);
			titulo.setFont(new Font("Arial", Font.BOLD, 50));
			mainpanel.add(titulo, BorderLayout.NORTH);
			titulo.setBackground(Color.BLACK);
	        titulo.setForeground(Color.GREEN);
	        Font fontBotones = new Font("MV BOLI", Font.BOLD, 30);
	        
			
			JPanel button = new JPanel();
			button.setLayout(new GridLayout(2,2));
			button.setBackground(Color.BLACK);
			
			
			JButton playbutton = new JButton("JUGAR");
			playbutton.setFocusable(false);
			playbutton.setBackground(Color.BLACK);
			playbutton.setForeground(Color.GREEN);
			playbutton.setFont(fontBotones);
			
			
			playbutton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new AhorcadoGUI();
					dispose();
				}
				
			}
			);
			
			JButton leaderbutton = new JButton("LEADERBOARD");
			leaderbutton.setFocusable(false);
			leaderbutton.setBackground(Color.BLACK);
			leaderbutton.setForeground(Color.GREEN);
			leaderbutton.setFont(fontBotones);
			playbutton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					AhorcadoLeaderboard();
					
				}
				
			
			});
			
			JButton skinbutton = new JButton("SKINS");
			skinbutton.setFocusable(false);
			skinbutton.setBackground(Color.BLACK);
			skinbutton.setForeground(Color.GREEN);
			skinbutton.setFont(fontBotones);
			
			skinbutton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					AhorcadoSkin();
					
				}
				
			});
			
			JButton volverbutton = new JButton("VOLVER");
			volverbutton.setFocusable(false);
			volverbutton.setBackground(Color.BLACK);
			volverbutton.setForeground(Color.GREEN);
			volverbutton.setFont(fontBotones);
			
			volverbutton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new MainMenuGUI();
					dispose();
					
				}
				
			});
			
			button.add(playbutton);
			button.add(leaderbutton);
			button.add(skinbutton);
			button.add(volverbutton);
			mainpanel.add(button, BorderLayout.CENTER);
			add(mainpanel);
			setVisible(true);
		}
		
		private void AhorcadoLeaderboard() {
			JOptionPane.showMessageDialog(this, "Aun sin hacer");
		}
		
		private void AhorcadoSkin() {
			JOptionPane.showMessageDialog(this, "Aun sin implementar");
		}
			
		}
		
		
		


