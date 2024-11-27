package GUI;

import javax.swing.*;
//import Menus.snake.menuPausaSnake;
import Menus.snake.menuSnake;
import usuario.UsuarioSnake;

import java.awt.*;

public class SnakeGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private JButton botonPausa;
    public static UsuarioSnake usuario;

    public SnakeGUI(UsuarioSnake usuario) {
        
        SnakeGUI.usuario = usuario;
        
        setTitle("SNAKE");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setResizable(false);

        Font font = new Font("Arial", Font.BOLD, 25);
        
        // PANEL SUPERIOR (panelArriba) PARA SCORE Y BOTÓN "PAUSA"
        JPanel panelArriba = new JPanel();
        panelArriba.setBackground(Color.BLACK);
        panelArriba.setLayout(new BorderLayout());

        // BOTÓN "PAUSA" (WEST DEL PANEL SUPERIOR)
        /*
        botonPausa = new JButton("PAUSA");
        botonPausa.setFont(font);
        botonPausa.setFocusable(false);
        botonPausa.setForeground(Color.GREEN);
        botonPausa.setBackground(Color.BLACK);
        panelArriba.add(botonPausa, BorderLayout.WEST);
        */
        
        // USUARIO
        JLabel user = new JLabel();
        
        user.setText("Usuario: " + usuario.getNombre());
        user.setFont(font);
        user.setForeground(Color.GREEN);
        
        panelArriba.add(user, BorderLayout.EAST);
        
        // AÑADIR EL PANEL SUPERIOR AL JFrame (NORTE)
        add(panelArriba, BorderLayout.NORTH);
        
        // ÁREA DE JUEGO (PANEL CENTRAL)
        panelSnake snakeGame = new panelSnake(600, 600, this, usuario);  // Pasar la referencia
        snakeGame.setPreferredSize(new Dimension(600, 600));
        add(snakeGame, BorderLayout.CENTER);
        
        // PANEL INFERIOR (panelAbajo) PARA BOTÓN "VOLVER"
        /*
        JPanel panelAbajo = new JPanel();
        panelAbajo.setBackground(Color.BLACK);
        panelAbajo.setLayout(new BorderLayout());
        */

        // BOTÓN "VOLVER" (WEST DEL PANEL INFERIOR)
        /*
        JButton botonVolver = new JButton("VOLVER");
        botonVolver.setFont(font);
        botonVolver.setFocusable(false);
        botonVolver.setForeground(Color.GREEN);
        botonVolver.setBackground(Color.BLACK);
        panelAbajo.add(botonVolver, BorderLayout.WEST);
        */
        
        /*
        botonPausa.addActionListener(e -> {
            botonVolver.setEnabled(false);
            botonPausa.setEnabled(false);
            new menuPausaSnake(this);
        });

        botonVolver.addActionListener(e -> {
            new menuSnake(menuSnake.usuario);
            dispose();
        });
        */
        
        // AÑADIR EL PANEL INFERIOR AL JFrame (SUR)
        //add(panelAbajo, BorderLayout.SOUTH);    
        
        pack();
        setVisible(true);
        snakeGame.requestFocus();
    }

    //IAG (Herramienta: ChatGPT)
    //Para poder volver a pulsar los botones al reanudar la pausa
    public void enableButtons() {
        JButton botonVolver = (JButton) ((JPanel) getContentPane().getComponent(2)).getComponent(0);
        botonVolver.setEnabled(true);
        botonPausa.setEnabled(true);
    }
    
    public void goToMenu() {
        new menuSnake(SnakeGUI.usuario);
        dispose();
    }
}
