package GUI;

import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class AhorcadoGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] palabras = {"J A V A", "P R U E B A", "A H O R C A D O", "C L A S E", "O B J E T O"};
    private String palabra;
    private char[] palabraAdivinada;  
    private int intentosRestantes = 6;  
    private JLabel labelPalabra;  
    private JLabel labelIntentos;  
    private JTextField inputLetra;  
    private DibujoAhorcado panelAhorcado;  

    public AhorcadoGUI() {
    	// Lista de palabras para el juego
    	this.setVisible(true);
    	
    	Random random = new Random();
    	palabra = palabras[random.nextInt(palabras.length)];

        // Inicializa el J-Frame
        setTitle("Juego del Ahorcado");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        
        getContentPane().setBackground(Color.BLACK);

        // Inicializa el array de la palabra a adivinar
        palabraAdivinada = new char[palabra.length()];
        for (int i = 0; i < palabra.length(); i++) {
        	if (i % 2 == 0) {
            palabraAdivinada[i] = '_';
        	} else {
        		palabraAdivinada[i] = ' ';
        	}
        }

        // Panel superior para mostrar la palabra
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(Color.BLACK);
        labelPalabra = new JLabel(String.valueOf(palabraAdivinada));
        labelPalabra.setForeground(Color.GREEN);
        panelSuperior.add(labelPalabra);
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central para el dibujo del ahorcado
        panelAhorcado = new DibujoAhorcado();
        add(panelAhorcado, BorderLayout.CENTER);

        // Panel inferior para los controles
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(Color.BLACK);
        panelInferior.setLayout(new FlowLayout());
        labelPalabra.setBackground(Color.BLACK);
        labelPalabra.setForeground(Color.GREEN);

        inputLetra = new JTextField(5);
        inputLetra.setBackground(Color.BLACK);
        inputLetra.setForeground(Color.GREEN);
        panelInferior.add(inputLetra);

        JButton botonVerificar = new JButton("Verificar");
        botonVerificar.setBackground(Color.BLACK);
        botonVerificar.setForeground(Color.GREEN);
        panelInferior.add(botonVerificar);

        labelIntentos = new JLabel("Intentos restantes: " + intentosRestantes);
        labelIntentos.setForeground(Color.GREEN);
        panelInferior.add(labelIntentos);

        add(panelInferior, BorderLayout.SOUTH);

        ActionListener verificarAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarLetra(inputLetra.getText().toUpperCase());
                inputLetra.setText("");  // Limpia el campo de texto
            }
        };
        botonVerificar.addActionListener(verificarAction);
        
        KeyListener keylistener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					verificarAction.actionPerformed(null);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        };
        inputLetra.addKeyListener(keylistener);
    }

    private void verificarLetra(String letra) {
        if (letra.length() != 1) {
            JOptionPane.showMessageDialog(this, "Introduce solo una letra");
            return;
        }

        boolean acierto = false;
        char letraChar = letra.charAt(0);
        for (int i = 0; i < palabra.length(); i++) {
            if (palabra.charAt(i) == letraChar) {
                palabraAdivinada[i] = letraChar;
                acierto = true;
            }
        }

        if (!acierto) {
            intentosRestantes--;
        }

        labelPalabra.setText(String.valueOf(palabraAdivinada));
        labelIntentos.setText("Intentos restantes: " + intentosRestantes);
        
        // Actualiza el dibujo del ahorcado
        panelAhorcado.repaint();

        // Comprueba si ha ganado o perdido
        if (String.valueOf(palabraAdivinada).equals(palabra)) {
            JOptionPane.showMessageDialog(this, "¡Has ganado!");
            reiniciarJuego();
        } else if (intentosRestantes == 0) {
            JOptionPane.showMessageDialog(this, "¡Has perdido! La palabra era: " + palabra);
            reiniciarJuego();
        }
    }

    private void reiniciarJuego() {
        // Reinicia el juego
    	Random random = new Random();
    	palabra = palabras[random.nextInt(palabras.length)];
    	
    	palabraAdivinada = new char[palabra.length()];
    	
        intentosRestantes = 6;
        for (int i = 0; i < palabra.length(); i++) {
        	if (i % 2 == 0) {
            palabraAdivinada[i] = '_';
        	} else {
        		palabraAdivinada[i] = ' ';
        	}
        }
        labelPalabra.setText(String.valueOf(palabraAdivinada));
        labelIntentos.setText("Intentos restantes: " + intentosRestantes);
        panelAhorcado.repaint();
    }

    // Clase para el panel donde se dibujará el ahorcado
    private class DibujoAhorcado extends JPanel {
    	
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            setBackground(Color.BLACK);
            
            g.setColor(Color.GREEN);

            // Dibuja la estructura del ahorcado
            g.drawLine(50, 250, 150, 250);  // Base
            g.drawLine(100, 250, 100, 50);  // Poste vertical
            g.drawLine(100, 50, 200, 50);   // Poste horizontal
            g.drawLine(200, 50, 200, 100);  // Cuerda

            // Dibuja partes del cuerpo según los intentos restantes
            if (intentosRestantes <= 5) {
                g.drawOval(175, 100, 50, 50);  // Cabeza
            }
            if (intentosRestantes <= 4) {
                g.drawLine(200, 150, 200, 200);  // Cuerpo
            }
            if (intentosRestantes <= 3) {
                g.drawLine(200, 160, 170, 180);  // Brazo izquierdo
            }
            if (intentosRestantes <= 2) {
                g.drawLine(200, 160, 230, 180);  // Brazo derecho
            }
            if (intentosRestantes <= 1) {
                g.drawLine(200, 200, 170, 230);  // Pierna izquierda
            }
            if (intentosRestantes <= 0) {
                g.drawLine(200, 200, 230, 230);  // Pierna derecha
            }
        }
       }
    }
    

