
package Menus.Ahorcado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        //IAG: ChatGPT
        //Sin cambios
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
        //hasta aqui ayuda de IAG
        tablaleader.setDefaultRenderer(Object.class, cellRenderer);

        scrollboard.setViewportView(tablaleader);

        JPopupMenu sortMenu = new JPopupMenu();
        JMenuItem sortByScore = new JMenuItem("De mayor a menor");
        JMenuItem sortByTime = new JMenuItem("Por antigüedad");
        sortMenu.add(sortByScore);
        sortMenu.add(sortByTime);

        sortByScore.addActionListener(e -> sortLeaderboardByScore());
        sortByTime.addActionListener(e -> displayLeaderboardInOrder());
        //IAG: ChatGPT
        //Sin cambios
        tablaleader.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = tablaleader.columnAtPoint(e.getPoint());
                if (col == 1) { 
                    sortMenu.show(tablaleader.getTableHeader(), e.getX(), e.getY());
                }
            }
        });
        //Hasta aqui ayuda de IAG

        loadScoresFromFile();

        setVisible(true);
    }
    //IAG: ChatGPT
    //Adaptado

    private void loadScoresFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) { 
                    String nombre = parts[0];
                    long puntuacion = Long.parseLong(parts[1].trim());
                    Vector<Object> row = new Vector<>(Arrays.asList(nombre, puntuacion));
                    modeloLeaderboard.addRow(row);
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Invalid score format in leaderboard.txt.");
        }
    }
    //hasta aqui ayuda de IAG

    private void displayLeaderboardInOrder() {
        modeloLeaderboard.setRowCount(0); 
        loadScoresFromFile(); 
    }
    //IAG: ChatGPT
    //Adaptado

    @SuppressWarnings("unchecked")
    private void sortLeaderboardByScore() {
        Vector<Vector<Object>> data = (Vector<Vector<Object>>) (Vector<?>) modeloLeaderboard.getDataVector();
        data.sort((row1, row2) -> Long.compare((Long) row2.get(1), (Long) row1.get(1)));
        modeloLeaderboard.fireTableDataChanged();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AhorcadoLeaderboard::new);
    }
}
