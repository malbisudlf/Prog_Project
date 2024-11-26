package Menus.snake;

import javax.swing.*;
import BD.GestorBDSnake;
import usuario.UsuarioSnake;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class usuarioNuevoSnake extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField campoNombre;
    private JButton botonCrearUsuario;
    private final GestorBDSnake gestorBD = new GestorBDSnake();  // USAMOS EL GESTOR DE BASE DE DATOS

    public usuarioNuevoSnake() {  
        // CONFIGURAMOS LA VENTANA PRINCIPAL
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

        // ACCION AL HACER CLIC EN EL BOTON PARA CREAR EL USUARIO
        botonCrearUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // OBTENEMOS EL NOMBRE INGRESADO POR EL USUARIO
                String userName = campoNombre.getText().trim();
                
                // VERIFICAMOS QUE EL NOMBRE NO ESTE VACIO
                if (!userName.isEmpty()) {
                    // VERIFICAMOS SI EL NOMBRE DE USUARIO ES UNICO
                    if (isUniqueUserName(userName)) {
                    	
                        saveNewUser(userName);  // GUARDAMOS EL NUEVO USUARIO
                        
                        // OBTENEMOS EL USUARIO CREADO DESDE LA BASE DE DATOS
                        UsuarioSnake nuevoUsuario = gestorBD.getUserByName(userName);
                        
                        new menuSnake(nuevoUsuario); // CREAMOS EL JUEGO PARA ESTE USUARIO
                        dispose(); // CERRAMOS LA VENTANA ACTUAL
                    } else { 
                        // SI EL NOMBRE YA EXISTE, MOSTRAMOS UN MENSAJE DE ERROR
                        JOptionPane.showMessageDialog(usuarioNuevoSnake.this, "El nombre de usuario ya existe.");
                    }
                } else { 
                    // SI EL NOMBRE ESTA VACIO, MOSTRAMOS UN MENSAJE DE ERROR
                    JOptionPane.showMessageDialog(usuarioNuevoSnake.this, "Por favor, ingresa un nombre.");
                }
            }
        });

        // PANEL PRINCIPAL
        JPanel panel = new JPanel(new BorderLayout());
        
        // PANEL DE ARRIBA (PARA LOS CONTROLES SUPERIORES)
        JPanel panelArriba = new JPanel(new BorderLayout());
        panelArriba.setBackground(Color.BLACK);
        panelArriba.setForeground(Color.WHITE);
        
        // BOTON DE VOLVER (PARA REGRESAR AL MENÚ ANTERIOR)
        JButton botonVolver = new JButton("VOLVER");
        botonVolver.setBackground(Color.BLACK);
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setFocusable(false);
        
        // ACCION AL HACER CLIC EN EL BOTON DE VOLVER
        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // VOLVEMOS AL MENÚ ANTERIOR
                new seleccionUsuarioSnake();
                dispose(); // CERRAMOS LA VENTANA ACTUAL
            }
        });
       
        // AÑADIMOS EL BOTON DE VOLVER AL PANEL DE ARRIBA
        panelArriba.add(botonVolver, BorderLayout.EAST);
        
        // PANEL QUE CONTIENE EL LABEL
        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(Color.BLACK);
        
        // LABEL QUE PIDE EL NOMBRE DEL NUEVO USUARIO
        JLabel label = new JLabel("Nombre usuario nuevo:");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);
        
        // AÑADIMOS EL LABEL AL PANEL LABEL
        panelLabel.add(label);
        
        // AÑADIMOS EL PANEL DE LABEL AL PANEL DE ARRIBA
        panelArriba.add(panelLabel, BorderLayout.WEST);
        
        // HABILITAMOS EL CAMPO DE TEXTO PARA ENFOCARLO
        campoNombre.setFocusable(true);
        
        // AÑADIMOS LOS COMPONENTES AL PANEL PRINCIPAL
        panel.add(panelArriba, BorderLayout.NORTH);
        panel.add(campoNombre, BorderLayout.CENTER);
        panel.add(botonCrearUsuario, BorderLayout.SOUTH);

        // AÑADIMOS EL PANEL AL FRAME
        add(panel);
        
        // HACEMOS VISIBLE LA VENTANA
        setVisible(true);
    }

    // VERIFICAR SI EL NOMBRE DE USUARIO ES ÚNICO
    private boolean isUniqueUserName(String userName) {
        return gestorBD.getUserByName(userName) == null;  // VERIFICAMOS SI YA EXISTE UN USUARIO CON ESE NOMBRE
    }

    // GUARDAR EL NUEVO USUARIO EN LA BASE DE DATOS
    private void saveNewUser(String userName) {
        // CREAR UN NUEVO USUARIO CON PUNTUACIONES INICIALIZADAS A CERO
        if (gestorBD.addUser(userName)) {
            // ACTUALIZAR EL CSV DESPUES DE AGREGAR EL USUARIO
            gestorBD.saveToCSV();  
            JOptionPane.showMessageDialog(this, "Usuario creado exitosamente.");
        } else {
            // SI HAY UN ERROR AL GUARDAR EL USUARIO EN LA BASE DE DATOS, MOSTRAMOS UN MENSAJE DE ERROR
            JOptionPane.showMessageDialog(this, "Error al guardar el usuario en la base de datos.");
        }
    }
}
