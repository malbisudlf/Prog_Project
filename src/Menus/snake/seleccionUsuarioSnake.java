package Menus.snake;

import javax.swing.*;
import Menus.MainMenuGUI;
import usuario.UsuarioSnake;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class seleccionUsuarioSnake extends JFrame{
    
    private static final long serialVersionUID = 1L;
    private JButton usuarioExistente;
    private JButton usuarioNuevo;
    public static UsuarioSnake usuario;
    public seleccionUsuarioSnake() {
    	menuSnake.usuario = usuario;
        // CONFIGURACIÓN DE LA VENTANA PRINCIPAL
        setTitle("Seleccionar Usuario");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.BLACK);
        
        // CREAR BOTONES PARA LA SELECCIÓN DE USUARIO
        usuarioExistente = new JButton("Usuario Existente");
        usuarioExistente.setBackground(Color.BLACK);
        usuarioExistente.setForeground(Color.WHITE);
        
        usuarioNuevo = new JButton("Nuevo Usuario");
        usuarioNuevo.setBackground(Color.BLACK);
        usuarioNuevo.setForeground(Color.WHITE);
        
        // EVITAR QUE LOS BOTONES SE PUEDAN FOCUSAR
        usuarioExistente.setFocusable(false);
        usuarioNuevo.setFocusable(false);
        
        // ACCIÓN AL HACER CLIC EN EL BOTÓN DE USUARIO EXISTENTE
        usuarioExistente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantallaUsuarioExistente();
            }
        });
        
        // ACCIÓN AL HACER CLIC EN EL BOTÓN DE NUEVO USUARIO
        usuarioNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantallaUsuarioNuevo();
            }
        });
        
        // CONFIGURACIÓN DEL LAYOUT PRINCIPAL
        setLayout(new BorderLayout());
        
        // PANEL DE LOS BOTONES
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.BLACK);
        
        // CARGAR Y ESCALAR EL ÍCONO DE USUARIO
        ImageIcon userIcon = new ImageIcon("resources/images/usuarioSnakelogin.png");
        // ESCALAR LA IMAGEN A UN TAMAÑO DE 100x100
        Image img = userIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Escala a 100x100 píxeles
        userIcon = new ImageIcon(img);
        
        JLabel userIconLabel = new JLabel(userIcon);
        
        // AÑADIR EL ÍCONO Y LOS BOTONES AL PANEL
        panelBotones.add(userIconLabel);
        panelBotones.add(usuarioExistente);
        panelBotones.add(usuarioNuevo);
        
        // PANEL PARA EL LABEL DE INSTRUCCIONES
        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(Color.BLACK);        
        
        // LABEL CON LA INSTRUCCIÓN PARA EL USUARIO
        JLabel label = new JLabel("Selecciona tu tipo de usuario:");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);
        
        // AÑADIR EL LABEL AL PANEL
        labelPanel.add(label); // Añadir el JLabel al JPanel
        
        // AÑADIR LOS COMPONENTES AL FRAME
        add(labelPanel, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);

        // HACER VISIBLE EL FRAME
        setVisible(true);
    }
    
    // ACCIÓN AL SELECCIONAR UN USUARIO EXISTENTE
    private void pantallaUsuarioExistente() {
        new usuarioExistente();
        dispose();
    }
    
    // ACCIÓN AL CREAR UN NUEVO USUARIO
    private void pantallaUsuarioNuevo() {
        new usuarioNuevo();
        dispose();
    }
}
