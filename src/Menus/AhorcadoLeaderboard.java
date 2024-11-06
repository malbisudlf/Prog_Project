package Menus;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

public class AhorcadoLeaderboard extends JFrame {
    private static final long serialVersionUID = 1L;
    private DefaultTableModel modeloLeaderboard;
    private JTable tablaleader;
    private static final String FILE_PATH = "leaderboard.txt";
//EN UN FUTURO, AÑADIR FLECHA PARA FILTRAR POR PUNTUACION
    public AhorcadoLeaderboard() {
        setTitle("Leaderboard");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setFocusable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);
        
        
        JPanel panelbotton = new JPanel();
        mainPanel.add(panelbotton, BorderLayout.SOUTH);
        panelbotton.setBackground(Color.BLACK);
        
        JButton volver = new JButton("VOLVER");
        volver.setFocusable(false);
        volver.setBackground(Color.BLACK);
        volver.setForeground(Color.WHITE);
        panelbotton.add(volver, BorderLayout.WEST);
        
        volver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new menuAhorcado();
				dispose();
				
			}
        	
        });
        

        JScrollPane scrollboard = new JScrollPane();
        scrollboard.setBackground(Color.BLACK);
        this.getContentPane().setLayout(new GridLayout(1, 2));
        this.getContentPane().add(scrollboard);
        mainPanel.add(scrollboard, BorderLayout.CENTER);

        Vector<String> cabeceraLeaderboard = new Vector<>(Arrays.asList("Nombre", "Puntuación"));
        this.modeloLeaderboard = new DefaultTableModel(new Vector<>(), cabeceraLeaderboard);
        this.tablaleader = new JTable(this.modeloLeaderboard);
        tablaleader.setBackground(Color.BLACK);
        tablaleader.setForeground(Color.WHITE);
        tablaleader.setGridColor(Color.GRAY);
        tablaleader.setFillsViewportHeight(true);

        
        TableCellRenderer cellRenderer = new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value != null ? value.toString() : "");
                label.setOpaque(true);
                label.setBackground(Color.BLACK);
                label.setForeground(Color.WHITE);
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        };
        tablaleader.setDefaultRenderer(Object.class, cellRenderer);

        scrollboard.setViewportView(tablaleader);

        loadScoresFromFile();

        setVisible(true);
    }

    private void loadScoresFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String nombre = parts[0];
                    long puntuacion = Long.parseLong(parts[1].trim()); // Use long to avoid integer overflow
                    Vector<Object> row = new Vector<>(Arrays.asList(nombre, puntuacion));
                    modeloLeaderboard.addRow(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Invalid score format in leaderboard.txt.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AhorcadoLeaderboard::new);
    }
}
