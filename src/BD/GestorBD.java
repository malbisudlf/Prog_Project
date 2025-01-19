package BD;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import usuario.UsuarioSnake;

public class GestorBD {

    private static final String DB_URL = "jdbc:sqlite:snake_game.db";
    private static final String CONFIG_FILE = "resources/config.properties";
    private boolean loadFromCSV;
    private boolean saveToCSV;
    private boolean resetDatabase;
    private boolean loadFromTxt;    // Nueva propiedad
    private boolean saveToTxt;     // Nueva propiedad
    private boolean resetTxt;      // Nueva propiedad
    private static final String FILE_PATH = "resources/leaderboard.txt"; // Ruta del archivo de texto

    private static final String CREATE_USERS_TABLE = """
        CREATE TABLE IF NOT EXISTS usuarios (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL UNIQUE,
            puntuacion_maxima INTEGER DEFAULT 0,
            puntos_totales INTEGER DEFAULT 0
        );
    """;

    private static final String CREATE_AHORCADO_TABLE = """
        CREATE TABLE IF NOT EXISTS ahorcado (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL,
            puntuacion INTEGER DEFAULT 0,
            FOREIGN KEY(nombre) REFERENCES usuarios(nombre)
        );
    """;  
    
    private static final String CREATE_PONG_TABLE = """
        CREATE TABLE IF NOT EXISTS pong (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL,
            puntuacion INTEGER DEFAULT 0,
            FOREIGN KEY(nombre) REFERENCES usuarios(nombre)
        );
    """;

    public GestorBD() {
        loadConfig();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            if (resetDatabase) {
                resetDatabase();
            }

            stmt.execute(CREATE_USERS_TABLE);
            stmt.execute(CREATE_AHORCADO_TABLE);
            stmt.execute(CREATE_PONG_TABLE);

            if (loadFromCSV) {
                loadFromCSV();
            }
            if (loadFromTxt) {
                loadScoresFromTxt(); // Cargar las puntuaciones desde el archivo al iniciar
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);

            loadFromCSV = Boolean.parseBoolean(properties.getProperty("loadFromCSV", "false"));
            saveToCSV = Boolean.parseBoolean(properties.getProperty("saveToCSV", "false"));
            resetDatabase = Boolean.parseBoolean(properties.getProperty("resetDatabase", "false"));
            loadFromTxt = Boolean.parseBoolean(properties.getProperty("loadFromTxt", "false")); // Cargar desde txt
            saveToTxt = Boolean.parseBoolean(properties.getProperty("saveToTxt", "false"));   // Guardar en txt
            resetTxt = Boolean.parseBoolean(properties.getProperty("resetTxt", "false"));   // Resetear txt

        } catch (IOException e) {
            System.err.println("Error al cargar el archivo de configuración: " + e.getMessage());
            // Configuración por defecto
            loadFromCSV = false;
            saveToCSV = false;
            resetDatabase = false;
            loadFromTxt = false;
            saveToTxt = false;
            resetTxt = false;
        }
    }

    public UsuarioSnake getUserByName(String userName) {
        String sql = "SELECT * FROM usuarios WHERE nombre = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int puntuacionMaxima = rs.getInt("puntuacion_maxima");
                int puntosTotales = rs.getInt("puntos_totales");
                return new UsuarioSnake(id, nombre, puntuacionMaxima, puntosTotales);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addUser(String userName) {
        String sql = "INSERT INTO usuarios (nombre, puntuacion_maxima, puntos_totales) VALUES (?, 0, 0)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUserExists(String nombre) {
        String sql = "SELECT 1 FROM usuarios WHERE nombre = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar las puntuaciones del juego Snake
    public boolean updateSnakeScores(String nombre, int nuevaPuntuacion, int puntosPartida) {
        String sql = """
            UPDATE usuarios
            SET puntuacion_maxima = CASE WHEN ? > puntuacion_maxima THEN ? ELSE puntuacion_maxima END,
                puntos_totales = puntos_totales + ?
            WHERE nombre = ?
        """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nuevaPuntuacion);
            pstmt.setInt(2, nuevaPuntuacion);
            pstmt.setInt(3, puntosPartida);
            pstmt.setString(4, nombre);
            pstmt.executeUpdate();

            if (saveToCSV) {
                saveToCSV();
            }
           
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    // Método para actualizar las puntuaciones del juego Ahorcado
    public boolean updateAhorcadoScores(String nombre, int puntuacion) {
        String sql = "INSERT OR REPLACE INTO ahorcado(nombre, puntuacion) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setInt(2, puntuacion);
            pstmt.executeUpdate();

            
            if (saveToTxt) {
                saveScoresToTxt();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // Método para actualizar las puntuaciones del juego Ahorcado
    public boolean updatePongScores(String nombre, int puntuacion) {
        String sql = "INSERT OR REPLACE INTO pong(nombre, puntuacion) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setInt(2, puntuacion);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<UsuarioSnake> getAllUsers() {
        List<UsuarioSnake> usuarios = new ArrayList<>();
        String sql = "SELECT id, nombre, puntuacion_maxima, puntos_totales FROM usuarios ORDER BY puntuacion_maxima DESC";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int puntuacionMaxima = rs.getInt("puntuacion_maxima");
                int puntosTotales = rs.getInt("puntos_totales");
                UsuarioSnake usuario = new UsuarioSnake(id, nombre, puntuacionMaxima, puntosTotales);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public void resetDatabase() {
        String dropUsersTable = "DROP TABLE IF EXISTS usuarios";
        String dropAhorcadoTable = "DROP TABLE IF EXISTS ahorcado";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(dropUsersTable);
            stmt.execute(dropAhorcadoTable);
            stmt.execute(CREATE_USERS_TABLE);
            stmt.execute(CREATE_AHORCADO_TABLE);
            System.out.println("La base de datos se ha reiniciado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al reiniciar la base de datos: " + e.getMessage());
        }
    }

    private void loadFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("resources/usuarios.csv"));
             Connection conn = DriverManager.getConnection(DB_URL)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) continue;

                String nombre = parts[1].trim();
                int puntuacionMaxima = Integer.parseInt(parts[2].trim());
                int puntosTotales = Integer.parseInt(parts[3].trim());

                if (!isUserExists(nombre)) {
                    String sql = "INSERT INTO usuarios(nombre, puntuacion_maxima, puntos_totales) VALUES (?, ?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, nombre);
                        pstmt.setInt(2, puntuacionMaxima);
                        pstmt.setInt(3, puntosTotales);
                        pstmt.executeUpdate();
                        
                    }
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveToCSV() {
        String sql = "SELECT id, nombre, puntuacion_maxima, puntos_totales FROM usuarios";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/usuarios.csv", false));
             Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int puntuacionMaxima = rs.getInt("puntuacion_maxima");
                int puntosTotales = rs.getInt("puntos_totales");
                bw.write(id + "," + nombre + "," + puntuacionMaxima + "," + puntosTotales);
                bw.newLine();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadScoresFromTxt() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
             Connection conn = DriverManager.getConnection(DB_URL)) {

            // Eliminar todos los registros actuales en la tabla 'ahorcado' antes de cargar los nuevos
            String deleteAllSql = "DELETE FROM ahorcado";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteAllSql)) {
                deleteStmt.executeUpdate();
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 2) continue;

                String nombre = parts[0].trim();
                int puntuacion = Integer.parseInt(parts[1].trim());

                // Insertar los nuevos datos desde el archivo
                String sql = "INSERT INTO ahorcado(nombre, puntuacion) VALUES (?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, nombre);
                    pstmt.setInt(2, puntuacion);  // Ya no se guarda la fecha
                    pstmt.executeUpdate();
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveScoresToTxt() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            String sql = "SELECT nombre, puntuacion FROM ahorcado ORDER BY puntuacion DESC";
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    int puntuacion = rs.getInt("puntuacion");

                    bw.write(nombre + "," + puntuacion);
                    bw.newLine();
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void resetTxt() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            bw.write(""); // Borra todo el contenido del archivo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
