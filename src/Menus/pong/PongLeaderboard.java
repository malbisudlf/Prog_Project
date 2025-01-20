package Menus.pong;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Menus.pong.PongLeaderboard;
import Menus.Ahorcado.menuAhorcado;
import Menus.pong.MenuPong;
import Menus.snake.SnakeLeaderboard;
import Menus.snake.menuSnake;
import db.GestorBD;
import usuario.UsuarioSnake;

public class PongLeaderboard extends JFrame{
	private static final String DB_URL = "jdbc:sqlite:resources/db/snake_game.db";
    private static final long serialVersionUID = 1L;
    private DefaultTableModel modeloLeaderboard;
    private JTable tablaleader;
    public static UsuarioSnake usuario;

    public PongLeaderboard(UsuarioSnake usuario) {
        menuSnake.usuario = usuario;
        setTitle("Leaderboard");
        setSize(700, 500);
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
            new MenuPong(usuario);
            dispose();
        });

        Vector<String> cabeceraLeaderboard = new Vector<>(Arrays.asList("Nombre", "Puntuación Normal", "Puntuación Dificil", "Puntuación Imposible"));
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

        JScrollPane scrollboard = new JScrollPane(tablaleader);
        scrollboard.setBorder(BorderFactory.createEmptyBorder());
        scrollboard.getViewport().setBackground(new Color(245, 245, 245));

        scrollboard.getVerticalScrollBar().setBackground(new Color(240, 240, 240));
        scrollboard.getHorizontalScrollBar().setBackground(new Color(240, 240, 240));

        mainPanel.add(scrollboard, BorderLayout.CENTER);

        loadScoresFromDatabase();  // Cargar por puntuación inicialmente
        setVisible(true);
    }

    private void loadScoresFromDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT nombre, puntuacionNormal, puntuacionDificil, puntuacionImposible FROM pong "
            		+ "WHERE puntuacionNormal != 0 OR puntuacionDificil != 0 OR puntuacionImposible != 0 "
            		+ "ORDER BY puntuacionNormal DESC, puntuacionDificil DESC, puntuacionImposible DESC";
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    int puntuacionNormal = rs.getInt("puntuacionNormal");
                    int puntuacionDificil = rs.getInt("puntuacionDificil");
                    int puntuacionImposible = rs.getInt("puntuacionImposible");
                    Vector<Object> row = new Vector<>(Arrays.asList(nombre, puntuacionNormal + " - 0", puntuacionDificil + " - 0", puntuacionImposible + " toques"));
                    modeloLeaderboard.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
