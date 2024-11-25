package BD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorBDSnake {

    private static final String DB_URL = "jdbc:sqlite:snake_game.db";
    private static final String CREATE_USERS_TABLE = """
        CREATE TABLE IF NOT EXISTS usuarios (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL UNIQUE
        );
   """;

    private static final String CREATE_LEADERBOARD_TABLE = """
        CREATE TABLE IF NOT EXISTS leaderboard (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            usuario_id INTEGER NOT NULL,
            puntuacion INTEGER NOT NULL,
            FOREIGN KEY(usuario_id) REFERENCES usuarios(id)
        );
    """;

    public GestorBDSnake() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            // Crear tablas si no existen
            stmt.execute(CREATE_USERS_TABLE);
            stmt.execute(CREATE_LEADERBOARD_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(String nombre) {
        String sql = "INSERT INTO usuarios(nombre) VALUES (?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false; // Si falla, probablemente sea porque el nombre ya existe
        }
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

    public List<String> getAllUsers() {
        List<String> usuarios = new ArrayList<>();
        String sql = "SELECT nombre FROM usuarios";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}

