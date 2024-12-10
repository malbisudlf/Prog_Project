package Menus.pong;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import GUI.PongGUI;
import Menus.MainMenuGUI;
import Menus.snake.menuSnake;
import usuario.UsuarioSnake;

public class MenuPong extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public static UsuarioSnake usuario;
	public MenuPong(UsuarioSnake usuario) {
		menuSnake.usuario = usuario;
		
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
		PvP.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Obtener el tamaño actual del botón
                int anchoBoton = PvP.getWidth();
                int altoBoton = PvP.getHeight();

                // Redimensionar la imagen para ajustarse al tamaño del botón
                Image imagenEscalada = portadaPvP.getImage().getScaledInstance(anchoBoton , altoBoton , Image.SCALE_SMOOTH); //ChatGPT
                PvP.setIcon(new ImageIcon(imagenEscalada));
            }
        });
		PvP.setIcon(portadaPvP);
		PvP.setFocusable(false);
		PvP.setBackground(Color.BLACK);
		PvP.setForeground(Color.WHITE);
		PvP.setFont(fontBotones);
		PvP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new PongGUI(usuario);
				dispose();
			}
		});
		
		JButton PvC = new JButton();
		ImageIcon portadaPvC = new ImageIcon("resources/images/PvC.png");
		PvC.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Obtener el tamaño actual del botón
                int anchoBoton = PvC.getWidth();
                int altoBoton = PvC.getHeight();

                // Redimensionar la imagen para ajustarse al tamaño del botón
                Image imagenEscalada = portadaPvC.getImage().getScaledInstance(anchoBoton , altoBoton , Image.SCALE_SMOOTH); //ChatGPT
                PvC.setIcon(new ImageIcon(imagenEscalada));
            }
        });
		PvC.setIcon(portadaPvC);
		PvC.setFocusable(false);
		PvC.setBackground(Color.BLACK);
		PvC.setForeground(Color.WHITE);
		PvC.setFont(fontBotones);
		PvC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new MenuDificultadPong(usuario);
				dispose();
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
				Skins();
			}
		});
		
		JButton Volver = new JButton();
		ImageIcon portadaVolver = new ImageIcon("resources/images/volver.png");
		Volver.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Obtener el tamaño actual del botón
                int anchoBoton = Volver.getWidth();
                int altoBoton = Volver.getHeight();

                // Redimensionar la imagen para ajustarse al tamaño del botón
                Image imagenEscalada = portadaVolver.getImage().getScaledInstance(anchoBoton , altoBoton , Image.SCALE_SMOOTH); //ChatGPT
                Volver.setIcon(new ImageIcon(imagenEscalada));
            }
        });
		Volver.setIcon(portadaVolver);
		Volver.setFocusable(false);
		Volver.setBackground(Color.BLACK);
		Volver.setForeground(Color.WHITE);
		Volver.setFont(fontBotones);
		Volver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new MainMenuGUI(usuario);
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

	private void Skins() {
		JOptionPane.showMessageDialog(this, "Aun sin implementar");
	}
	
}
