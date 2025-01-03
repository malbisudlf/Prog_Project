package Menus.Ahorcado;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Menus.snake.menuSnake;
import usuario.UsuarioSnake;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

public class AhorcadoLeaderboard extends JFrame {
    private static final String DB_URL = "jdbc:sqlite:snake_game.db";
    private static final long serialVersionUID = 1L;
    private DefaultTableModel modeloLeaderboard;
    private JTable tablaleader;
    private static final String FILE_PATH = "leaderboard.txt";
    public static UsuarioSnake usuario;

    public AhorcadoLeaderboard(UsuarioSnake usuario) {
        menuSnake.usuario = usuario;
        setTitle("Leaderboard");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        JPanel panelbotton = new JPanel();
        panelbotton.setBackground(new Color(240, 240, 240));
        mainPanel.add(panelbotton, BorderLayout.SOUTH);

        JButton volver = new JButton("VOLVER");
        volver.setFocusable(false);
        volver.setBackground(new Color(41, 121, 255));
        volver.setForeground(Color.WHITE);
        volver.setFont(new Font("SansSerif", Font.BOLD, 14));
        volver.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panelbotton.add(volver);
        volver.addActionListener(e -> {
            new menuAhorcado(usuario);
            dispose();
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(240, 240, 240));
        JLabel ordenarLabel = new JLabel("Ordenar por:");
        ordenarLabel.setForeground(Color.DARK_GRAY);
        ordenarLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        topPanel.add(ordenarLabel);

        JComboBox<String> ordenarCombo = new JComboBox<>(new String[]{"Puntuación (Mayor a Menor)", "Antigüedad"});
        ordenarCombo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ordenarCombo.setBackground(new Color(240, 240, 240));
        ordenarCombo.setForeground(Color.DARK_GRAY);
        ordenarCombo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        ordenarCombo.setRenderer(new BasicComboBoxRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (isSelected) {
                    label.setBackground(new Color(41, 121, 255));
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(new Color(240, 240, 240));
                    label.setForeground(Color.DARK_GRAY);
                }
                return label;
            }
        });

        ordenarCombo.addActionListener(e -> {
            if (ordenarCombo.getSelectedIndex() == 0) {
                sortLeaderboardByScore();  // Ordena por puntuación
            } else {
                displayLeaderboardInOrder(true);  // Carga por antigüedad
            }
        });

        topPanel.add(ordenarCombo);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        Vector<String> cabeceraLeaderboard = new Vector<>(Arrays.asList("Nombre", "Puntuación"));
        this.modeloLeaderboard = new DefaultTableModel(new Vector<>(), cabeceraLeaderboard) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaleader = new JTable(this.modeloLeaderboard);
        tablaleader.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaleader.setRowHeight(30);
        tablaleader.setGridColor(new Color(224, 224, 224));

        tablaleader.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tablaleader.getTableHeader().setBackground(new Color(173, 216, 230));

        tablaleader.getTableHeader().setForeground(Color.BLACK);
        tablaleader.getTableHeader().setBorder(BorderFactory.createMatteBorder(1, 0, 2, 0, new Color(25, 118, 210)));

        tablaleader.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cell.setHorizontalAlignment(JLabel.CENTER);
                cell.setFont(new Font("Thoama", Font.BOLD, 16));
                if (row % 2 == 0) {
                    cell.setBackground(new Color(230, 240, 255));
                } else {
                    cell.setBackground(new Color(255, 245, 230));
                }
                cell.setForeground(new Color(33, 33, 33));
                if (isSelected) {
                    cell.setBackground(new Color(255, 171, 64));
                    cell.setForeground(Color.WHITE);
                }
                return cell;
            }
        });

        JScrollPane scrollboard = new JScrollPane(tablaleader);
        scrollboard.setBorder(BorderFactory.createEmptyBorder());
        scrollboard.getViewport().setBackground(new Color(245, 245, 245));

        scrollboard.getVerticalScrollBar().setBackground(new Color(240, 240, 240));
        scrollboard.getHorizontalScrollBar().setBackground(new Color(240, 240, 240));

        mainPanel.add(scrollboard, BorderLayout.CENTER);

        loadScoresFromDatabase(false);  // Cargar por puntuación inicialmente
        setVisible(true);
    }

    private void loadScoresFromDatabase(boolean ordenarPorAntiguedad) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = ordenarPorAntiguedad ? "SELECT nombre, puntuacion, fecha FROM ahorcado ORDER BY fecha ASC" : "SELECT nombre, puntuacion FROM ahorcado ORDER BY puntuacion DESC";
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    int puntuacion = rs.getInt("puntuacion");
                    String fecha = rs.getString("fecha");  // Suponiendo que tienes un campo 'fecha'
                    Vector<Object> row = new Vector<>(Arrays.asList(nombre, puntuacion, fecha));
                    modeloLeaderboard.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayLeaderboardInOrder(boolean ordenarPorAntiguedad) {
        modeloLeaderboard.setRowCount(0);
        loadScoresFromDatabase(ordenarPorAntiguedad);
    }

    @SuppressWarnings("unchecked")
    private void sortLeaderboardByScore() {
        Vector<Vector<Object>> data = (Vector<Vector<Object>>) (Vector<?>) modeloLeaderboard.getDataVector();
        
        // Ordenar los datos de mayor a menor por puntuación (Integer)
        data.sort((row1, row2) -> Integer.compare((Integer) row2.get(1), (Integer) row1.get(1))); 
        
        // Asegúrate de actualizar el modelo después de ordenar
        modeloLeaderboard.setRowCount(0); // Limpiar los datos actuales
        for (Vector<Object> row : data) {
            modeloLeaderboard.addRow(row); // Añadir los datos ordenados de nuevo
        }

        // Notificar que los datos han cambiado
        modeloLeaderboard.fireTableDataChanged();
    }

    public static void main(String[] args) {
        new AhorcadoLeaderboard(usuario);
    }
}
