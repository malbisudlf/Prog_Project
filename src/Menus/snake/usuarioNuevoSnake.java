package Menus.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class usuarioNuevoSnake extends JFrame {
    private JTextField campoNombre;
    private JButton botonCrearUsuario;

    public usuarioNuevoSnake() {
        setTitle("Crear Nuevo Usuario");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        campoNombre = new JTextField(20);
        botonCrearUsuario = new JButton("Crear Usuario");

        botonCrearUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = campoNombre.getText().trim();
                if (!userName.isEmpty()) {
                    if (isUniqueUserName(userName)) {
                        saveNewUser(userName);
                        JOptionPane.showMessageDialog(usuarioNuevoSnake.this, "Usuario creado: " + userName);
                        new menuSnake(userName);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(usuarioNuevoSnake.this, "El nombre de usuario ya existe.");
                    }
                } else {
                    JOptionPane.showMessageDialog(usuarioNuevoSnake.this, "Por favor, ingresa un nombre.");
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Nombre del nuevo usuario:"), BorderLayout.NORTH);
        panel.add(campoNombre, BorderLayout.CENTER);
        panel.add(botonCrearUsuario, BorderLayout.SOUTH);

        add(panel);
        
        setVisible(true);
    }

    private boolean isUniqueUserName(String userName) {
        // Implementar lógica para verificar si el nombre de usuario ya existe en la base de datos
        // Aquí, simulado como un usuario único (debería estar en tu base de datos)
        return !userName.equalsIgnoreCase("Juan");  // Ejemplo de usuario ya existente
    }

    private void saveNewUser(String userName) {
        // Implementa aquí el guardado del nuevo usuario en la base de datos
    }
}

