package GUI;

import java.util.Random;
import javax.swing.*;

import Menus.menuAhorcado;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;



public class AhorcadoGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] palabras = {"J A V A", "P R U E B A", "A H O R C A D O", "C L A S E", "O B J E T O"};
    private String palabra;
    private char[] palabraAdivinada;  
    private int intentosRestantes = 6;  
    private int score = 0;
    public int scores;
    public String nombres;
    private JLabel labelPalabra;  
    private JLabel labelIntentos; 
    private JLabel palabrasMal;
    private JLabel labelScore;
    private JTextField inputLetra;  
    private DibujoAhorcado panelAhorcado;  

    public AhorcadoGUI() {
    	
    	this.setVisible(true);
    	
    	Random random = new Random();
    	palabra = palabras[random.nextInt(palabras.length)];

        
        setTitle("Juego del Ahorcado");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        
        getContentPane().setBackground(Color.BLACK);

        // IAG (Chat GPT)
        // Sin cambios. Le explicamos nuestro proyecto y situación, y nos devolvió el código para pasar de la palabra a las _
        palabraAdivinada = new char[palabra.length()];
        for (int i = 0; i < palabra.length(); i++) {
        	if (i % 2 == 0) {
            palabraAdivinada[i] = '_';
        	} else {
        		palabraAdivinada[i] = ' ';
        	}
        }
        //La ayuda de la IAG es hasta aquí
       
        
        JPanel panelDerecha = new JPanel();
        panelDerecha.setBackground(Color.BLACK);
        palabrasMal = new JLabel();
        palabrasMal.setForeground(Color.WHITE);
        panelDerecha.add(palabrasMal);
        add(panelDerecha, BorderLayout.EAST);
        JLabel puntuacion = new JLabel("0");
        puntuacion.setBackground(Color.BLACK);
        puntuacion.setForeground(Color.WHITE);
        
        
        
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(Color.BLACK);
        labelPalabra = new JLabel(String.valueOf(palabraAdivinada));
        labelPalabra.setForeground(Color.WHITE);
        panelSuperior.add(labelPalabra);
        add(panelSuperior, BorderLayout.NORTH);
        
        JButton volverbutton = new JButton("Finalizar Partida");
        volverbutton.setBackground(Color.BLACK);
        volverbutton.setForeground(Color.WHITE);
        volverbutton.setFocusable(false);
        
        
        volverbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int salida = JOptionPane.showConfirmDialog(null, "Seguro que quieres acabar la partida?", "ATENCION", JOptionPane.YES_NO_OPTION );
				if(salida == JOptionPane.YES_OPTION) {
					String nombre = JOptionPane.showInputDialog("Si quieres guarar tu puntuación, ingresa tu nombre");
					if (nombre != null && !nombre.trim().isEmpty()) {
						saveScoreToFile(nombre, score);
		                
		            } else {
		            	
		            	
		            	
		                
		            }
					new menuAhorcado();
					dispose();
			}
				
					
				}
			
			
        	
        });
        
        
        
        JPanel panelIzquierda = new JPanel();
        panelIzquierda.setBackground(Color.BLACK);
        panelIzquierda.add(volverbutton, BorderLayout.NORTH);
        add(panelIzquierda, BorderLayout.WEST);
        panelIzquierda.add(puntuacion, BorderLayout.NORTH);

        
        panelAhorcado = new DibujoAhorcado();
        add(panelAhorcado, BorderLayout.CENTER);
        

        
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(Color.BLACK);
        panelInferior.setLayout(new FlowLayout());
        labelPalabra.setBackground(Color.BLACK);
        labelPalabra.setForeground(Color.WHITE);

        inputLetra = new JTextField(5);
        inputLetra.setBackground(Color.BLACK);
        inputLetra.setForeground(Color.WHITE);
        panelInferior.add(inputLetra);

        JButton botonVerificar = new JButton("Verificar");
        botonVerificar.setBackground(Color.BLACK);
        botonVerificar.setForeground(Color.WHITE);
        panelInferior.add(botonVerificar);

        labelIntentos = new JLabel("Intentos restantes: " + intentosRestantes);
        labelIntentos.setForeground(Color.WHITE);
        panelInferior.add(labelIntentos);

        labelScore = new JLabel("Score: " + score);
        labelScore.setForeground(Color.WHITE);
        panelInferior.add(labelScore);
        
        add(panelInferior, BorderLayout.SOUTH);

        ActionListener verificarAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarLetra(inputLetra.getText().toUpperCase());
                inputLetra.setText("");  
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
                score += 10;
            } 
        }

        if (!acierto) {
            intentosRestantes--;
            palabrasMal.setText(palabrasMal.getText() + " " + letraChar);
            score--;
        }

        labelPalabra.setText(String.valueOf(palabraAdivinada));
        labelIntentos.setText("Intentos restantes: " + intentosRestantes);
        labelScore.setText("Score: " + score);
        
        
        panelAhorcado.repaint();

        
        if (String.valueOf(palabraAdivinada).equals(palabra)) {
            JOptionPane.showMessageDialog(this, "¡Has ganado!");
            reiniciarJuego();
        } else if (intentosRestantes == 0) {
            JOptionPane.showMessageDialog(this, "¡Has perdido! La palabra era: " + palabra);
            reiniciarJuego();
        }
    }

	private void saveScoreToFile(String nombre, int score) {
	    try (FileWriter writer = new FileWriter("leaderboard.txt", true)) {
	        writer.write(nombre + " " + score + "\n");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

    private void reiniciarJuego() {
        
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
        palabrasMal.setText(null);
        labelIntentos.setText("Intentos restantes: " + intentosRestantes);
        
        panelAhorcado.repaint();
    }

    //IAG (Chat GPT)
    //Adaptado a nuestro proyecto. Lo obtenido por la IAG fue las coordenadas de dibujado, el resto fue nuestra adaptación
    private class DibujoAhorcado extends JPanel {
    	
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            setBackground(Color.BLACK);
            
            g.setColor(Color.WHITE);

            
            g.drawLine(50, 250, 150, 250);  
            g.drawLine(100, 250, 100, 50);  
            g.drawLine(100, 50, 200, 50);   
            g.drawLine(200, 50, 200, 100);  

            
            if (intentosRestantes <= 5) {
                g.drawOval(175, 100, 50, 50);  
            }
            if (intentosRestantes <= 4) {
                g.drawLine(200, 150, 200, 200);  
            }
            if (intentosRestantes <= 3) {
                g.drawLine(200, 160, 170, 180);  
            }
            if (intentosRestantes <= 2) {
                g.drawLine(200, 160, 230, 180);  
            }
            if (intentosRestantes <= 1) {
                g.drawLine(200, 200, 170, 230);  
            }
            if (intentosRestantes <= 0) {
                g.drawLine(200, 200, 230, 230);  
            }
        }
       }
    }
    

