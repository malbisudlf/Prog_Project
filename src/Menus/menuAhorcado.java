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
			mainpanel.add(titulo, SwingConstants.NORTH);
			titulo.setBackground(Color.BLACK);
	        titulo.setForeground(Color.GREEN);
			
			JPanel button = new JPanel();
			button.setLayout(new GridLayout(2,2));
			button.setBackground(Color.BLACK);
			
			
			JButton playbutton = new JButton("Jugar");
			playbutton.setFocusable(false);
			
			
			playbutton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new AhorcadoGUI();
				}
				
			}
			);
			
			JButton leaderbutton = new JButton("Leaderboard");
			leaderbutton.setFocusable(false);
			
			playbutton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					AhorcadoLeaderboard();
					
				}
				
			
			});
			
			JButton skinbutton = new JButton("Skins");
			skinbutton.setFocusable(false);
			
			skinbutton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					AhorcadoSkin();
					
				}
				
			});
			
			
		}
		
		private void AhorcadoLeaderboard() {
			JOptionPane.showMessageDialog(this, "Aun sin hacer");
		}
		
		private void AhorcadoSkin() {
			JOptionPane.showMessageDialog(this, "Aun sin implementar");
		}
			
		}
		
		
		


