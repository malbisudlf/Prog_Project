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
import BD.GestorBDSnake;  // Asegúrate de importar la clase GestorBDSnake
import usuario.UsuarioSnake;

public class usuarioExistenteSnake extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private JTextField searchField;
    private JList<String> userList;
    private DefaultListModel<String> listModel;
    private List<String> allUsers;
    private final GestorBDSnake gestorBD = new GestorBDSnake();  // Instancia de GestorBDSnake
    
    public usuarioExistenteSnake() {
        setTitle("Seleccionar Usuario Existente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Inicializa la lista de usuarios (CON BD SE OBTENDRA DE AHI) 
        allUsers = getUsersBd();
        listModel = new DefaultListModel<>();
        allUsers.forEach(listModel::addElement);
        
        // Lista con los usuarios
        userList = new JList<>(listModel);
        userList.setBackground(Color.BLACK);
        userList.setForeground(Color.WHITE);
        userList.setSelectionBackground(Color.DARK_GRAY);
        userList.setSelectionForeground(Color.WHITE);
        
        // Área de búsqueda (JTextField)
        searchField = new JTextField(20);
        searchField.setBackground(Color.BLACK);
        searchField.setForeground(Color.WHITE);
        searchField.setCaretColor(Color.WHITE);
        
        // Filtro en tiempo real
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
        
        // Botón seleccionar (JButton)
        JButton selectButton = new JButton("Seleccionar");
        selectButton.setFocusable(false);
        selectButton.setBackground(Color.BLACK);
        selectButton.setForeground(Color.WHITE);
        selectButton.addActionListener(e -> selectUser());
        
        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        
        // Panel de arriba
        JPanel panelArriba = new JPanel(new BorderLayout());
        panelArriba.setBackground(Color.BLACK);
        
        // Botón Volver
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
        
        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(Color.BLACK);
        
        JLabel label = new JLabel("Buscar usuario:");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);
        
        panelLabel.add(label);
        panelArriba.add(panelLabel, BorderLayout.WEST);
        
        panel.add(panelArriba, BorderLayout.NORTH);
        panel.add(searchField, BorderLayout.CENTER);
        panel.add(new JScrollPane(userList), BorderLayout.SOUTH);
        panel.add(selectButton, BorderLayout.EAST);
        
        add(panel);
        setVisible(true);
    }

    // Filtro de la lista de usuarios
    private void filtrarUserList() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        listModel.clear();

        List<String> filteredUsers = allUsers.stream()
                .filter(user -> user.toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());

        filteredUsers.forEach(listModel::addElement);
    }

    // Acción al seleccionar un usuario
    private void selectUser() {
        String selectedUser = userList.getSelectedValue();
        if (selectedUser != null) {
            new menuSnake(selectedUser);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario.");
        }
    }

    // Obtener los usuarios de la base de datos
    private List<String> getUsersBd() {
        List<String> users = new ArrayList<>();
        
        try {
            List<UsuarioSnake> listaUsuarios = gestorBD.getAllUsers();
            for (UsuarioSnake usuario : listaUsuarios) {
                users.add(usuario.getNombre());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener los usuarios.");
        }
        
        return users;
    }
}
