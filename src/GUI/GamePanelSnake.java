package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

import Snake.Comida;
import Snake.Serpiente;

public class GamePanelSnake extends JPanel implements ActionListener, KeyListener{
	
	private Serpiente serpiente;
	private Comida comida;
	private Timer timer;
	private int score;
	
	public GamePanelSnake() {
		setPreferredSize(new Dimension(400, 300));
		setBackground(Color.BLACK);
		setFocusable(false);
		addKeyListener(this);
		serpiente = new Serpiente();
		comida = new Comida();
		timer = new Timer(100, this);
		timer.start();
		score = 0;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
