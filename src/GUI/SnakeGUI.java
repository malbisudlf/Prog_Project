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

import Menus.*;

public class SnakeGUI extends JFrame {

    public SnakeGUI() {
        
        setTitle("SNAKE");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        

        

        Font font = new Font("Arial", Font.BOLD, 20);
        
        // Panel Superior (panelArriba) para Título y Botón "VOLVER"
        JPanel panelArriba = new JPanel();
        panelArriba.setBackground(Color.BLACK);
        panelArriba.setLayout(new BorderLayout());

        // Botón "PAUSA" (Derecha del panel inferior)
        JButton botonPausa = new JButton("PAUSA");
        botonPausa.setFont(font);
        botonPausa.setFocusable(false);
        botonPausa.setForeground(Color.GREEN);
        botonPausa.setBackground(Color.BLACK);
        panelArriba.add(botonPausa, BorderLayout.WEST);

        botonPausa.addActionListener(e -> {
            new menuPausaSnake();
        });

        // Marcador de Puntaje (Centro del panel inferior)
        JLabel labelScore = new JLabel("Score: 0", SwingConstants.CENTER);
        labelScore.setFont(new Font("Arial", Font.BOLD, 25));
        labelScore.setForeground(Color.GREEN);
        panelArriba.add(labelScore, BorderLayout.EAST);
        
        
        // Añadir el panel superior al JFrame (NORTE)
        add(panelArriba, BorderLayout.NORTH);
        
        // Área de Juego (panel central)
        JPanel areaJuego = new JPanel();
        areaJuego.setBackground(Color.BLACK);
        areaJuego.setPreferredSize(new Dimension(400, 300));
        add(areaJuego, BorderLayout.CENTER);
        
        // Panel Inferior (panelAbajo) para Botón "PAUSA" y marcador de puntaje
        JPanel panelAbajo = new JPanel();
        panelAbajo.setBackground(Color.BLACK);
        panelAbajo.setLayout(new BorderLayout());

        

        // Botón "VOLVER" (Izquierda del panel superior)
        JButton botonVolver = new JButton("VOLVER");
        botonVolver.setFont(font);
        botonVolver.setFocusable(false);
        botonVolver.setForeground(Color.GREEN);
        botonVolver.setBackground(Color.BLACK);
        panelAbajo.add(botonVolver, BorderLayout.WEST);

        botonVolver.addActionListener(e -> {
            new menuSnake();
            dispose();
        });

        
        
        
        // Añadir el panel inferior al JFrame (SUR)
        add(panelAbajo, BorderLayout.SOUTH);    
        
        setVisible(true);
    }
}