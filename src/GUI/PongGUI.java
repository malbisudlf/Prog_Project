package GUI;

import Menus.MainMenuGUI;
import Menus.pong.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

import javax.swing.*;

public class PongGUI extends JFrame implements ActionListener{
	
	static final long serialVersionUID = 1L;
	
	private static final int ANCHO = 1080;
    private static final int ALTO = 630;
    private static final int ALTO_VISUAL = ALTO - 37;
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
    private boolean isPaused = true;

    private GamePanel gamePanel;
    private MenuPausa menuPausa;

    public PongGUI() {
    	setTitle("PONG");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(ANCHO, ALTO);
		setLocationRelativeTo(null);
        setResizable(false);
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        gamePanel = new GamePanel();
        gamePanel.setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        add(layeredPane, BorderLayout.CENTER);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        gamePanel.setBounds(0, 0, ANCHO, ALTO);

        addKeyListener(new KeyHandler());
        timer = new Timer(10, this);
        timer.start();
        
        menuPausa = new MenuPausa();
        menuPausa.setBackground(Color.BLACK);
		layeredPane.add(menuPausa, JLayeredPane.PALETTE_LAYER);
		menuPausa.setVisible(false);
		menuPausa.setBounds(0, 0, ANCHO/3, ALTO_VISUAL);
        layeredPane.setVisible(true);
        setVisible(true);
        ShowControles();
    }
    
    public PongGUI(Dificultad dificultad) {
    	setTitle("PONG");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(ANCHO, ALTO);
		setLocationRelativeTo(null);
        setResizable(false);
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        gamePanel = new GamePanel();
        gamePanel.setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        add(layeredPane, BorderLayout.CENTER);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        gamePanel.setBounds(0, 0, ANCHO, ALTO);

        addKeyListener(new KeyHandler());
        timer = new Timer(10, this);
        timer.start();
        
        menuPausa = new MenuPausa();
        menuPausa.setBackground(Color.BLACK);
		layeredPane.add(menuPausa, JLayeredPane.PALETTE_LAYER);
		menuPausa.setVisible(false);
		menuPausa.setBounds(0, 0, ANCHO/3, ALTO_VISUAL);
        layeredPane.setVisible(true);
        setVisible(true);
        ShowControles();
    }

    private void togglePause() {
    	isPaused = !isPaused;
    	menuPausa.setVisible(isPaused);
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
        if (teclasPresionadas.contains(KeyEvent.VK_S) && pala1Y < ALTO_VISUAL - PALA_ALTO) {
            pala1Y += PALA_VELOCIDAD;
        }

        // Controles jugador 2
        if (teclasPresionadas.contains(KeyEvent.VK_UP) && pala2Y > 0) {
            pala2Y -= PALA_VELOCIDAD;
        }
        if (teclasPresionadas.contains(KeyEvent.VK_DOWN) && pala2Y < ALTO_VISUAL - PALA_ALTO) {
            pala2Y += PALA_VELOCIDAD;
        }
    }

    private void checkColisiones() {
    	// Colision con tejado o suelo
        if (bolaY <= 0) {
        	bolaY = 0;
            bolaYDir = -bolaYDir;
        }
        if (bolaY >= ALTO_VISUAL - BOLA_TAMAÑO) {
        	bolaY = ALTO_VISUAL - BOLA_TAMAÑO;
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
            bolaXDir = -bolaVel;
            bolaYDir = 0f;
            puntuacion2++;
        }
        if (bolaX > (ANCHO - BOLA_TAMAÑO)) {
            bolaX = ANCHO / 2 - BOLA_TAMAÑO / 2;
            bolaY = ALTO / 2 - BOLA_TAMAÑO / 2;
            bolaXDir = bolaVel;
            bolaYDir = 0f;
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
    
    private class MenuPausa extends JPanel {
    	
		private static final long serialVersionUID = 1L;

		public MenuPausa() {
	        setLayout(new BorderLayout());
	        setBackground(Color.BLACK);
			
			JLabel titulo = new JLabel("PAUSADO", SwingConstants.CENTER);
			titulo.setForeground(Color.WHITE);
			titulo.setFont(new Font("Arial", Font.BOLD, 50));
			add(titulo, BorderLayout.NORTH);
			
			JPanel botones = new JPanel();
			botones.setLayout(new GridLayout(4,1));
			botones.setBackground(Color.BLACK);
			
			Font fontBotones = new Font("Arial", Font.BOLD, 30);
			
			JButton Reanudar = new JButton("Reanudar");
			Reanudar.setFocusable(false);
			Reanudar.setBackground(Color.BLACK);
			Reanudar.setForeground(Color.WHITE);
			Reanudar.setFont(fontBotones);
			Reanudar.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
					togglePause();
				}
			});
			
			JButton Controles = new JButton("Controles");
			Controles.setFocusable(false);
			Controles.setBackground(Color.BLACK);
			Controles.setForeground(Color.WHITE);
			Controles.setFont(fontBotones);
			Controles.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
					ShowControles();
				}
			});
			
			JButton Restart = new JButton("Restart");
			Restart.setFocusable(false);
			Restart.setBackground(Color.BLACK);
			Restart.setForeground(Color.WHITE);
			Restart.setFont(fontBotones);
			Restart.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
					pala1Y = ALTO / 2 - PALA_ALTO / 2;
				    pala2Y = ALTO / 2 - PALA_ALTO / 2;
				    bolaX = ANCHO / 2 - BOLA_TAMAÑO / 2;
				    bolaY = ALTO / 2 - BOLA_TAMAÑO / 2;
				    bolaXDir = bolaVel;
				    bolaYDir = 0f;
				    
				    puntuacion1 = 0;
				    puntuacion2 = 0;
				    togglePause();
				}
			});
			
			JButton MainMenu = new JButton("Menu Principal");
			MainMenu.setFocusable(false);
			MainMenu.setBackground(Color.BLACK);
			MainMenu.setForeground(Color.WHITE);
			MainMenu.setFont(fontBotones);
			MainMenu.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
					new MainMenuGUI();
					dispose();
				}
			});
			
			botones.add(Reanudar);
			botones.add(Controles);
			botones.add(Restart);
			botones.add(MainMenu);
			add(botones, BorderLayout.CENTER);
    	}
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int tecla = e.getKeyCode();
            teclasPresionadas.add(tecla);
            // Pausar
            if (teclasPresionadas.contains(KeyEvent.VK_SPACE)) {
                togglePause();
            }
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            int tecla = e.getKeyCode();
            teclasPresionadas.remove(tecla);
        }
    }
    
    private void ShowControles() {
		JOptionPane.showMessageDialog(this, "Jugador 1 : W,S\nJugador 2 : Flechas\nPausar/Reanudar : Espacio", "Controles", JOptionPane.INFORMATION_MESSAGE);
	}
}