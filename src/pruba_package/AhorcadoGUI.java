
package pruba_package;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AhorcadoGUI extends JFrame {
    private String palabra = "JAVA";  // La palabra a adivinar
    private char[] palabraAdivinada;  // Guarda el progreso del jugador
    private int intentosRestantes = 6;  // Máximo de intentos
    private JLabel labelPalabra;  // Muestra la palabra oculta
    private JLabel labelIntentos;  // Muestra los intentos restantes
    private JTextField inputLetra;  // Campo para la letra ingresada
    private JLabel labelAhorcado;  // Lugar para el dibujo del ahorcado
    
    public AhorcadoGUI() {
        // Inicializa el JFrame
        setTitle("Juego del Ahorcado");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicializa el array de la palabra a adivinar
        palabraAdivinada = new char[palabra.length()];
        for (int i = 0; i < palabra.length(); i++) {
            palabraAdivinada[i] = '_';
        }

        // Panel superior para mostrar la palabra
        JPanel panelSuperior = new JPanel();
        labelPalabra = new JLabel(String.valueOf(palabraAdivinada));
        panelSuperior.add(labelPalabra);
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central para el dibujo del ahorcado
        labelAhorcado = new JLabel("Intentos restantes: " + intentosRestantes);
        add(labelAhorcado, BorderLayout.CENTER);

        // Panel inferior para los controles
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout());

        inputLetra = new JTextField(5);
        panelInferior.add(inputLetra);

        JButton botonVerificar = new JButton("Verificar");
        panelInferior.add(botonVerificar);

        labelIntentos = new JLabel("Intentos restantes: " + intentosRestantes);
        panelInferior.add(labelIntentos);

        add(panelInferior, BorderLayout.SOUTH);

        // Acción del botón "Verificar"
        botonVerificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarLetra(inputLetra.getText().toUpperCase());
                inputLetra.setText("");  // Limpia el campo de texto
            }
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
            }
        }

        if (!acierto) {
            intentosRestantes--;
        }

        labelPalabra.setText(String.valueOf(palabraAdivinada));
        labelIntentos.setText("Intentos restantes: " + intentosRestantes);
        
        // Actualiza el dibujo del ahorcado
        actualizarAhorcado();

        // Comprueba si ha ganado o perdido
        if (String.valueOf(palabraAdivinada).equals(palabra)) {
            JOptionPane.showMessageDialog(this, "¡Has ganado!");
            reiniciarJuego();
        } else if (intentosRestantes == 0) {
            JOptionPane.showMessageDialog(this, "¡Has perdido! La palabra era: " + palabra);
            reiniciarJuego();
        }
    }

    private void actualizarAhorcado() {
        String dibujo = "";
        switch (intentosRestantes) {
            case 5: dibujo = "Cabeza"; break;
            case 4: dibujo = "Cuerpo"; break;
            case 3: dibujo = "Brazo izquierdo"; break;
            case 2: dibujo = "Brazo derecho"; break;
            case 1: dibujo = "Pierna izquierda"; break;
            case 0: dibujo = "Pierna derecha"; break;
        }
        labelAhorcado.setText(dibujo);
    }

    private void reiniciarJuego() {
        // Reinicia el juego
        intentosRestantes = 6;
        for (int i = 0; i < palabra.length(); i++) {
            palabraAdivinada[i] = '_';
        }
        labelPalabra.setText(String.valueOf(palabraAdivinada));
        labelIntentos.setText("Intentos restantes: " + intentosRestantes);
        labelAhorcado.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AhorcadoGUI gui = new AhorcadoGUI();
            gui.setVisible(true);
        });
    }
}
