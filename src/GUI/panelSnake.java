package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class panelSnake extends JPanel implements ActionListener, KeyListener {

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
    SnakeGUI parentGUI;  // Referencia a SnakeGUI

    panelSnake(int anchoPanel, int altoPanel, SnakeGUI parentGUI) {
        this.anchoPanel = anchoPanel;
        this.altoPanel = altoPanel;
        this.parentGUI = parentGUI;  // Guardar referencia
        setPreferredSize(new Dimension(this.anchoPanel, this.altoPanel));
        setBackground(Color.GREEN);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<>();
        food = new Tile(23, 5);
        random = new Random();
        placeFood();

        velocidadX = 0;
        velocidadY = 0;
        bucleJuego = new Timer(100, this);
        bucleJuego.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < anchoPanel / tileSize; i++) {
            g.drawLine(i * tileSize, 0, i * tileSize, altoPanel);
            g.drawLine(0, i * tileSize, anchoPanel, i * tileSize);
        }

        g.setColor(Color.RED);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        g.setColor(Color.BLUE);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        for (Tile snakePart : snakeBody) {
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score + " | High Score: " + highScore, anchoPanel - 200, 20);
    }

    public void placeFood() {
        food.x = random.nextInt(anchoPanel / tileSize);
        food.y = random.nextInt(altoPanel / tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            score++;
            placeFood();
            if (score > highScore) highScore = score;
        }

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

        snakeHead.x += velocidadX;
        snakeHead.y += velocidadY;

        for (Tile snakePart : snakeBody) {
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        if (snakeHead.x < 0 || snakeHead.x >= anchoPanel / tileSize || snakeHead.y < 0 || snakeHead.y >= altoPanel / tileSize) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            repaint();
        } else {
            bucleJuego.stop();
            showGameOverScreen();
        }
    }

    private void showGameOverScreen() {
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
            restartGame();
        } else {
            parentGUI.goToMenu();
        }
    }

    private void restartGame() {
        snakeHead = new Tile(5, 5);
        snakeBody.clear();
        score = 0;
        velocidadX = 0;
        velocidadY = 0;
        gameOver = false;
        bucleJuego.start();
        placeFood();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocidadY != 1) {
            velocidadX = 0;
            velocidadY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocidadY != -1) {
            velocidadX = 0;
            velocidadY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocidadX != -1) {
            velocidadX = 1;
            velocidadY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocidadX != 1) {
            velocidadX = -1;
            velocidadY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
