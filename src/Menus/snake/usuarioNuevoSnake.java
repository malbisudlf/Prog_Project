package Menus.snake;

import javax.swing.*;
import BD.GestorBDSnake;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class usuarioNuevoSnake extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField campoNombre;
    private JButton botonCrearUsuario;
    private final GestorBDSnake gestorBD = new GestorBDSnake();  // Usamos el gestor de base de datos

    public usuarioNuevoSnake() {  
        setTitle("Crear Nuevo Usuario");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // TEXTFIELD DONDE ESCRIBIR USUARIO NUEVO
        campoNombre = new JTextField(20);
        campoNombre.setBackground(Color.BLACK);
        campoNombre.setForeground(Color.WHITE);
        campoNombre.setCaretColor(Color.WHITE);
        
        // BOTON PARA CREAR EL USUARIO
        botonCrearUsuario = new JButton("Crear Usuario");
        botonCrearUsuario.setBackground(Color.BLACK);
        botonCrearUsuario.setForeground(Color.WHITE);

        botonCrearUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = campoNombre.getText().trim();
                if (!userName.isEmpty()) {
                    if (isUniqueUserName(userName)) {
                        saveNewUser(userName);
                        new menuSnake(userName); // Crear el juego para este usuario
                        dispose();
                    } else { // Si el nombre ya existe, muestra un mensaje de error
                        JOptionPane.showMessageDialog(usuarioNuevoSnake.this, "El nombre de usuario ya existe.");
                    }
                } else { // Si no se escribe nada, muestra un mensaje de error
                    JOptionPane.showMessageDialog(usuarioNuevoSnake.this, "Por favor, ingresa un nombre.");
                }
            }
        });

        // PANEL PRINCIPAL
        JPanel panel = new JPanel(new BorderLayout());
        
        // PANEL DE ARRIBA
        JPanel panelArriba = new JPanel(new BorderLayout());
        panelArriba.setBackground(Color.BLACK);
        panelArriba.setForeground(Color.WHITE);
        
        // BOTON DE VOLVER
        JButton botonVolver = new JButton("VOLVER");
        botonVolver.setBackground(Color.BLACK);
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setFocusable(false);
        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Volver al menú anterior
                new seleccionUsuarioSnake();
                dispose();
            }
        });
       
        panelArriba.add(botonVolver, BorderLayout.EAST);
        
        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(Color.BLACK);
        
        JLabel label = new JLabel("Nombre usuario nuevo:");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);
        
        panelLabel.add(label);
        
        panelArriba.add(panelLabel, BorderLayout.WEST);
        campoNombre.setFocusable(true);
        
        panel.add(panelArriba, BorderLayout.NORTH);
        panel.add(campoNombre, BorderLayout.CENTER);
        panel.add(botonCrearUsuario, BorderLayout.SOUTH);

        add(panel);
        
        setVisible(true);
    }

    // Verificar si el nombre de usuario es único
    private boolean isUniqueUserName(String userName) {
        return gestorBD.getUserByName(userName) == null;  // Verifica si ya existe un usuario con ese nombre
    }

    // Guardar el nuevo usuario en la base de datos
    private void saveNewUser(String userName) {
        // Crear un nuevo usuario con puntuaciones inicializadas a cero
        if (gestorBD.addUser(userName)) {
            gestorBD.saveToCSV();  // Actualizar el CSV después de agregar el usuario
            JOptionPane.showMessageDialog(this, "Usuario creado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el usuario en la base de datos.");
        }
    }
}
