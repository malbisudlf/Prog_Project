package Menus.snake;

import javax.swing.*;

import Menus.MainMenuGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class seleccionUsuarioSnake extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton usuarioExistente;
    private JButton usuarioNuevo;
    
    public seleccionUsuarioSnake() {
        setTitle("Seleccionar Usuario");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear botones
        usuarioExistente = new JButton("Usuario Existente");
        usuarioNuevo = new JButton("Nuevo Usuario");
        usuarioExistente.setFocusable(false);
        usuarioNuevo.setFocusable(false);
        
        usuarioExistente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantallaUsuarioExistente();
            }
        });
        
        usuarioNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantallaUsuarioNuevo();
            }
        });
        
        setLayout(new BorderLayout());
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(usuarioExistente);
        panelBotones.add(usuarioNuevo);

        // Añadir una etiqueta para dar instrucciones al usuario
        JLabel label = new JLabel("Selecciona tu tipo de usuario:");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        
        JButton botonVolver = new JButton("VOLVER");
        botonVolver.setFocusable(false);
        botonVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new MainMenuGUI();
				dispose();
			}
		});
        
        add(botonVolver, BorderLayout.SOUTH);
        
        setVisible(true);

    }
    
    private void pantallaUsuarioExistente() {
    	new usuarioExistenteSnake();
    	dispose();
    }
    
    private void pantallaUsuarioNuevo() {
    	new usuarioNuevoSnake();
    	dispose();
    }
}