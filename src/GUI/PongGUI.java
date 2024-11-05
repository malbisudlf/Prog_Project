package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PongGUI extends JFrame implements ActionListener{
	
	static final long serialVersionUID = 1L;
	
	private static final int ANCHO = 1080;
    private static final int ALTO = 540;
    private static final int PALA_ANCHO = 10;
    private static final int PALA_ALTO = 150;
    private static final int BOLA_TAMAÑO = 20;
    private static final int PALA_VELOCIDAD = 10;
    private static final int BOLA_VELOCIDAD = 5;

    private int pala1Y = ALTO / 2 - PALA_ALTO / 2;
    private int pala2Y = ALTO / 2 - PALA_ALTO / 2;
    private int bolaX = ANCHO / 2 - BOLA_TAMAÑO / 2;
    private int bolaY = ALTO / 2 - BOLA_TAMAÑO / 2;
    private int bolaXDir = BOLA_VELOCIDAD;
    private int bolaYDir = BOLA_VELOCIDAD;

    private Timer timer;
    
    private GamePanel gamePanel;

    public PongGUI() {
    	setTitle("PONG");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(ANCHO, ALTO);
        setResizable(false);
        
        gamePanel = new GamePanel();
        gamePanel.setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        add(gamePanel, BorderLayout.CENTER);
        
        addKeyListener(new KeyHandler());
        timer = new Timer(10, this);
        timer.start();
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moverBola();
        checkColisiones();
        repaint();
    }

    private void moverBola() {
        bolaX += bolaXDir;
        bolaY += bolaYDir;
    }

    private void checkColisiones() {
        if (bolaY <= 0 || bolaY >= ALTO - BOLA_TAMAÑO) {
            bolaYDir = -bolaYDir;
        }

        // Colision con palas
        if (bolaX <= 30 && bolaY >= pala1Y && bolaY <= pala1Y + PALA_ALTO) {
            bolaXDir = -bolaXDir;
        }
        if (bolaX >= ANCHO - 50 && bolaY >= pala2Y && bolaY <= pala2Y + PALA_ALTO) {
            bolaXDir = -bolaXDir;
        }

        // Resetear la bola si se sale de los lados
        if (bolaX < 0 || bolaX > ANCHO) {
            bolaX = ANCHO / 2 - BOLA_TAMAÑO / 2;
            bolaY = ALTO / 2 - BOLA_TAMAÑO / 2;
            bolaXDir = -bolaXDir;
        }
    }
    
    private class GamePanel extends JPanel {

		private static final long serialVersionUID = 1L;

		@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            
            g.fillRect(20, pala1Y, PALA_ANCHO, PALA_ALTO);
            g.fillRect(ANCHO - 30, pala2Y, PALA_ANCHO, PALA_ALTO);
            g.fillOval(bolaX, bolaY, BOLA_TAMAÑO, BOLA_TAMAÑO);
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            // Controles jugador 1
            if (key == KeyEvent.VK_W && pala1Y > 0) {
                pala1Y -= PALA_VELOCIDAD;
            }
            if (key == KeyEvent.VK_S && pala1Y < ALTO - PALA_ALTO) {
                pala1Y += PALA_VELOCIDAD;
            }

            // Controles jugador 2
            if (key == KeyEvent.VK_UP && pala2Y > 0) {
                pala2Y -= PALA_VELOCIDAD;
            }
            if (key == KeyEvent.VK_DOWN && pala2Y < ALTO - PALA_ALTO) {
                pala2Y += PALA_VELOCIDAD;
            }
        }
    }
}
