package usuario;

public class UsuarioSnake {
    private int id;
    private String nombre;
    private int puntuacionAlta;
    private int puntosTotales;

    // Constructor
    public UsuarioSnake(int id, String nombre, int puntuacionAlta, int puntosTotales) {
        this.id = id;
        this.nombre = nombre;
        this.puntuacionAlta = puntuacionAlta;
        this.puntosTotales = puntosTotales;
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

    public int getPuntuacionAlta() {
        return puntuacionAlta;
    }

    public void setPuntuacionAlta(int puntuacionAlta) {
        this.puntuacionAlta = puntuacionAlta;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }

    // Método para actualizar la puntuación alta si la nueva puntuación es mayor
    public void actualizarPuntuacionAlta(int nuevaPuntuacion) {
        if (nuevaPuntuacion > this.puntuacionAlta) {
            this.puntuacionAlta = nuevaPuntuacion;
        }
    }

    // Método para sumar puntos totales (por ejemplo, cada vez que el usuario juegue)
    public void agregarPuntosTotales(int puntos) {
        this.puntosTotales += puntos;
    }

    // Método toString para imprimir de forma legible
    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre='" + nombre + "', puntuacionAlta=" + puntuacionAlta + ", monedas=" + puntosTotales + "}";
    }
}
