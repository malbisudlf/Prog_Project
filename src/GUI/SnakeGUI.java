package GUI;

import javax.swing.*;
//import Menus.snake.menuPausaSnake;
import Menus.snake.menuSnake;
import usuario.UsuarioSnake;

import java.awt.*;

public class SnakeGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    
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
        
        pack();
        setVisible(true);
        snakeGame.requestFocus();
    }
    
    public void goToMenu() {
        new menuSnake(SnakeGUI.usuario);
        dispose();
    }
}