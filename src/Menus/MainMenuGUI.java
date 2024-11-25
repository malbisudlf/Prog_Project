package Menus;

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

import Menus.Ahorcado.menuAhorcado;
import Menus.pong.MenuPong;

import Menus.snake.seleccionUsuarioSnake;

public class MainMenuGUI extends JFrame{

	private static final long serialVersionUID = 1L;

	public MainMenuGUI(){
		
		// CONFIGURAR LA VENTANA PRINCIPAL DEL MENÚ
		setTitle("MAIN MENU");
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(3, 1));
		//setResizable(false);
		
		// CREAR LOS BOTONES
		JButton botonAhorcado = new JButton();
		JButton botonPong = new JButton();
		JButton botonSnake = new JButton();
		
		// QUITAR EL FOCUS DE LOS BOTONES
		botonAhorcado.setFocusable(false);
		botonPong.setFocusable(false);
		botonSnake.setFocusable(false);
		
		// CONFIGURAR LA FUENTE DE LOS BOTONES
		Font fontPortada = new Font("MV BOLI", Font.BOLD, 50);
		botonAhorcado.setFont(fontPortada);
		botonPong.setFont(fontPortada);
		botonSnake.setFont(fontPortada);
		
		// CONFIGURAR EL FONDO Y COLOR DEL TEXTO DE LOS BOTONES
		botonAhorcado.setBackground(Color.black);
		botonPong.setBackground(Color.black);
		botonSnake.setBackground(Color.black);
		
		botonAhorcado.setForeground(Color.green);
		botonPong.setForeground(Color.green);
		botonSnake.setForeground(Color.green);	

		// AGREGAR ICONOS A LOS BOTONES
		ImageIcon portadaAhorcado = new ImageIcon("resources/images/portadaHangman.png");
		botonAhorcado.setIcon(portadaAhorcado);
		
		ImageIcon portadaPong = new ImageIcon("resources/images/portadaPongClasico.png");
		botonPong.setIcon(portadaPong);
		
		ImageIcon portadaSnake = new ImageIcon("resources/images/portadaSnakeVerde.png");
		botonSnake.setIcon(portadaSnake);
		
		//IAG (herramienta: ChatGPT)
		//ADAPTADO (Codigo de ChatGpt adaptado a nuestro proyecto, cogemos la idea de chatgpt
		//y la trasladamos con algun cambio para que se adapte a nuestra idea.
		
		//Nuestra idea, que la imagen fuera del tamaño del boton, incluso cambiando el tamaño.
		
		// Agregar un ComponentListener para detectar cambios de tamaño
        botonAhorcado.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Obtener el tamaño actual del botón
                int anchoBoton = botonAhorcado.getWidth();
                int altoBoton = botonAhorcado.getHeight();

                // Redimensionar la imagen para ajustarse al tamaño del botón
                Image imagenEscalada = portadaAhorcado.getImage().getScaledInstance(anchoBoton, altoBoton, Image.SCALE_SMOOTH); //ChatGPT
                botonAhorcado.setIcon(new ImageIcon(imagenEscalada));
            }
        });
        
        //IAG, lo mismo que antes
        botonPong.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Obtener el tamaño actual del botón
                int anchoBoton = botonPong.getWidth();
                int altoBoton = botonPong.getHeight();

                // Redimensionar la imagen para ajustarse al tamaño del botón
                Image imagenEscalada = portadaPong.getImage().getScaledInstance(anchoBoton, altoBoton, Image.SCALE_SMOOTH);
                botonPong.setIcon(new ImageIcon(imagenEscalada));
            }
        });
        
        //IAG, lo mismo que antes
        botonSnake.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Obtener el tamaño actual del botón
                int anchoBoton = botonSnake.getWidth();
                int altoBoton = botonSnake.getHeight();

                // Redimensionar la imagen para ajustarse al tamaño del botón
                Image imagenEscalada = portadaSnake.getImage().getScaledInstance(anchoBoton, altoBoton, Image.SCALE_SMOOTH);
                botonSnake.setIcon(new ImageIcon(imagenEscalada));
            }
        });
        
        // AÑADIR LOS BOTONES AL LAYOUT
		add(botonAhorcado);
		add(botonPong);
		add(botonSnake);
		
		// ACCIÓN DEL BOTÓN AHORCADO
		botonAhorcado.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 new menuAhorcado();
				 dispose();
			}
		});
		
		// ACCIÓN DEL BOTÓN PONG
		botonPong.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 new MenuPong();
				 dispose();
			}
		});
		
		// ACCIÓN DEL BOTÓN SNAKE
		botonSnake.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 new seleccionUsuarioSnake();
				 dispose();
			}
		});
			
		// HACER VISIBLE EL MENÚ PRINCIPAL
		setVisible(true);
	}
}
