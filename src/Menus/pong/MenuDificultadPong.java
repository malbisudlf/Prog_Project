package Menus.pong;

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
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import GUI.PongGUI;
import Menus.snake.menuSnake;
import usuario.UsuarioSnake;

public class MenuDificultadPong extends JFrame{
	
	private static final long serialVersionUID = 1L;
	public static UsuarioSnake usuario;
	public MenuDificultadPong(UsuarioSnake usuario) {
		menuSnake.usuario = usuario;
		setTitle("PONG");
		setSize(360, 540);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
        setBackground(Color.BLACK);
		
		JLabel titulo = new JLabel("DIFICULTAD", SwingConstants.CENTER);
		titulo.setOpaque(true);
		titulo.setForeground(Color.WHITE);
		titulo.setBackground(Color.BLACK);
		titulo.setFont(new Font("Arial", Font.BOLD, 50));
		add(titulo, BorderLayout.NORTH);
		
		JPanel botones = new JPanel();
		botones.setLayout(new GridLayout(5,1));
		botones.setBackground(Color.BLACK);
		
		Font fontBotones = new Font("Arial", Font.BOLD, 30);
		
		JButton Facil = new JButton("FACIL");
		Facil.setFocusable(false);
		Facil.setBackground(Color.BLACK);
		Facil.setForeground(Color.WHITE);
		Facil.setFont(fontBotones);
		Facil.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new PongGUI(usuario, Dificultad.Facil);
				dispose();
			}
		});
		
		JButton Normal = new JButton("NORMAL");
		Normal.setFocusable(false);
		Normal.setBackground(Color.BLACK);
		Normal.setForeground(Color.WHITE);
		Normal.setFont(fontBotones);
		Normal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new PongGUI(usuario, Dificultad.Normal);
				dispose();
			}
		});
		
		JButton Dificil = new JButton("DIFICIL");
		Dificil.setFocusable(false);
		Dificil.setBackground(Color.BLACK);
		Dificil.setForeground(Color.WHITE);
		Dificil.setFont(fontBotones);
		Dificil.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new PongGUI(usuario, Dificultad.Dificil);
				dispose();
			}
		});
		
		JButton MuyDificil = new JButton("99% IMPOSIBLE");
		MuyDificil.setFocusable(false);
		MuyDificil.setBackground(Color.BLACK);
		MuyDificil.setForeground(Color.WHITE);
		MuyDificil.setFont(fontBotones);
		MuyDificil.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new PongGUI(usuario, Dificultad.Imposible);
				dispose();
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
				new MenuPong(usuario);
				dispose();
			}
		});
		
		botones.add(Facil);
		botones.add(Normal);
		botones.add(Dificil);
		botones.add(MuyDificil);
		botones.add(Volver);
		add(botones, BorderLayout.CENTER);
		setVisible(true);
	}
}