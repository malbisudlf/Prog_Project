package Menus.Ahorcado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import GUI.AhorcadoGUI;
import Menus.MainMenuGUI;
import Menus.snake.menuSnake;
import usuario.UsuarioSnake;

public class menuAhorcado extends JFrame{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static UsuarioSnake usuario;

		public menuAhorcado() {
			menuSnake.usuario = usuario;
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
	        titulo.setForeground(Color.WHITE);
	        Font fontBotones = new Font("MV BOLI", Font.BOLD, 30);
	        
			
			JPanel button = new JPanel();
			button.setLayout(new GridLayout(2,2));
			button.setBackground(Color.BLACK);
			
			
			JButton playbutton = new JButton("JUGAR");
			playbutton.setFocusable(false);
			playbutton.setBackground(Color.BLACK);
			playbutton.setForeground(Color.WHITE);
			playbutton.setFont(fontBotones);
			
			
			playbutton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					new AhorcadoGUI();
					dispose();
				}
				
			}
			);
			
			JButton leaderbutton = new JButton("LEADERBOARD");
			leaderbutton.setFocusable(false);
			leaderbutton.setBackground(Color.BLACK);
			leaderbutton.setForeground(Color.WHITE);
			leaderbutton.setFont(fontBotones);
			leaderbutton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new AhorcadoLeaderboard();
					dispose();
					
				}
				
			
			});
			
			JButton skinbutton = new JButton("SKINS");
			skinbutton.setFocusable(false);
			skinbutton.setBackground(Color.BLACK);
			skinbutton.setForeground(Color.WHITE);
			skinbutton.setFont(fontBotones);
			
			skinbutton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					AhorcadoSkin();
					
				}
				
			});
			
			JButton volverbutton = new JButton();
			volverbutton.setFocusable(false);
			volverbutton.setBackground(Color.BLACK);
			volverbutton.setForeground(Color.WHITE);
			volverbutton.setFont(fontBotones);
			
			ImageIcon volverfoto = new ImageIcon("resources/images/volver.png");
			
			volverbutton.addComponentListener(new ComponentAdapter() {
	            @Override
	            public void componentResized(ComponentEvent e) {
	                
	                int anchoBoton = volverbutton.getWidth();
	                int altoBoton = volverbutton.getHeight();

	                // IAG ChatGPT
	                //Adaptado
	                Image imagenEscalada = volverfoto.getImage().getScaledInstance(anchoBoton -25, altoBoton -5, Image.SCALE_SMOOTH); 
	                //HAsta aqui uso de chatGPT
	                volverbutton.setIcon(new ImageIcon(imagenEscalada));
	            }
	        });
			volverbutton.setIcon(volverfoto);
			
			volverbutton.addActionListener(new ActionListener() {
				

				@Override
				public void actionPerformed(ActionEvent e) {
					new MainMenuGUI(usuario);
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
		
		
		
		private void AhorcadoSkin() {
			JOptionPane.showMessageDialog(this, "Aun sin implementar");
		}
		
			
}