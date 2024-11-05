package Menus.snake;

import javax.swing.*;

import Menus.MainMenuGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class seleccionUsuarioSnake extends JFrame{
	
	
	private static final long serialVersionUID = 1L;
	private JButton usuarioExistente;
    private JButton usuarioNuevo;
    
    public seleccionUsuarioSnake() {
        setTitle("Seleccionar Usuario");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.BLACK);
        
        
        // Crear botones
        usuarioExistente = new JButton("Usuario Existente");
        usuarioExistente.setBackground(Color.BLACK);
        usuarioExistente.setForeground(Color.WHITE);
        
        usuarioNuevo = new JButton("Nuevo Usuario");
        usuarioNuevo.setBackground(Color.BLACK);
        usuarioNuevo.setForeground(Color.WHITE);
        
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
        panelBotones.setBackground(Color.BLACK);
        
        ImageIcon userIcon = new ImageIcon("resources/images/usuarioSnakelogin.png");
        
     // Escalar la imagen
        Image img = userIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Escala a 50x50 píxeles
        userIcon = new ImageIcon(img);
        
        JLabel userIconLabel = new JLabel(userIcon);

        panelBotones.add(userIconLabel);
        panelBotones.add(usuarioExistente);
        panelBotones.add(usuarioNuevo);
        
        // Añadir una etiqueta para dar instrucciones al usuario
        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(Color.BLACK);
        
        
        
        JLabel label = new JLabel("Selecciona tu tipo de usuario:");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);
        
        labelPanel.add(label); // Añadir el JLabel al panel
        
        add(labelPanel, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        
        JButton botonVolver = new JButton("VOLVER");
        botonVolver.setFocusable(false);
        botonVolver.setBackground(Color.BLACK);
        botonVolver.setForeground(Color.WHITE);
        
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