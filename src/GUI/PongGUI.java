package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PongGUI extends JFrame implements ActionListener{
	
	static final long serialVersionUID = 1L;
	
	private static final int ANCHO = 1080;
    private static final int ALTO = 540;
    private static final int PALA_DESP_I = 20;
    private static final int PALA_DESP_D = PALA_DESP_I + 20;
    private static final int PALA_ANCHO = 10;
    private static final int PALA_ALTO = 150;
    private static final int BOLA_TAMAÑO = 20;
    private static final int PALA_VELOCIDAD = 10;
    
    
    private int pala1Y = ALTO / 2 - PALA_ALTO / 2;
    private int pala2Y = ALTO / 2 - PALA_ALTO / 2;
    private int bolaX = ANCHO / 2 - BOLA_TAMAÑO / 2;
    private int bolaY = ALTO / 2 - BOLA_TAMAÑO / 2;
    private float bolaVel = 3f;
    private float bolaAcel = 0.75f;
    private float bolaXDir = bolaVel;
    private float bolaYDir = 0f;

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
    	// Colision con tejado o suelo
        if (bolaY <= 0) {
        	bolaY = 0;
            bolaYDir = -bolaYDir;
        }
        if (bolaY >= ALTO - (2 * BOLA_TAMAÑO)) {
        	bolaY = ALTO - (2 * BOLA_TAMAÑO);
        	bolaYDir = -bolaYDir;
        }

        // Colision con palas
        if (bolaX <= (PALA_DESP_I + PALA_ANCHO) && bolaY >= pala1Y && bolaY <= pala1Y + PALA_ALTO) {
            bolaXDir = -bolaXDir + bolaAcel;
            float a = ((float)((pala1Y + (PALA_ALTO/2)) - bolaY) / (PALA_ALTO/2));
            bolaYDir = -bolaXDir * a;
        }
        if (bolaX >= ANCHO - (PALA_DESP_D + BOLA_TAMAÑO) && bolaY >= pala2Y && bolaY <= pala2Y + PALA_ALTO) {
            bolaXDir = -bolaXDir - bolaAcel;
            float a = ((float)((pala2Y + (PALA_ALTO/2)) - bolaY) / (PALA_ALTO/2));
            bolaYDir = bolaXDir * a;
        }

        // Resetear la bola si se sale de los lados
        if (bolaX < 0 || bolaX > ANCHO) {
            bolaX = ANCHO / 2 - BOLA_TAMAÑO / 2;
            bolaY = ALTO / 2 - BOLA_TAMAÑO / 2;
            bolaXDir = -bolaXDir;
            bolaXDir = bolaVel;
            bolaYDir = bolaVel;
        }
    }
    
    private class GamePanel extends JPanel {

		private static final long serialVersionUID = 1L;

		@Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            
            g.fillRect(PALA_DESP_I, pala1Y, PALA_ANCHO, PALA_ALTO);
            g.fillRect(ANCHO - PALA_DESP_D, pala2Y, PALA_ANCHO, PALA_ALTO);
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
