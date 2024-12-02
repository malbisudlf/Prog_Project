package GUI;

import Menus.pong.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

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
    private static final int PALA_VELOCIDAD = 6;
    
    
    private int pala1Y = ALTO / 2 - PALA_ALTO / 2;
    private int pala2Y = ALTO / 2 - PALA_ALTO / 2;
    private int bolaX = ANCHO / 2 - BOLA_TAMAÑO / 2;
    private int bolaY = ALTO / 2 - BOLA_TAMAÑO / 2;
    private float bolaVel = 3f;
    private float bolaAcel = 0.75f;
    private float bolaXDir = bolaVel;
    private float bolaYDir = 0f;
    
    private final HashSet<Integer> teclasPresionadas = new HashSet<>();
    
    private int puntuacion1 = 0;
    private int puntuacion2 = 0;
    
    //IAG (herramienta: ChatGPT)
	//ADAPTADO (Codigo de ChatGpt adaptado a nuestro proyecto, cogemos la idea del Timer de chatgpt
	//y la trasladamos con algun cambio para que se adapte a nuestra idea.
    private Timer timer;
    private boolean isPaused = false;
    private MenuPausaPong menuPausa;

    private GamePanel gamePanel;

    public PongGUI() {
    	setTitle("PONG");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(ANCHO, ALTO + 65);
		setLocationRelativeTo(null);
        setResizable(false);

        gamePanel = new GamePanel();
        gamePanel.setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        add(gamePanel, BorderLayout.CENTER);

        JButton botonPausa = new JButton("Pausar");
        botonPausa.setFocusable(false);
        botonPausa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        });
        add(botonPausa, BorderLayout.NORTH);

        addKeyListener(new KeyHandler());
        timer = new Timer(10, this);
        timer.start();
        
        menuPausa = new MenuPausaPong();
        menuPausa.setVisible(false);
        setVisible(true);
    }

    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            menuPausa.setVisible(true);
            timer.stop();
        } else {
            menuPausa.setVisible(false); 
            timer.start();              
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isPaused) {
            moverBola();
            moverPalas();
            checkColisiones();
            repaint();
        }
    }

    private void moverBola() {
        bolaX += bolaXDir;
        bolaY += bolaYDir;
    }
    
    private void moverPalas() {
    	// Controles jugador 1
        if (teclasPresionadas.contains(KeyEvent.VK_W) && pala1Y > 0) {
            pala1Y -= PALA_VELOCIDAD;
        }
        if (teclasPresionadas.contains(KeyEvent.VK_S) && pala1Y < ALTO - PALA_ALTO) {
            pala1Y += PALA_VELOCIDAD;
        }

        // Controles jugador 2
        if (teclasPresionadas.contains(KeyEvent.VK_UP) && pala2Y > 0) {
            pala2Y -= PALA_VELOCIDAD;
        }
        if (teclasPresionadas.contains(KeyEvent.VK_DOWN) && pala2Y < ALTO - PALA_ALTO) {
            pala2Y += PALA_VELOCIDAD;
        }
    }

    private void checkColisiones() {
    	// Colision con tejado o suelo
        if (bolaY <= 0) {
        	bolaY = 0;
            bolaYDir = -bolaYDir;
        }
        if (bolaY >= ALTO - BOLA_TAMAÑO) {
        	bolaY = ALTO - BOLA_TAMAÑO;
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

        // Resetear la bola si se sale de los lados y cambiar puntuacion
        if (bolaX < 0) {
            bolaX = ANCHO / 2 - BOLA_TAMAÑO / 2;
            bolaY = ALTO / 2 - BOLA_TAMAÑO / 2;
            bolaXDir = -bolaXDir;
            bolaXDir = bolaVel;
            bolaYDir = bolaVel;
            puntuacion2++;
        }
        if (bolaX > (ANCHO - BOLA_TAMAÑO)) {
            bolaX = ANCHO / 2 - BOLA_TAMAÑO / 2;
            bolaY = ALTO / 2 - BOLA_TAMAÑO / 2;
            bolaXDir = -bolaXDir;
            bolaXDir = bolaVel;
            bolaYDir = bolaVel;
            puntuacion1++;
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
           
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(new Font("Arial", Font.BOLD, 70));
            g2d.setColor(Color.WHITE);
            
            g2d.drawString((puntuacion1 + "  -  " + puntuacion2), ANCHO/2 - 95, 70);
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int tecla = e.getKeyCode();
            teclasPresionadas.add(tecla);
            System.out.println("Se ha pulsado la tecla" + tecla);
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            int tecla = e.getKeyCode();
            teclasPresionadas.remove(tecla);
            System.out.println("Se ha soltado la tecla" + tecla);
        }
    }
}
