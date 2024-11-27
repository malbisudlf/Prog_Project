package Menus.snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import BD.GestorBDSnake;
import usuario.UsuarioSnake;

public class SnakeLeaderboard extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private JTable leaderboardTable;
	public static UsuarioSnake usuarioSnake;
	
	public SnakeLeaderboard(UsuarioSnake usuarioSnake) {
		
		SnakeLeaderboard.usuarioSnake = usuarioSnake;
		
		setTitle("SNAKE LEADERBOARD");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		GestorBDSnake gestorBDSnake = new GestorBDSnake();
		
		List<UsuarioSnake> usuarios = gestorBDSnake.getAllUsers();
		
		String[][] data = new String[usuarios.size()][3]; //TANTAS FILAS COMO USUARIOS Y COLUMNAS PARA NOMBRE, PUNTUACION MAXIMA Y MONEDAS
		for (int i = 0; i < usuarios.size(); i++) {
			
			UsuarioSnake usuario = usuarios.get(i);
			
			data[i][0] = usuario.getNombre(); //NOMBRE
			data[i][1] = String.valueOf(usuario.getPuntuacionAlta()); //PUNTUACION MAS ALTA
			data[i][2] = String.valueOf(usuario.getPuntosTotales()); //MONEDAS
				
		}
		
		String[] nombresColumnas = {"NOMBRE", "PUNTUACION MAXIMA", "MONEDAS"};
		
		DefaultTableModel model = new DefaultTableModel(data, nombresColumnas) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		leaderboardTable = new JTable(model);
		
		leaderboardTable.setRowHeight(20);
		
		//RENDERER PARA LOS HEADERS
		leaderboardTable.getTableHeader().setDefaultRenderer(new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				
				JLabel headerLabel = new JLabel(value.toString());
				
				headerLabel.setFont(new Font("MV BOLI", Font.BOLD, 16));
				headerLabel.setForeground(new Color(255, 204, 229)); //ROSA PASTEL CLARO
				headerLabel.setBackground(Color.DARK_GRAY);
				
				headerLabel.setHorizontalAlignment(JLabel.CENTER);
				
				headerLabel.setOpaque(true);
				
								
				return headerLabel;
				
			}
		});
		
		//RENDERER PARA LAS CELDAS
		
		leaderboardTable.setDefaultRenderer(Object.class, new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {

			JLabel celdaLabel = new JLabel(value.toString());
			
			celdaLabel.setForeground(Color.BLACK);
			celdaLabel.setFont(new Font("MV BOLI", Font.BOLD, 16));
			celdaLabel.setHorizontalAlignment(SwingConstants.CENTER);
			
			if (data[row][0].equals(usuarioSnake.getNombre())) { // Match by username
				
	            celdaLabel.setBackground(new Color(255, 239, 213));
				
			} else if (row % 2 == 0) { //SI LA FILA ES PAR
				
				if (column == 0) { //COLUMNA NOMBRE
					celdaLabel.setBackground(new Color(204, 229, 255)); //AZUL PASTEL
				} else if (column == 1) {
					celdaLabel.setBackground(new Color(204, 255, 204)); //VERDE PASTEL
				} else if (column == 2) {
					celdaLabel.setBackground(new Color(255, 253, 208)); //AMARILLO PASTEL
				}

				
			} else {
				
				if (column == 0) { //COLUMNA NOMBRE
					celdaLabel.setBackground(new Color(229, 243, 255)); //AZUL CLARO PASTEL
				} else if (column == 1) {
					celdaLabel.setBackground(new Color(229, 255, 229)); //VERDE CLARO PASTEL
				} else if (column == 2) {
					celdaLabel.setBackground(new Color(255, 255, 235)); //AMARILLO CLARO PASTEL
				}
				
			}
			
			if (isSelected) {
                celdaLabel.setBackground(Color.GRAY);
            }
			
			celdaLabel.setOpaque(true);
			
			return celdaLabel;
			}
		});
	
		JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        add(scrollPane, BorderLayout.CENTER);
       
        JPanel restoPaneles = new JPanel();
        restoPaneles.setBackground(Color.BLACK);
        
        JLabel labelArriba = new JLabel("LEADERBOARD SNAKE");
        
        labelArriba.setHorizontalAlignment(SwingConstants.CENTER);
        
        labelArriba.setFont(new Font("MV BOLI", Font.BOLD, 25));
        labelArriba.setBackground(new Color(230, 230, 230)); //MORADO SUAVE PASTEL
        labelArriba.setForeground(new Color(102, 51, 153)); // MORADO OSCURO PASTEL

        
        labelArriba.setOpaque(true);
        
        JPanel panelAbajo = new JPanel();
        
        panelAbajo.setBackground(new Color(230, 230, 230)); //GRIS PASTEL SUAVE
        
        JButton botonVolver = new JButton("VOLVER");
        
        botonVolver.setBackground(Color.DARK_GRAY);
        botonVolver.setForeground(new Color(255, 204, 229));

        botonVolver.setFocusable(false);
        
        botonVolver.addActionListener(e -> {
        	new menuSnake(usuarioSnake);
        	dispose();
        });
        
        panelAbajo.add(botonVolver, BorderLayout.WEST);
        
        setBackground(Color.BLACK);
        add(panelAbajo, BorderLayout.SOUTH);
        add(labelArriba, BorderLayout.NORTH);
        
        setVisible(true);
	}

}
