package BD;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import usuario.UsuarioSnake;

	//LA MAYORIA COGIDA DE JDBC-P1 Y JDBC-P2
	//LO HECHO CON IAG COMENTADO EN SU SITIO
	
public class GestorBDSnake {

    private static final String DB_URL = "jdbc:sqlite:snake_game.db";
    //private static final String CSV_FILE = "resources/data/usuarios.csv";  // Comentado porque se está usando una base de datos SQLite

    private static final String CREATE_USERS_TABLE = """
        CREATE TABLE IF NOT EXISTS usuarios (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL UNIQUE,
            puntuacion_maxima INTEGER DEFAULT 0,
            puntos_totales INTEGER DEFAULT 0
        );
    """; 

    public GestorBDSnake() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            // Crear tabla si no existe
            stmt.execute(CREATE_USERS_TABLE);

            // Cargar datos desde el fichero CSV (actualizado a base de datos)
            loadFromCSV();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Obtener un usuario por su nombre
    
    //IAG(Herramienta: ChatGPT)
    //Modificado
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
                return new UsuarioSnake(id, nombre, puntuacionMaxima, puntosTotales);  // Retorna un objeto Usuario con puntuaciones
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Si no existe el usuario, devuelve null
    }

    // Añadir un nuevo usuario
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

    
    // Verificar si un usuario existe por su nombre
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

    // Actualizar puntuación máxima y puntos totales de un usuario
    //IAG(Herramienta: ChatGPT)
    //IA solamrnte para la variable String sql.
 // Actualizar puntuación máxima y puntos totales de un usuario
    public boolean updateScores(String nombre, int nuevaPuntuacion, int puntosPartida) {
        String sql = """
            UPDATE usuarios
            SET puntuacion_maxima = CASE WHEN ? > puntuacion_maxima THEN ? ELSE puntuacion_maxima END,
                puntos_totales = puntos_totales + ?
            WHERE nombre = ?
        """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nuevaPuntuacion);   // Compara con puntuacion_maxima
            pstmt.setInt(2, nuevaPuntuacion);   // Actualiza si es mayor
            pstmt.setInt(3, puntosPartida);     // Suma los puntos totales ganados en la partida
            pstmt.setString(4, nombre);         // Nombre del jugador
            pstmt.executeUpdate();
            saveToCSV(); // Actualizar el CSV con la nueva puntuación
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Obtener todos los usuarios
    public List<UsuarioSnake> getAllUsers() {
        List<UsuarioSnake> usuarios = new ArrayList<>();
        String sql = "SELECT id, nombre, puntuacion_maxima, puntos_totales FROM usuarios";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            // Recorremos el ResultSet para obtener los usuarios
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int puntuacionMaxima = rs.getInt("puntuacion_maxima");
                int puntosTotales = rs.getInt("puntos_totales");
                
                // Creamos un objeto Usuario con los datos obtenidos
                UsuarioSnake usuario = new UsuarioSnake(id, nombre, puntuacionMaxima, puntosTotales);
                
                // Agregamos el usuario a la lista
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones en caso de error en la consulta
        }
        
        return usuarios;
    }

    // Registrar una nueva puntuación para un usuario
    public boolean addNewScore(String nombre, int puntuacion, int puntuacionAlta) {
        if (!isUserExists(nombre)) {
            addUser(nombre); // Si no existe, se crea el usuario
        }
        return updateScores(nombre, puntuacion, puntuacionAlta);  // Actualiza la puntuación del usuario
    }

    // Obtener el jugador con la mejor puntuación máxima
    public String getTopPlayer() {
        String sql = "SELECT nombre FROM usuarios ORDER BY puntuacion_maxima DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getString("nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Si no hay jugadores, retorna null
    }

    // Cargar datos desde el fichero CSV
    //DE OTRAS PRACT
    private void loadFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("resources/usuarios.csv"));
             Connection conn = DriverManager.getConnection(DB_URL)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) continue;  // Asegurarse de que los datos estén completos

                int id = Integer.parseInt(parts[0].trim());
                String nombre = parts[1].trim();
                int puntuacionMaxima = Integer.parseInt(parts[2].trim());
                int puntosTotales = Integer.parseInt(parts[3].trim());

                if (!isUserExists(nombre)) {
                    String sql = "INSERT INTO usuarios(id, nombre, puntuacion_maxima, puntos_totales) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, id);
                        pstmt.setString(2, nombre);
                        pstmt.setInt(3, puntuacionMaxima);
                        pstmt.setInt(4, puntosTotales);
                        pstmt.executeUpdate();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el fichero CSV: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Guardar datos en el fichero CSV
    //DE OTRAS PRACT
    public void saveToCSV() {
        String sql = "SELECT id, nombre, puntuacion_maxima, puntos_totales FROM usuarios";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/usuarios.csv", true));
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
    
 // Método para borrar la tabla de usuarios y recrearla
    //IAG(Herramienta: ChatGPT)
    //SIN MODIFICAR
    //IMPLEMENTADO SIMPLEMENTE PARA PODER RESETEAR LA BASE DE DATOS QUE LLENE DE USUARIOS RANDOM
    public void resetDatabase() {
        String dropTableSQL = "DROP TABLE IF EXISTS usuarios";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            // Eliminar la tabla
            stmt.execute(dropTableSQL);

            // Volver a crear la tabla
            stmt.execute(CREATE_USERS_TABLE);
            System.out.println("La base de datos se ha reiniciado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al reiniciar la base de datos: " + e.getMessage());
        }
    }


}
