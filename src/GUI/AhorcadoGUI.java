package GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import Menus.Ahorcado.menuAhorcado;
import Menus.snake.menuSnake;
import usuario.UsuarioSnake;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class AhorcadoGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private List<String> palabras;
    private String palabra;
    private char[] palabraAdivinada;
    private int intentosRestantes = 6;
    private int score = 0;
    private JLabel labelPalabra;
    private JLabel labelIntentos;
    private JLabel palabrasMal;
    private JLabel labelScore;
    private JTextField inputLetra;
    private DibujoAhorcado panelAhorcado;
    public static UsuarioSnake usuario;
    public AhorcadoGUI(UsuarioSnake usuario) {
    	menuSnake.usuario = usuario;
        this.setVisible(true);
        palabras = cargarPalabrasDesdeCSV();
        if (palabras.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron palabras en el archivo CSV.");
            System.exit(1);
        }
        Random random = new Random();
        palabra = palabras.get(random.nextInt(palabras.size()));

        setTitle("Juego del Ahorcado");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);

        palabraAdivinada = new char[palabra.length()];
        for (int i = 0; i < palabra.length(); i++) {
            palabraAdivinada[i] = (i % 2 == 0) ? '_' : ' ';
        }

        JPanel panelDerecha = new JPanel();
        panelDerecha.setBackground(Color.BLACK);
        palabrasMal = new JLabel();
        palabrasMal.setForeground(Color.WHITE);
        panelDerecha.add(palabrasMal);
        add(panelDerecha, BorderLayout.EAST);

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
        volverbutton.addActionListener(e -> {
            int salida = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres acabar la partida?", "ATENCIÓN", JOptionPane.YES_NO_OPTION);
            if (salida == JOptionPane.YES_OPTION) {
                
                    saveScoreToFile(usuario, score);
                
                new menuAhorcado(usuario);
                dispose();
            }
        });

        JPanel panelIzquierda = new JPanel();
        panelIzquierda.setBackground(Color.BLACK);
        panelIzquierda.add(volverbutton, BorderLayout.NORTH);
        add(panelIzquierda, BorderLayout.WEST);

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

        ActionListener verificarAction = e -> {
            verificarLetra(inputLetra.getText().toUpperCase());
            inputLetra.setText("");
        };
        botonVerificar.addActionListener(verificarAction);

        inputLetra.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    verificarAction.actionPerformed(null);
                }
            }
            public void keyReleased(KeyEvent e) {}
        });
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

    private List<String> cargarPalabrasDesdeCSV() {
        List<String> palabras = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/palabras.csv")))) {
            String linea = br.readLine();
            if (linea != null) {
                for (String palabra : linea.split(",")) {
                    palabras.add(palabra.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return palabras;
    }

    private void saveScoreToFile(UsuarioSnake usuario2, int score) {
        try (FileWriter writer = new FileWriter("leaderboard.txt", true)) {
            writer.write(usuario2 + "," + score + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reiniciarJuego() {
        Random random = new Random();
        palabra = palabras.get(random.nextInt(palabras.size()));
        palabraAdivinada = new char[palabra.length()];
        intentosRestantes = 6;
        for (int i = 0; i < palabra.length(); i++) {
            palabraAdivinada[i] = (i % 2 == 0) ? '_' : ' ';
        }
        labelPalabra.setText(String.valueOf(palabraAdivinada));
        palabrasMal.setText(null);
        labelIntentos.setText("Intentos restantes: " + intentosRestantes);
        panelAhorcado.repaint();
    }

    private class DibujoAhorcado extends JPanel {
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

            if (intentosRestantes <= 5) g.drawOval(175, 100, 50, 50);
            if (intentosRestantes <= 4) g.drawLine(200, 150, 200, 200);
            if (intentosRestantes <= 3) g.drawLine(200, 160, 170, 180);
            if (intentosRestantes <= 2) g.drawLine(200, 160, 230, 180);
            if (intentosRestantes <= 1) g.drawLine(200, 200, 170, 230);
            if (intentosRestantes <= 0) g.drawLine(200, 200, 230, 230);
        }
    }
}
