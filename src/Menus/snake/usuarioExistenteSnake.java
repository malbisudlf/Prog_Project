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

public class usuarioExistenteSnake extends JFrame{
	
	private JTextField searchField;
    private JList<String> userList;
    private DefaultListModel<String> listModel;
    private List<String> allUsers;
    
    public usuarioExistenteSnake() {
        setTitle("Seleccionar Usuario Existente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializa la lista de usuarios (deberías obtener esta lista desde la base de datos)
        allUsers = getUsersBd();
        listModel = new DefaultListModel<>();
        allUsers.forEach(listModel::addElement);

        userList = new JList<>(listModel);
        searchField = new JTextField(20);
        
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
        
        JButton selectButton = new JButton("Seleccionar");
        selectButton.addActionListener(e -> selectUser());

        JPanel panel = new JPanel(new BorderLayout());
        
        JPanel panelArriba = new JPanel(new BorderLayout());
        JButton botonVolver = new JButton("VOLVER");
        botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new seleccionUsuarioSnake();
				dispose();
			}
		});
        panelArriba.add(botonVolver, BorderLayout.EAST);
        panelArriba.add(new JLabel("Buscar usuario:"), BorderLayout.WEST);
        
        panel.add(panelArriba , BorderLayout.NORTH);
        panel.add(searchField, BorderLayout.CENTER);
        panel.add(new JScrollPane(userList), BorderLayout.SOUTH);
        panel.add(selectButton, BorderLayout.EAST);

        add(panel);
        setVisible(true);
    }

    //IAG 
    //MODIFICADO
    //Hecho tanto con IA tanto con el filtro que hicimos en clase en su dia
    private void filtrarUserList() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        listModel.clear();

        List<String> filteredUsers = allUsers.stream()
                .filter(user -> user.toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());

        filteredUsers.forEach(listModel::addElement);
    }

    private void selectUser() {
        String selectedUser = userList.getSelectedValue();
        if (selectedUser != null) {
            JOptionPane.showMessageDialog(this, "Has seleccionado: " + selectedUser);
            // Aquí, podrías pasar el nombre del usuario a la pantalla principal o a la sesión del juego
            new menuSnake(selectedUser);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario.");
        }
    }

    private List<String> getUsersBd() {
    	
    	//HABRIA QUE HACERLO CON LA BASES DE DATOS
    	//AÑADO ESTA LISTA DE PRUEBA TEMPORAL
        List<String> users = new ArrayList<>();
        users.add("Messillas");
        users.add("Guiri");
        users.add("MalbisuDLF");
        users.add("Cesarin");
        users.add("Aimi");
        users.add("Trueba");
        users.add("Tej");
        users.add("Raoul");
        users.add("Roberto");
        return users;
    }
}
