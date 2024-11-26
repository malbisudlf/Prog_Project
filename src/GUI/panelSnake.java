package GUI;

import javax.swing.*;

import BD.GestorBDSnake;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class panelSnake extends JPanel implements ActionListener, KeyListener {

    //CODIGO BASE DEL JUEGO DE KIP YIP CODING
    //MODIFICADA EN ALGUNA PARTE PARA ADAPTARSE A NUESTRO PROYECTO

    private static final long serialVersionUID = 1L;

    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int anchoPanel;
    int altoPanel;
    int tileSize = 25;

    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    Tile food;
    Random random;

    Timer bucleJuego;
    int velocidadX;
    int velocidadY;

    int score = 0;
    int highScore = 0;
    boolean gameOver = false;
    SnakeGUI parentGUI;  // Referencia a SnakeGUI para poder acceder al menú

    panelSnake(int anchoPanel, int altoPanel, SnakeGUI parentGUI) {
        this.anchoPanel = anchoPanel;
        this.altoPanel = altoPanel;
        this.parentGUI = parentGUI;  // Guardar referencia al JFrame principal
        setPreferredSize(new Dimension(this.anchoPanel, this.altoPanel));
        setBackground(Color.GREEN);
        addKeyListener(this);  // Escuchar las teclas para mover la serpiente
        setFocusable(true);

        snakeHead = new Tile(5, 5);  // Inicializar la cabeza de la serpiente en la posición (5, 5)
        snakeBody = new ArrayList<>();
        food = new Tile(23, 5);  // Colocar la comida en una posición inicial
        random = new Random();
        placeFood();  // Colocar la comida en una posición aleatoria

        velocidadX = 0;
        velocidadY = 0;

        Timer timerOrigen = new Timer(100, this); //MODIFICAR ESTO PARA AUMENTAR O DISMINUIR LA DIFICULTAD //PARA MODOS MAS DIFICILES EN EL FUTURO
        bucleJuego = timerOrigen; 
        bucleJuego.start();  // Iniciar el ciclo del juego
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);  // Dibuja los elementos en el panel
    }

    public void draw(Graphics g) {
        // Dibuja las líneas que dividen el área de juego en una cuadrícula
        for (int i = 0; i < anchoPanel / tileSize; i++) {
            g.drawLine(i * tileSize, 0, i * tileSize, altoPanel);
            g.drawLine(0, i * tileSize, anchoPanel, i * tileSize);
        }

        // Dibuja la comida (en rojo)
        g.setColor(Color.RED);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        // Dibuja la cabeza de la serpiente (en azul)
        g.setColor(Color.BLUE);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // Dibuja el cuerpo de la serpiente (en azul también)
        for (Tile snakePart : snakeBody) {
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        // Dibuja la puntuación en la parte superior derecha de la pantalla
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score + " | High Score: " + highScore, anchoPanel - 200, 20);
    }

    public void placeFood() {
        // Coloca la comida en una posición aleatoria dentro del área del panel
        food.x = random.nextInt(anchoPanel / tileSize);
        food.y = random.nextInt(altoPanel / tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        // Comprueba si dos "tiles" (celdas) están en la misma posición
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        // Verifica si la serpiente ha comido la comida
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));  // Añade una nueva parte al cuerpo de la serpiente
            score++;  // Incrementa la puntuación
            placeFood();  // Coloca una nueva comida
            if (score > highScore) highScore = score;  // Actualiza el récord si es necesario
        }

        // Mueve el cuerpo de la serpiente
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        // Mueve la cabeza de la serpiente
        snakeHead.x += velocidadX;
        snakeHead.y += velocidadY;

        // Verifica si la cabeza de la serpiente choca con su cuerpo (fin del juego)
        for (Tile snakePart : snakeBody) {
            if (collision(snakeHead, snakePart)) {
                gameOver = true;  // Fin del juego si choca
            }
        }

        // Verifica si la serpiente se ha salido del panel
        if (snakeHead.x < 0 || snakeHead.x >= anchoPanel / tileSize || snakeHead.y < 0 || snakeHead.y >= altoPanel / tileSize) {
            gameOver = true;  // Fin del juego si se sale
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();  // Mueve la serpiente si no ha terminado el juego
            repaint();  // Redibuja el panel
        } else {
            bucleJuego.stop();  // Detiene el bucle del juego
            showGameOverScreen();  // Muestra la pantalla de "Game Over"
        }
    }

    private void showGameOverScreen() {
        // Actualizar puntos del usuario
        SnakeGUI.usuario.agregarPuntosTotales(score);

        // Guardar cambios en la base de datos
        GestorBDSnake gestorBD = new GestorBDSnake();
        if (gestorBD.updateScores(SnakeGUI.usuario.getNombre(), highScore, score)) {
            JOptionPane.showMessageDialog(this, "Puntuaciones actualizadas en la base de datos.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar las puntuaciones en la base de datos.");
        }

        // Mostrar opciones de reinicio o volver al menú
        int choice = JOptionPane.showOptionDialog(
                this,
                "Game Over! Your score: " + score + "\nHigh Score: " + highScore,
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Restart", "Back to Menu"},
                "Restart"
        );

        if (choice == JOptionPane.YES_OPTION) {
            restartGame(); // Reinicia el juego
        } else {
            parentGUI.goToMenu(); // Vuelve al menú principal
        }
    }


    private void restartGame() {
        // Reinicia el estado del juego
        snakeHead = new Tile(5, 5);
        snakeBody.clear();
        score = 0;
        velocidadX = 0;
        velocidadY = 0;
        gameOver = false;
        bucleJuego.start();  // Reinicia el bucle del juego
        placeFood();  // Coloca una nueva comida
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Detecta las teclas presionadas para cambiar la dirección de la serpiente
        if (e.getKeyCode() == KeyEvent.VK_UP && velocidadY != 1) {
            velocidadX = 0;
            velocidadY = -1;  // Cambia la dirección hacia arriba
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocidadY != -1) {
            velocidadX = 0;
            velocidadY = 1;  // Cambia la dirección hacia abajo
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocidadX != -1) {
            velocidadX = 1;
            velocidadY = 0;  // Cambia la dirección hacia la derecha
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocidadX != 1) {
            velocidadX = -1;
            velocidadY = 0;  // Cambia la dirección hacia la izquierda
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
