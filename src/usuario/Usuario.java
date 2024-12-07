package usuario;

public class Usuario {
    private int id;
    private String nombre;
    private int puntuacionAltaSnake;
    private int puntosTotalesSnake;
    private int puntuacionAltaAhorcado;

    // Constructor
    public Usuario(int id, String nombre, int puntuacionAltaSnake, int puntosTotalesSnake, int puntuacionAltaAhorcado) {
        this.id = id;
        this.nombre = nombre;
        this.puntuacionAltaSnake = puntuacionAltaSnake;
        this.puntosTotalesSnake = puntosTotalesSnake;
        this.puntuacionAltaAhorcado = puntuacionAltaAhorcado;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntuacionAltaSnake() {
        return puntuacionAltaSnake;
    }

    public void setPuntuacionAltaSnake(int puntuacionAltaSnake) {
        this.puntuacionAltaSnake = puntuacionAltaSnake;
    }

    public int getPuntosTotalesSnake() {
        return puntosTotalesSnake;
    }

    public void setPuntosTotalesSnake(int puntosTotalesSnake) {
        this.puntosTotalesSnake = puntosTotalesSnake;
    }

    public int getPuntuacionAltaAhorcado() {
        return puntuacionAltaAhorcado;
    }

    public void setPuntuacionAltaAhorcado(int puntuacionAltaAhorcado) {
        this.puntuacionAltaAhorcado = puntuacionAltaAhorcado;
    }

    // Métodos para actualizar puntuaciones de Snake
    public void actualizarPuntuacionAltaSnake(int nuevaPuntuacion) {
        if (nuevaPuntuacion > this.puntuacionAltaSnake) {
            this.puntuacionAltaSnake = nuevaPuntuacion;
        }
    }

    public void agregarPuntosTotalesSnake(int puntos) {
        this.puntosTotalesSnake += puntos;
    }

    // Métodos para actualizar puntuaciones de Ahorcado
    public void actualizarPuntuacionAltaAhorcado(int nuevaPuntuacion) {
        if (nuevaPuntuacion > this.puntuacionAltaAhorcado) {
            this.puntuacionAltaAhorcado = nuevaPuntuacion;
        }
    }

    // Método toString para imprimir de forma legible
    @Override
    public String toString() {
        return "Usuario{id=" + id +
                ", nombre='" + nombre + '\'' +
                ", puntuacionAltaSnake=" + puntuacionAltaSnake +
                ", puntosTotalesSnake=" + puntosTotalesSnake +
                ", puntuacionAltaAhorcado=" + puntuacionAltaAhorcado +
                '}';
    }
}
