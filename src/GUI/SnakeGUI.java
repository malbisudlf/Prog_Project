package GUI;

import javax.swing.*;
import Menus.snake.menuPausaSnake;
import Menus.snake.menuSnake;
import java.awt.*;

public class SnakeGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private JButton botonPausa;
    public static String userName;

    public SnakeGUI(String userName) {
        
        SnakeGUI.userName = userName;
        
        setTitle("SNAKE");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setResizable(false);

        Font font = new Font("Arial", Font.BOLD, 25);
        
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
        
        // USUARIO
        JLabel user = new JLabel();
        
        user.setText("Usuario: " + userName);
        user.setFont(font);
        user.setForeground(Color.GREEN);
        
        
        panelArriba.add(user, BorderLayout.EAST);
        
        // Añadir el panel superior al JFrame (NORTE)
        add(panelArriba, BorderLayout.NORTH);
        
        // Área de Juego (panel central)
        panelSnake snakeGame = new panelSnake(600, 600, this);  // Pasar la referencia
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

    public void enableButtons() {
        JButton botonVolver = (JButton) ((JPanel) getContentPane().getComponent(2)).getComponent(0);
        botonVolver.setEnabled(true);
        botonPausa.setEnabled(true);
    }
    
    public void goToMenu() {
        new menuSnake(SnakeGUI.userName);
        dispose();
    }
}
