package Menus.snake;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Menus.MainMenuGUI;
import db.GestorBD;
import usuario.UsuarioSnake;

public class usuarioExistente extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private JTextField searchField;
    private JList<String> userList;
    private DefaultListModel<String> listModel;
    private List<String> allUsers;
    private final GestorBD gestorBD = new GestorBD();  // INSTANCIA DE GESTORBDSNAKE
    
    public usuarioExistente() {
        // CONFIGURACIÓN DE LA VENTANA PRINCIPAL
        setTitle("Seleccionar Usuario Existente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // INICIALIZA LA LISTA DE USUARIOS (CON BD SE OBTENDRÁ DE AHÍ) 
        allUsers = getUsersBd();
        listModel = new DefaultListModel<>();
        allUsers.forEach(listModel::addElement);
        
        // CREACIÓN DE LA LISTA DE USUARIOS
        userList = new JList<>(listModel);
        userList.setBackground(Color.BLACK);
        userList.setForeground(Color.WHITE);
        userList.setSelectionBackground(Color.DARK_GRAY);
        userList.setSelectionForeground(Color.WHITE);
        
        // ÁREA DE BÚSQUEDA (JTEXTFIELD)
        searchField = new JTextField(20);
        searchField.setBackground(Color.BLACK);
        searchField.setForeground(Color.WHITE);
        searchField.setCaretColor(Color.WHITE);
        
        // FILTRO EN TIEMPO REAL
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarUserList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarUserList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarUserList();
            }
        });
        
        // BOTÓN SELECCIONAR (JBUTTON)
        JButton selectButton = new JButton("Seleccionar");
        selectButton.setFocusable(false);
        selectButton.setBackground(Color.BLACK);
        selectButton.setForeground(Color.WHITE);
        selectButton.addActionListener(e -> selectUser());
        
        // PANEL PRINCIPAL
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        
        // PANEL DE ARRIBA
        JPanel panelArriba = new JPanel(new BorderLayout());
        panelArriba.setBackground(Color.BLACK);
        
        // BOTÓN VOLVER
        JButton botonVolver = new JButton("VOLVER");
        botonVolver.setBackground(Color.BLACK);
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setFocusable(false);
        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new seleccionUsuarioSnake();
                dispose();
            }
        });
        panelArriba.add(botonVolver, BorderLayout.EAST);
        
        // PANEL DE LABEL
        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(Color.BLACK);
        
        // LABEL QUE INFORMA "BUSCAR USUARIO"
        JLabel label = new JLabel("Buscar usuario:");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);
        
        panelLabel.add(label);
        panelArriba.add(panelLabel, BorderLayout.WEST);
        
        // AÑADIMOS LOS COMPONENTES AL PANEL PRINCIPAL
        panel.add(panelArriba, BorderLayout.NORTH);
        panel.add(searchField, BorderLayout.CENTER);
        panel.add(new JScrollPane(userList), BorderLayout.SOUTH);
        panel.add(selectButton, BorderLayout.EAST);
        
        // AÑADIMOS EL PANEL AL FRAME
        add(panel);
        setVisible(true);
    }

    // FILTRO DE LA LISTA DE USUARIOS
    
    //IAG (Herramienta: Chat GPT)
    //Con cambios.
    //Hecho con la ia y con el que hicimos de comics en clase
    private void filtrarUserList() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        listModel.clear();

        // FILTRAMOS LOS USUARIOS QUE CONTIENEN EL TEXTO DE BÚSQUEDA
        List<String> filteredUsers = allUsers.stream()
                .filter(user -> user.toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());

        // AGREGAMOS LOS USUARIOS FILTRADOS A LA LISTA
        filteredUsers.forEach(listModel::addElement);
    }

    // ACCIÓN AL SELECCIONAR UN USUARIO
    private void selectUser() {
        String selectedUser = userList.getSelectedValue();
        List<UsuarioSnake> listaUsuarios = gestorBD.getAllUsers();
        if (selectedUser != null) {
            // CREAMOS EL MENÚ PARA EL USUARIO SELECCIONADO
        	for (UsuarioSnake usuario : listaUsuarios) {
                if (usuario.getNombre().equals(selectedUser)) {
                	new MainMenuGUI(usuario);
                	dispose();
                }
            }
            
            
        } else {
            // SI NO SE SELECCIONÓ UN USUARIO, MOSTRAMOS UN MENSAJE
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario.");
        }
    }

    // OBTENER LOS USUARIOS DE LA BASE DE DATOS
    
    //IAG(Herramienta: ChatGPT)
    //Sin modificar
    private List<String> getUsersBd() {
        List<String> users = new ArrayList<>();
        
        try {
            // OBTENEMOS TODOS LOS USUARIOS DESDE LA BASE DE DATOS
            List<UsuarioSnake> listaUsuarios = gestorBD.getAllUsers();
            for (UsuarioSnake usuario : listaUsuarios) {
                users.add(usuario.getNombre());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // SI HAY UN ERROR AL OBTENER LOS USUARIOS, MOSTRAMOS UN MENSAJE
            JOptionPane.showMessageDialog(this, "Error al obtener los usuarios.");
        }
        
        return users;
    }
}
