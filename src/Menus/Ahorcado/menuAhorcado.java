package Menus.Ahorcado;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import GUI.AhorcadoGUI;
import Menus.MainMenuGUI;
import Menus.snake.menuSnake;
import db.GestorBD;
import usuario.UsuarioSnake;

public class menuAhorcado extends JFrame {
    private static final long serialVersionUID = 1L;
    public static UsuarioSnake usuario;

    public menuAhorcado(UsuarioSnake usuario) {
        menuSnake.usuario = usuario;
        setTitle("MENU AHORCADO");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setBackground(Color.BLACK);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        JLabel titulo = new JLabel("AHORCADO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 50));
        mainPanel.add(titulo, BorderLayout.NORTH);
        titulo.setBackground(Color.BLACK);
        titulo.setForeground(Color.WHITE);

        Font fontBotones = new Font("MV BOLI", Font.BOLD, 30);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.setBackground(Color.BLACK);

        // Botón para jugar
        JButton playButton = new JButton("JUGAR");
        playButton.setFocusable(false);
        playButton.setBackground(Color.BLACK);
        playButton.setForeground(Color.WHITE);
        playButton.setFont(fontBotones);
        playButton.addActionListener(e -> {
            new AhorcadoGUI(usuario);
            dispose();
        });

        // Botón para leaderboard
        JButton leaderButton = new JButton("LEADERBOARD");
        leaderButton.setFocusable(false);
        leaderButton.setBackground(Color.BLACK);
        leaderButton.setForeground(Color.WHITE);
        leaderButton.setFont(fontBotones);
        leaderButton.addActionListener(e -> {
            new AhorcadoLeaderboard(usuario); // Ahora se utiliza directamente la clase combinada
            dispose();
        });

       

        // Botón para volver al menú principal
        JButton volverButton = new JButton();
        volverButton.setFocusable(false);
        volverButton.setBackground(Color.BLACK);
        volverButton.setForeground(Color.WHITE);
        volverButton.setFont(fontBotones);

        ImageIcon volverFoto = new ImageIcon("resources/images/volver.png");
        volverButton.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int anchoBoton = volverButton.getWidth();
                int altoBoton = volverButton.getHeight();

                Image imagenEscalada = volverFoto.getImage().getScaledInstance(anchoBoton - 25, altoBoton - 5, Image.SCALE_SMOOTH);
                volverButton.setIcon(new ImageIcon(imagenEscalada));
            }
        });
        volverButton.setIcon(volverFoto);
        volverButton.addActionListener(e -> {
            new MainMenuGUI(usuario);
            dispose();
        });

        // Añadir botones al panel
        buttonPanel.add(playButton);
        buttonPanel.add(leaderButton);
        
        buttonPanel.add(volverButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    
}
