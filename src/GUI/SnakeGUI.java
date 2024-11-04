package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Menus.snake.menuPausaSnake;
import Menus.snake.menuSnake;


public class SnakeGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton botonPausa;

	public SnakeGUI() {
        
        setTitle("SNAKE");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setResizable(false);

        Font font = new Font("Arial", Font.BOLD, 20);
        
        // Panel Superior (panelArriba) para Score y Botón "PAUSA"
        JPanel panelArriba = new JPanel();
        panelArriba.setBackground(Color.BLACK);
        panelArriba.setLayout(new BorderLayout());

        // Botón "PAUSA" (West del panel superior)
        botonPausa = new JButton("PAUSA");
        botonPausa.setFont(font);
        botonPausa.setFocusable(false);
        botonPausa.setForeground(Color.GREEN);
        botonPausa.setBackground(Color.BLACK);
        panelArriba.add(botonPausa, BorderLayout.WEST);

        

        // Marcador de Puntaje (EAST del panel Superior)
        JLabel labelScore = new JLabel("Score: 0", SwingConstants.CENTER);
        labelScore.setFont(new Font("Arial", Font.BOLD, 25));
        labelScore.setForeground(Color.GREEN);
        panelArriba.add(labelScore, BorderLayout.EAST);
        
        
        // Añadir el panel superior al JFrame (NORTE)
        add(panelArriba, BorderLayout.NORTH);
        
        // Área de Juego (panel central)
        
        panelSnake snakeGame = new panelSnake(600, 600);
        snakeGame.setPreferredSize(new Dimension(600, 600));
        add(snakeGame, BorderLayout.CENTER);
        
        // Panel Inferior (panelAbajo) para Botón "VOLVER"
        JPanel panelAbajo = new JPanel();
        panelAbajo.setBackground(Color.BLACK);
        panelAbajo.setLayout(new BorderLayout());

        

        // Botón "VOLVER" (WEST del panel inferior)
        JButton botonVolver = new JButton("VOLVER");
        botonVolver.setFont(font);
        botonVolver.setFocusable(false);
        botonVolver.setForeground(Color.GREEN);
        botonVolver.setBackground(Color.BLACK);
        panelAbajo.add(botonVolver, BorderLayout.WEST);
        
        botonPausa.addActionListener(e -> {
        	botonVolver.setEnabled(false);
        	botonPausa.setEnabled(false);
            new menuPausaSnake(this);
        });

        botonVolver.addActionListener(e -> {
            new menuSnake(menuSnake.userName);
            dispose();
        });
        
        // Añadir el panel inferior al JFrame (SUR)
        add(panelAbajo, BorderLayout.SOUTH);    
        
        pack();
        setVisible(true);
        snakeGame.requestFocus();
    }
	//IAG (Herramienta: ChatGPT)
	//SIN MODIFICAR
	public void enableButtons() {
        JButton botonVolver = (JButton) ((JPanel) getContentPane().getComponent(2)).getComponent(0);
        botonVolver.setEnabled(true);
        botonPausa.setEnabled(true);
    }
}