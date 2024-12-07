package BD;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import usuario.Usuario;

public class GestorBD {
    private static final String DB_URL = "jdbc:sqlite:game_data.db";
    private static final String CREATE_USERS_TABLE = """
        CREATE TABLE IF NOT EXISTS usuarios (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL UNIQUE,
            puntuacion_maxima_snake INTEGER DEFAULT 0,
            puntos_totales_snake INTEGER DEFAULT 0,
            puntuacion_maxima_ahorcado INTEGER DEFAULT 0
        );
    """;

    public GestorBD() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            // Crear la tabla si no existe
            stmt.execute(CREATE_USERS_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para agregar un usuario
    public boolean addUser(String userName) {
        String sql = "INSERT INTO usuarios (nombre) VALUES (?)";
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

    // Método para actualizar puntuaciones de Snake
    public boolean updateSnakeScores(String nombre, int nuevaPuntuacion, int puntosPartida) {
        String sql = """
            UPDATE usuarios
            SET puntuacion_maxima_snake = CASE WHEN ? > puntuacion_maxima_snake THEN ? ELSE puntuacion_maxima_snake END,
                puntos_totales_snake = puntos_totales_snake + ?
            WHERE nombre = ?
        """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nuevaPuntuacion);
            pstmt.setInt(2, nuevaPuntuacion);
            pstmt.setInt(3, puntosPartida);
            pstmt.setString(4, nombre);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para actualizar puntuaciones de Ahorcado
    public boolean updateAhorcadoScore(String nombre, int nuevaPuntuacion) {
        String sql = """
            UPDATE usuarios
            SET puntuacion_maxima_ahorcado = CASE WHEN ? > puntuacion_maxima_ahorcado THEN ? ELSE puntuacion_maxima_ahorcado END
            WHERE nombre = ?
        """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nuevaPuntuacion);
            pstmt.setInt(2, nuevaPuntuacion);
            pstmt.setString(3, nombre);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para obtener todos los usuarios
    public List<Usuario> getAllUsers() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY puntuacion_maxima_snake DESC";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("puntuacion_maxima_snake"),
                        rs.getInt("puntos_totales_snake"),
                        rs.getInt("puntuacion_maxima_ahorcado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Método para obtener un usuario por su nombre
    public Usuario getUserByName(String nombre) {
        String sql = "SELECT * FROM usuarios WHERE nombre = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getInt("puntuacion_maxima_snake"),
                            rs.getInt("puntos_totales_snake"),
                            rs.getInt("puntuacion_maxima_ahorcado")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para guardar datos en CSV
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

                // Escribimos los datos en el archivo CSV
                bw.write(id + "," + nombre + "," + puntuacionMaxima + "," + puntosTotales);
                bw.newLine();  // Nueva línea para cada usuario
            }
        } catch (IOException e) {
            System.out.println("No se pudo guardar el fichero CSV: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
