package Main;

import BD.GestorBDSnake; // Asegúrate de importar correctamente la clase de gestión de la BD
import Menus.MainMenuGUI;
import usuario.UsuarioSnake;

public class Main {
    public static void main(String[] args) {
        // Inicializar la base de datos y cargar datos del CSV
        GestorBDSnake gestorBD = new GestorBDSnake();
        
        //DESCOMENTAR ESTO SI QUEREMOS RESETEAR LA BASE DE DATOS
        /*
        gestorBD.resetDatabase();
        System.out.println("BASE DE DATOS RESETEADA");
        */
        
        System.out.println("Base de datos inicializada correctamente.");
		
        // (Opcional) Verificar que los datos están cargados
        System.out.println("Usuarios registrados en la base de datos de SNAKE:");
        for (UsuarioSnake usuario : gestorBD.getAllUsers()) {
            System.out.println(usuario);
        }

        // Iniciar la interfaz gráfica
        new MainMenuGUI();
    }
}
