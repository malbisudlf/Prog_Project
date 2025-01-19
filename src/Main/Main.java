package Main;

<<<<<<< HEAD
import BD.GestorBDSnake;
=======
import Menus.MainMenuGUI;
>>>>>>> branch 'master' of https://github.com/malbisudlf/Prog_Project.git
import Menus.snake.seleccionUsuarioSnake;
import db.GestorBD;
import usuario.UsuarioSnake;

public class Main {
    public static void main(String[] args) {
        // Inicializar la base de datos y cargar datos del CSV
        GestorBD gestorBD = new GestorBD();
        
        //DESCOMENTAR ESTO SI QUEREMOS RESETEAR LA BASE DE DATOS
        /*
        gestorBD.resetDatabase();
        System.out.println("BASE DE DATOS RESETEADA");
        */
        
        System.out.println("Base de datos inicializada correctamente.");
		
        //VERIFICAMOS DATOS BD SNAKE
        System.out.println("Usuarios registrados en la base de datos de SNAKE:");
        for (UsuarioSnake usuario : gestorBD.getAllUsers()) {
            System.out.println(usuario);
        }

        // Iniciar la interfaz gráfica
        new seleccionUsuarioSnake();
    }
}
