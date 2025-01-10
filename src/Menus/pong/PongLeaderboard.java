package Menus.pong;

import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import BD.GestorBDSnake;
import Menus.pong.PongLeaderboard;
import Menus.pong.MenuPong;
import Menus.snake.SnakeLeaderboard;
import Menus.snake.menuSnake;
import usuario.UsuarioSnake;

public class PongLeaderboard extends JFrame{
private static final long serialVersionUID = 1L;
	
	private JTable leaderboardTable;
	public static UsuarioSnake usuarioSnake;
	
	public PongLeaderboard(UsuarioSnake usuarioSnake) {
		
		SnakeLeaderboard.usuarioSnake = usuarioSnake;
		
		setTitle("SNAKE LEADERBOARD");
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		GestorBDSnake gestorBDSnake = new GestorBDSnake();
		
		List<UsuarioSnake> usuarios = gestorBDSnake.getAllUsers();
		
		String[][] data = new String[usuarios.size()][4]; //TANTAS FILAS COMO USUARIOS Y COLUMNAS PARA NOMBRE, PUNTUACION MAXIMA Y MONEDAS
		for (int i = 0; i < usuarios.size(); i++) {
			
			UsuarioSnake usuario = usuarios.get(i);
			data[i][0] = String.valueOf(i+1); //POSICION
			data[i][1] = usuario.getNombre(); //NOMBRE
			data[i][2] = String.valueOf(usuario.getPuntuacionAlta()); //PUNTUACION MAS ALTA
			data[i][3] = String.valueOf(usuario.getPuntosTotales()); //MONEDAS
				
		}
		
		String[] nombresColumnas = {"POS", "NOMBRE", "HIGH SCORE", "MONEDAS"};
		
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

		        // Destacar la fila del usuario actual
		        if (data[row][1].equals(usuarioSnake.getNombre())) { // Comparar el nombre del usuario
		        	if(row == 0) {
		        		celdaLabel.setBackground(new Color(255, 215, 0)); //DORADO
		        	} else if (row == 1) {
		        		celdaLabel.setBackground(new Color(192, 192, 192)); //PLATEADO
		        	} else if (row == 2) {
		        		celdaLabel.setBackground(new Color(205, 127, 50)); //BRONCE
		        	} else {
		            celdaLabel.setBackground(new Color(255, 178, 102)); // DIFERENCIA USUARIO
		        	}
		        } 
		        // Premios especiales (oro, plata, bronce para el Top 3 en columna 0)
		        else if (row == 0 && column == 0) {
		            celdaLabel.setBackground(new Color(255, 215, 0)); // Dorado
		        } else if (row == 1 && column == 0) {
		            celdaLabel.setBackground(new Color(192, 192, 192)); // Plata
		        } else if (row == 2 && column == 0) {
		            celdaLabel.setBackground(new Color(205, 127, 50)); // Bronce
		        } 
		        // Fila par
		        else if (row % 2 == 0) {
		            if (column == 0) { // Columna Nombre
		                celdaLabel.setBackground(new Color(255, 200, 220)); // Rosa pastel
		            } else if (column == 1) {
		                celdaLabel.setBackground(new Color(204, 229, 255)); // Azul pastel
		            } else if (column == 2) {
		                celdaLabel.setBackground(new Color(204, 255, 204)); // Verde pastel
		            } else if (column == 3) {
		                celdaLabel.setBackground(new Color(255, 253, 208)); // Amarillo pastel
		            }
		        } 
		        // Fila impar
		        else {
		            if (column == 0) { // Columna Nombre
		                celdaLabel.setBackground(new Color(255, 228, 225)); // Rosa claro pastel
		            } else if (column == 1) {
		                celdaLabel.setBackground(new Color(229, 243, 255)); // Azul claro pastel
		            } else if (column == 2) {
		                celdaLabel.setBackground(new Color(229, 255, 229)); // Verde claro pastel
		            } else if (column == 3) {
		                celdaLabel.setBackground(new Color(255, 255, 235)); // Amarillo claro pastel
		            }
		        }

		        // Fila seleccionada
		        if (isSelected) {
		            celdaLabel.setBackground(Color.GRAY);
		        }

		        celdaLabel.setOpaque(true); // Asegura que el color de fondo se aplique
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
