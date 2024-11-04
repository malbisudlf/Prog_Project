package Menus.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class usuarioNuevoSnake extends JFrame {
    
	private static final long serialVersionUID = 1L;
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
        
        JPanel panelArriba = new JPanel(new BorderLayout());
        JButton botonVolver = new JButton("VOLVER");
        botonVolver.setFocusable(false);
        botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new seleccionUsuarioSnake();
				dispose();
			}
		});
       
        panelArriba.add(botonVolver, BorderLayout.EAST);
        panelArriba.add(new JLabel("Nombre usuario nuevo:"), BorderLayout.WEST);
        campoNombre.setFocusable(true);
        
        panel.add(panelArriba, BorderLayout.NORTH);
        panel.add(campoNombre, BorderLayout.CENTER);
        panel.add(botonCrearUsuario, BorderLayout.SOUTH);

        add(panel);
        
        setVisible(true);
    }

    private boolean isUniqueUserName(String userName) {
        // Luego implementarlo para la base de datos
        // De momento simulo con un ejemplo
        return !userName.equalsIgnoreCase("Messillas");
    }

    private void saveNewUser(String userName) {
        // Implementa aquí el guardado del nuevo usuario en la base de datos
    }
}

