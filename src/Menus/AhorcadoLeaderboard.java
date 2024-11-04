package Menus;

import java.awt.Color; import java.awt.Component; import java.awt.GridLayout; import java.io.BufferedReader; import java.io.BufferedWriter; import java.io.FileReader; import java.io.FileWriter; import java.io.IOException; import java.util.Arrays; import java.util.Vector;

import javax.swing.JFrame; import javax.swing.JScrollPane; import javax.swing.JTable; import javax.swing.border.TitledBorder; import javax.swing.table.DefaultTableModel; import javax.swing.table.TableCellRenderer;

public class AhorcadoLeaderboard extends JFrame {

private static final long serialVersionUID = 1L;
private DefaultTableModel modeloLeaderboard;
private JTable tablaleader;
private static final String FILE_PATH = "src/Menus/leaderboard.txt";

public AhorcadoLeaderboard() {
    setTitle("Leaderboard");
    setSize(600, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(false);
    getContentPane().setBackground(Color.BLACK);
    setFocusable(false);

    JScrollPane scrollboard = new JScrollPane();
    scrollboard.setBackground(Color.BLACK);
    scrollboard.setBorder(new TitledBorder("Puntuación"));
    this.getContentPane().setLayout(new GridLayout(1, 2));
    this.getContentPane().add(scrollboard);

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
            Component cell = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
            cell.setBackground(Color.BLACK);
            cell.setForeground(Color.WHITE);
            return cell;
        }
    };
    tablaleader.setDefaultRenderer(Object.class, cellRenderer);
    scrollboard.setViewportView(tablaleader);

    loadScoresFromFile();

    setVisible(true);
}

public void addScore(String nombre, int puntuacion) {
    Vector<Object> row = new Vector<>(Arrays.asList(nombre, puntuacion));
    modeloLeaderboard.addRow(row);
    saveScoreToFile(nombre, puntuacion);
}

private void saveScoreToFile(String nombre, int puntuacion) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
        writer.write(nombre + "," + puntuacion);
        writer.newLine();
        writer.flush();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void loadScoresFromFile() {
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                String nombre = parts[0];
                int puntuacion = Integer.parseInt(parts[1]);
                Vector<Object> row = new Vector<>(Arrays.asList(nombre, puntuacion));
                modeloLeaderboard.addRow(row);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}