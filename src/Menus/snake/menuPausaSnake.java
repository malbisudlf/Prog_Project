package Menus.snake;

import javax.swing.*;

import GUI.SnakeGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuPausaSnake extends JFrame{

    //BOTON REANUDAR (JButton)
    //BOTON REINICIAR (JButton)
    
    
	private static final long serialVersionUID = 1L;
	
	 public static SnakeGUI snakeGUI;

	public menuPausaSnake(SnakeGUI snakeGUI){

		menuPausaSnake.snakeGUI = snakeGUI;
		
        setTitle("SNAKE MENU PAUSA");
		setSize(300, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

        setLayout(new GridLayout(2, 1, 2, 2));

        //BOTON REANUDAR (JButton)
        JButton botonReanudar = new JButton("REANUDAR");
        //BOTON REINICIAR (JButton)
        JButton botonReiniciar = new JButton("REINICIAR");
       

        botonReanudar.setFocusable(false);
		botonReiniciar.setFocusable(false);
		

        botonReanudar.setBackground(Color.BLACK);
        botonReiniciar.setBackground(Color.BLACK);

        botonReanudar.setForeground(Color.WHITE);
        botonReiniciar.setForeground(Color.WHITE);

        Font font = new Font("ARIAL", Font.BOLD, 25);

        botonReanudar.setFont(font);
        botonReiniciar.setFont(font);

        add(botonReanudar);
        add(botonReiniciar);


        botonReanudar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
            	snakeGUI.enableButtons();
                dispose();
            }
            
        });

        botonReiniciar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                reiniciarJuego();
            	//Cuando el juego este creado modificar el metodo reiniciarJuego() que sirva para reiniciar la serpiente, contadores, etc.
            }
            
        });

        setVisible(true);
    }
    
    private void reiniciarJuego() {
    	JOptionPane.showMessageDialog(this, "Metodo reiniciarJuego() aun sin implementar");
    }
    
}
