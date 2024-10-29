package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SnakeGUI extends JFrame{
	
	//MAIN PANEL (JFrame)
	// |
	//TITULO (JLabel) (Arriba centro)
	//PUNTAJE (JLabel) (Arriba derecha)
	//PLAY AREA (JPanel) (Centro)
	//BOTONES ABAJO (JPanel) (BorderLayout)
	// |
	//VOLVER Y PAUSA (JButton) (Respectivamente, Abajo izquierda, Abajo Derecha)

	public SnakeGUI() {
		
		setTitle("SNAKE");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		
		Font font = new Font("Arial", Font.BOLD, 20);
		
		//TITULO (JLabel) (Arriba centro)
		JLabel labelTitulo = new JLabel("SNAKE", SwingConstants.CENTER);
		labelTitulo.setFont(font);
		labelTitulo.setBackground(Color.BLACK);
		labelTitulo.setForeground(Color.GREEN);
		add(labelTitulo, BorderLayout.NORTH);
		
		
		//PLAY AREA (JPanel) (Centro)
		JPanel areaJuego = new JPanel();
		areaJuego.setBackground(Color.BLACK);
		areaJuego.setPreferredSize(new Dimension(400, 300));
		add(areaJuego, BorderLayout.CENTER);
		
		//BOTONES ABAJO (JPanel) (GridLayout)
		JPanel panelAbajo = new JPanel();
		panelAbajo.setBackground(Color.BLACK);
		panelAbajo.setLayout(new FlowLayout());
		//prueba
		
		//VOLVER Y PAUSA (JButton) (Respectivamente, Abajo izquierda, Abajo Derecha)
		
		JButton botonVolver = new JButton("VOLVER");
		botonVolver.setFont(font);
		botonVolver.setFocusable(false);
		botonVolver.setForeground(Color.GREEN);
		botonVolver.setBackground(Color.BLACK);
		panelAbajo.add(botonVolver);
		
		JButton botonPausa = new JButton("PAUSA");
		botonPausa.setFocusable(false);
		botonPausa.setForeground(Color.GREEN);
		botonPausa.setBackground(Color.BLACK);
        panelAbajo.add(botonPausa);
        
        JLabel labelScore = new JLabel("Score: 0");
        labelScore.setFont(new Font("Arial", Font.PLAIN, 16));
        labelScore.setForeground(Color.GREEN);
        panelAbajo.add(labelScore);
		
		add(panelAbajo, BorderLayout.SOUTH);	
	
		setVisible(true);
		
	}
}
