package Menus.snake;

import javax.swing.*;

import BD.GestorBDSnake;
import GUI.*;
import Menus.MainMenuGUI;
import usuario.UsuarioSnake;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuSnake extends JFrame{

    private static final long serialVersionUID = 1L;
    
    public static UsuarioSnake usuario;
    
    public menuSnake(UsuarioSnake usuario) {
        
    	if (usuario == null) {
            throw new IllegalArgumentException("UsuarioSnake cannot be null");
        }
        menuSnake.usuario = usuario;
        
        // CONFIGURACIÓN DE LA VENTANA PRINCIPAL
        setTitle("SNAKE MENU");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(500, 100);
		//setLocationRelativeTo(null);
        setResizable(false);
        setBackground(Color.BLACK);
        
        // PANEL PRINCIPAL
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        
        // LABEL CON EL TÍTULO Y NOMBRE DE USUARIO
        JLabel titulo = new JLabel("			SNAKE    Usuario: " + usuario.getNombre(), SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setBackground(Color.BLACK);
        titulo.setForeground(Color.WHITE);
        
        mainPanel.add(titulo, BorderLayout.NORTH);
        
        // PANEL CON LOS BOTONES
        JPanel botones = new JPanel();
        botones.setLayout(new GridLayout(2, 2, 10, 10)); // JUGAR, LEADERBOARD, SKINS, VOLVER
        botones.setBackground(Color.BLACK);
        
        // Botón para empezar a jugar
        JButton botonJugar = buttonWithIcon("resources/images/row-1-column-1.png", "JUGAR");
        
        botonJugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestorBDSnake gestorBD = new GestorBDSnake();
                UsuarioSnake usuarioActualizado = gestorBD.getUserByName(usuario.getNombre());
                
                if (usuarioActualizado != null) {
                    menuSnake.usuario = usuarioActualizado; // Actualizar usuario
                    new SnakeGUI(menuSnake.usuario); // Crear nueva partida con el usuario actualizado
                    dispose(); // Cerrar el menú actual
                } else {
                    JOptionPane.showMessageDialog(null, "Error al cargar el usuario actualizado.");
                }
            }
        });

        
        // Botón para consultar el leaderboard
        JButton botonLeaderboard = buttonWithIcon("resources/images/row-1-column-2.png", "LEADERBOARD");
        botonLeaderboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // MUESTRA EL LEADERBOARD (AUN NO IMPLEMENTADO)
                showLeaderboard();
            }
        });
        
        // Botón para acceder a la tienda de skins
        JButton botonSkins = buttonWithIcon("resources/images/row-2-column-1.png", "SKINS");
        botonSkins.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // MUESTRA LA TIENDA DE SKINS (AUN NO IMPLEMENTADO)
                showSkins();
            }
        });
        
        // Botón para volver al menú principal
        JButton botonVolverMenu = buttonWithIcon("resources/images/row-2-column-2.png", "VOLVER");
        botonVolverMenu.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // VUELVE AL MENÚ PRINCIPAL
                new MainMenuGUI(usuario);
                dispose();
            }
        });
        
        // AÑADIR LOS BOTONES AL PANEL
        botones.add(botonJugar);
        botones.add(botonLeaderboard);
        botones.add(botonSkins);
        botones.add(botonVolverMenu);
        
        mainPanel.add(botones, BorderLayout.CENTER);
        
        add(mainPanel);
        
        setVisible(true);
    }
    
    // MÉTODO PARA MOSTRAR EL LEADERBOARD (AUN NO IMPLEMENTADO)
    private void showLeaderboard() {
        new SnakeLeaderboard(usuario);
        dispose();
    }
    
    // MÉTODO PARA MOSTRAR LA TIENDA DE SKINS (AUN NO IMPLEMENTADO)
    private void showSkins() {
        JOptionPane.showMessageDialog(this, "Skins aún no implementado");
    }
    
    // MÉTODO PARA CREAR UN BOTÓN CON UNA IMAGEN DE FONDO
    
    //IAG (Herramienta: ChatGPT)
    //Modificado
    //Idea de ChatGPT, muy simplificado por nosotros para que funcione como queremos
    private JButton buttonWithIcon(String imagePath, String text) {
        
        JButton button = new JButton(text);
        
        // ESTABLECE UNA IMAGEN DE FONDO PARA EL BOTÓN
        button.setIcon(new ImageIcon(imagePath));
        
        // CONFIGURA LA POSICIÓN DEL TEXTO EN EL BOTÓN
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        
        // CONFIGURACIÓN DEL TEXTO Y COLOR DEL BOTÓN
        button.setFont(new Font("MV BOLI", Font.BOLD, 30));
        button.setForeground(Color.WHITE);
        
        // EVITAR QUE EL BOTÓN PUEDA SER FOCUSADO
        button.setFocusable(false);
        // ACTIVAR EL DIBUJO DE BORDES
        button.setBorderPainted(true);
        
        return button;
    }
}
