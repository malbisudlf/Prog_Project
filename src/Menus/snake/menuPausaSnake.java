package Menus.snake;

import javax.swing.*;

import GUI.SnakeGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuPausaSnake extends JFrame{

    //BOTON REANUDAR (JButton)
    //BOTON REINICIAR (JButton)
    
	private static final long serialVersionUID = 1L;
	
	 public static SnakeGUI snakeGUI;

	// CONSTRUCTOR DEL MENÚ DE PAUSA
	public menuPausaSnake(SnakeGUI snakeGUI){

		menuPausaSnake.snakeGUI = snakeGUI;
		
        // CONFIGURACIÓN DE LA VENTANA PRINCIPAL DEL MENÚ DE PAUSA
        setTitle("SNAKE MENU PAUSA");
		setSize(300, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

        // CONFIGURACIÓN DE LAYOUT PARA LOS BOTONES
        setLayout(new GridLayout(2, 1, 2, 2));

        // BOTÓN REANUDAR (JButton)
        JButton botonReanudar = new JButton("REANUDAR");
        // BOTÓN REINICIAR (JButton)
        JButton botonReiniciar = new JButton("REINICIAR");
        
        // DESACTIVAR FOCUS PARA LOS BOTONES
        botonReanudar.setFocusable(false);
		botonReiniciar.setFocusable(false);
		
		// CONFIGURACIÓN DE LOS COLORES DE LOS BOTONES
        botonReanudar.setBackground(Color.BLACK);
        botonReiniciar.setBackground(Color.BLACK);

        botonReanudar.setForeground(Color.WHITE);
        botonReiniciar.setForeground(Color.WHITE);

        // CONFIGURACIÓN DE LA FUENTE DE LOS BOTONES
        Font font = new Font("ARIAL", Font.BOLD, 25);
        botonReanudar.setFont(font);
        botonReiniciar.setFont(font);

        // AÑADIR LOS BOTONES AL PANEL
        add(botonReanudar);
        add(botonReiniciar);

        // ACCIÓN DEL BOTÓN REANUDAR
        botonReanudar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // REANUDAR EL JUEGO
            	snakeGUI.enableButtons();
                dispose();
            }
            
        });

        // ACCIÓN DEL BOTÓN REINICIAR
        botonReiniciar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // LLAMADA AL MÉTODO PARA REINICIAR EL JUEGO
                reiniciarJuego();
            	// Cuando el juego esté implementado, modificar el método reiniciarJuego() para reiniciar la serpiente, contadores, etc.
            }
            
        });

        // HACER VISIBLE EL MENÚ DE PAUSA
        setVisible(true);
    }
    
    // MÉTODO PARA REINICIAR EL JUEGO (AUN NO IMPLEMENTADO)
    private void reiniciarJuego() {
    	JOptionPane.showMessageDialog(this, "Metodo reiniciarJuego() aun sin implementar");
    }
    
}
