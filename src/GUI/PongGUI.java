package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Menus.MainMenuGUI;
import Menus.pong.Dificultad;

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
    private static final int ALTO_REBOTE = ALTO_VISUAL - BOLA_TAMAÑO;
    private static final int PALA_VELOCIDAD = 8;
    private static final int BOLA_VELOCIDAD = 5;
    
    // Coordenadas reales (Esquina superior izquierda) de los objetos
    private int pala1Y = (ALTO_VISUAL / 2) - (PALA_ALTO / 2);
    private int pala2Y = (ALTO_VISUAL / 2) - (PALA_ALTO / 2);
    private int bolaX = (ANCHO / 2) - (BOLA_TAMAÑO / 2);
    private int bolaY = (ALTO_VISUAL / 2) - (BOLA_TAMAÑO / 2);
    
    // Coordenadas de la mitad de los objetos
    private int pala1Ymed = ALTO_VISUAL / 2;
    private int pala2Ymed = ALTO_VISUAL / 2;
    private int bolaYmed = ALTO_VISUAL / 2;
    
    private int bolaVel = BOLA_VELOCIDAD;
    private int bolaBotes = 0;
    private int bolaAcel = 1;
    private int bolaXDir = bolaVel;
    private int bolaYDir = 0;
    
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
	private Dificultad dificultad;
	private ArrayList<ArrayList<Integer>> camino;

    public PongGUI() {
    	this.dificultad = null;
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
        ShowControlesPvP();
    }
    
    public PongGUI(Dificultad dificultad) {
    	this.dificultad = dificultad;
    	camino = new ArrayList<ArrayList<Integer>>();
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
        ShowControlesPvC();
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
        bolaYmed += bolaYDir;
    }
    
    private void moverPalas() {
    	// Controles jugador 1
        if (teclasPresionadas.contains(KeyEvent.VK_W) && pala1Y > 0) {
            pala1Y -= PALA_VELOCIDAD;
            pala1Ymed -= PALA_VELOCIDAD;
        }
        if (teclasPresionadas.contains(KeyEvent.VK_S) && pala1Y < ALTO_VISUAL - PALA_ALTO) {
            pala1Y += PALA_VELOCIDAD;
            pala1Ymed += PALA_VELOCIDAD;
        }
        
        if (dificultad == null) {
	        // Controles jugador 2
	        if (teclasPresionadas.contains(KeyEvent.VK_UP) && pala2Y > 0) {
	            pala2Y -= PALA_VELOCIDAD;
	            pala2Ymed -= PALA_VELOCIDAD;
	        }
	        if (teclasPresionadas.contains(KeyEvent.VK_DOWN) && pala2Y < ALTO_VISUAL - PALA_ALTO) {
	            pala2Y += PALA_VELOCIDAD;
	            pala2Ymed += PALA_VELOCIDAD;
	        }
        }
        else {
	        if (teclasPresionadas.contains(KeyEvent.VK_UP) && pala1Y > 0) {
	            pala1Y -= PALA_VELOCIDAD;
	            pala1Ymed -= PALA_VELOCIDAD;
	        }
	        if (teclasPresionadas.contains(KeyEvent.VK_DOWN) && pala1Y < ALTO_VISUAL - PALA_ALTO) {
	            pala1Y += PALA_VELOCIDAD;
	            pala1Ymed += PALA_VELOCIDAD;
	        }
	        if (dificultad == Dificultad.Facil && bolaXDir > 0) {
	        	if (pala2Ymed < bolaYmed && pala2Y < ALTO_VISUAL - PALA_ALTO) {
	        		pala2Y += PALA_VELOCIDAD;
	        		pala2Ymed += PALA_VELOCIDAD;
	        	}
	        	if (pala2Ymed > bolaYmed && pala2Y > 0) {
	        		pala2Y -= PALA_VELOCIDAD;
	        		pala2Ymed -= PALA_VELOCIDAD;
	        	}
	        }
	        if (dificultad == Dificultad.Normal && bolaXDir > 0) {
	        	if (pala2Ymed < bolaYmed && pala2Y < ALTO_VISUAL - PALA_ALTO) {
	        		pala2Y += PALA_VELOCIDAD;
	        		pala2Ymed += PALA_VELOCIDAD;
	        	}
	        	if (pala2Ymed > bolaYmed && pala2Y > 0) {
	        		pala2Y -= PALA_VELOCIDAD;
	        		pala2Ymed -= PALA_VELOCIDAD;
	        	}
	        }
	        if (dificultad == Dificultad.Dificil && !camino.isEmpty()) {
	        	if (pala2Ymed < camino.getLast().getLast() && pala2Y < ALTO_VISUAL - PALA_ALTO) {
	        		pala2Y += PALA_VELOCIDAD;
	        		pala2Ymed += PALA_VELOCIDAD;
	        	}
	        	if (pala2Ymed > camino.getLast().getLast() && pala2Y > 0) {
	        		pala2Y -= PALA_VELOCIDAD;
	        		pala2Ymed -= PALA_VELOCIDAD;
	        	}
	        }
	        else {
	        	if (pala2Ymed < bolaYmed && pala2Y < ALTO_VISUAL - PALA_ALTO) {
	        		pala2Y += PALA_VELOCIDAD;
	        		pala2Ymed += PALA_VELOCIDAD;
	        	}
	        	if (pala2Ymed > bolaYmed && pala2Y > 0) {
	        		pala2Y -= PALA_VELOCIDAD;
	        		pala2Ymed -= PALA_VELOCIDAD;
	        	}
	        }
        }
    }

    private void checkColisiones() {
    	// Colision con tejado o suelo
        if (bolaY <= 0) {
        	bolaY = 0;
            bolaYDir = -bolaYDir;
        }
        if (bolaY >= ALTO_REBOTE) {
        	bolaY = ALTO_REBOTE;
        	bolaYDir = -bolaYDir;
        }

        // Colision con pala 1
        if (bolaX <= (PALA_DESP_I + PALA_ANCHO) && bolaY >= pala1Y && bolaY <= pala1Y + PALA_ALTO) {
        	bolaBotes++;
        	bolaXDir = -bolaXDir;
        	if (bolaBotes == 3) {
        		bolaVel += bolaAcel;
        		bolaXDir += bolaAcel;
        		bolaBotes = 0;
        	}
            float a = ((float)(bolaYmed - pala1Ymed) / (PALA_ALTO/3));
            bolaYDir = Math.round(bolaVel * a);
            if (dificultad == Dificultad.Dificil) {
            	camino = calcularCaminoD();
            }
        }
        // Colision con pala 2
        if (bolaX >= ANCHO - (PALA_DESP_D + BOLA_TAMAÑO) && bolaY >= pala2Y && bolaY <= pala2Y + PALA_ALTO) {
        	bolaBotes++;
        	bolaXDir = -bolaXDir;
        	if (bolaBotes == 3) {
        		bolaVel += bolaAcel;
        		bolaXDir -= bolaAcel;
        		bolaBotes = 0;
        	}
            float a = ((float)(bolaYmed - pala2Ymed) / (PALA_ALTO/3));
            bolaYDir = Math.round(-bolaVel * a);
            if (dificultad == Dificultad.Facil) {
            }
        }

        // Resetear la bola si se sale de los lados y cambiar puntuacion
        if (bolaX < 0) {
            bolaX = ANCHO / 2 - BOLA_TAMAÑO / 2;
            bolaY = ALTO / 2 - BOLA_TAMAÑO / 2;
            bolaVel = BOLA_VELOCIDAD;
            bolaXDir = -bolaVel;
            bolaYDir = 0;
            puntuacion2++;
            camino = new ArrayList<ArrayList<Integer>>();
        }
        if (bolaX > (ANCHO - BOLA_TAMAÑO)) {
            bolaX = ANCHO / 2 - BOLA_TAMAÑO / 2;
            bolaY = ALTO / 2 - BOLA_TAMAÑO / 2;
            bolaVel = BOLA_VELOCIDAD;
            bolaXDir = bolaVel;
            bolaYDir = 0;
            puntuacion1++;
            camino = new ArrayList<ArrayList<Integer>>();
        }
    }
    
    private ArrayList<ArrayList<Integer>> calcularCaminoD() {
    	ArrayList<ArrayList<Integer>> camino = new ArrayList<ArrayList<Integer>>();
    	int distanciaX = ANCHO - (PALA_DESP_I + PALA_ANCHO + PALA_DESP_D + BOLA_TAMAÑO);
    	int tiempoTotal = distanciaX / bolaXDir;
    	int distanciaY = Math.round(bolaY + (tiempoTotal * bolaYDir));
    	int secciones = distanciaY / ALTO_REBOTE;
    	int tiempoY;
    	int x;
    	int y;
    	// Caso 1: Bola se dirige hacia abajo
    	if (bolaYDir > 0) {
    		secciones++;
    		for (int i = 0; i < secciones; i++) {
    			camino.add(new ArrayList<Integer>());
    			// Primera Seccion (hasta el primer rebote)
        		if (i == 0) {
            		camino.getFirst().add(PALA_DESP_I + PALA_ANCHO);
            		camino.getFirst().add(bolaYmed);
            		tiempoY = Math.abs(Math.round((ALTO_REBOTE - bolaY) / bolaYDir));
        		} // Secciones 1, n-1 (del primer al ultimo rebote) 
        		else {
        			camino.get(i).add(camino.get(i-1).get(2));
        			camino.get(i).add(camino.get(i-1).getLast());
        			tiempoY = Math.abs(Math.round(ALTO_REBOTE / bolaYDir));
        		}
        		x = Math.round(camino.get(i).get(0) - (BOLA_TAMAÑO / 2) + (tiempoY * bolaXDir));
        		if ((i % 2) == 0) {
        			y = ALTO_REBOTE + (BOLA_TAMAÑO / 2);
        		}
        		else {
        			y = BOLA_TAMAÑO / 2;
        		}
        		// Seccion n (del ultimo rebote hasta la pala contraria)
        		if (i + 1 == secciones) {
        			camino.get(i).add(ANCHO - PALA_DESP_D);
        			if ((i % 2) == 0) {
        				y = (distanciaY % ALTO_REBOTE) + (BOLA_TAMAÑO / 2);
            		}
            		else {
            			y = ALTO_REBOTE - (distanciaY % ALTO_REBOTE) + (BOLA_TAMAÑO / 2);
            		}
        			camino.get(i).add(y);
        		}
        		else {
        			camino.get(i).add(x + (BOLA_TAMAÑO / 2));
        			camino.get(i).add(y);
        		}
        	}
    	}
    	//Caso 2: Bola se dirige hacia arriba
    	if (bolaYDir < 0) {
    		secciones = Math.abs(secciones) + 1;
    		if (distanciaY < 0) {
    			secciones++;
    		}
			for (int i = 0; i < secciones; i++) {
    			camino.add(new ArrayList<Integer>());
    			// Primera Seccion (hasta el primer rebote)
        		if (i == 0) {
            		camino.getFirst().add(PALA_DESP_I + PALA_ANCHO);
            		camino.getFirst().add(bolaYmed);
            		tiempoY = Math.abs(Math.round((bolaY) / bolaYDir));
        		} // Secciones 1, n-1 (del primer al ultimo rebote) 
        		else {
        			camino.get(i).add(camino.get(i-1).get(2));
        			camino.get(i).add(camino.get(i-1).getLast());
        			tiempoY = Math.abs(Math.round(ALTO_REBOTE / bolaYDir));
        		}
        		x = Math.round(camino.get(i).get(0) + (tiempoY * bolaXDir));
        		if ((i % 2) == 0) {
        			y = 0;
        		}
        		else {
        			y = ALTO_REBOTE;
        		}
        		// Seccion n (del ultimo rebote hasta la pala contraria)
        		if (i + 1 == secciones) {
        			camino.get(i).add(ANCHO - PALA_DESP_D);
        			if ((i % 2) == 0) {
        				y = distanciaY % ALTO_REBOTE;
            		}
            		else {
            			y = ALTO_REBOTE - (distanciaY % ALTO_REBOTE);
            		}
        			camino.get(i).add(y);
        		}
        		else {
        			camino.get(i).add(x);
        			camino.get(i).add(y);
        		}
        	}
    	}
    	if (bolaYDir == 0) {
    		camino.add(new ArrayList<Integer>());
    		camino.getFirst().add(PALA_DESP_I + PALA_ANCHO);
    		camino.getFirst().add(bolaYmed);
    		camino.getFirst().add(ANCHO - PALA_DESP_D);
    		camino.getFirst().add(bolaYmed);
    	}
    	System.out.println(camino);
    	return camino;
	}
    
    private ArrayList<ArrayList<Integer>> calcularCaminoI() {
    	ArrayList<ArrayList<Integer>> camino = new ArrayList<ArrayList<Integer>>();
		return camino;
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
            if (camino != null) {
            	for (ArrayList<Integer> seccion : camino) {
                	g.drawLine(seccion.get(0), seccion.get(1), seccion.get(2), seccion.get(3));
                }
            }
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
					if (dificultad == null) {
						ShowControlesPvP();
					} else {
						ShowControlesPvC();
					}
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
					pala1Y = (ALTO_VISUAL / 2) - (PALA_ALTO / 2);
				    pala2Y = (ALTO_VISUAL / 2) - (PALA_ALTO / 2);
				    bolaX = (ANCHO / 2) - (BOLA_TAMAÑO / 2);
				    bolaY = (ALTO_VISUAL / 2) - (BOLA_TAMAÑO / 2);
				    
				    pala1Ymed = ALTO_VISUAL / 2;
				    pala2Ymed = ALTO_VISUAL / 2;
				    bolaYmed = ALTO_VISUAL / 2;
				    
				    bolaVel = 5;
				    bolaBotes = 0;
				    bolaXDir = bolaVel;
				    bolaYDir = 0;
				    
				    puntuacion1 = 0;
				    puntuacion2 = 0;
				    
				    camino = new ArrayList<ArrayList<Integer>>();
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
    
    private void ShowControlesPvP() {
		JOptionPane.showMessageDialog(this, "Jugador 1 : W,S\nJugador 2 : Flechas\nPausar/Reanudar : Espacio", "Controles", JOptionPane.INFORMATION_MESSAGE);
	}
    
    private void ShowControlesPvC() {
		JOptionPane.showMessageDialog(this, "Jugador : W,S - Flechas\nPausar/Reanudar : Espacio", "Controles", JOptionPane.INFORMATION_MESSAGE);
	}
}