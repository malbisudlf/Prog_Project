package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;



public class panelSnake extends JPanel implements ActionListener, KeyListener{

	private static final long serialVersionUID = 1L;

	private class Tile {
		int x;
		int y;
		
		Tile(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	int anchoPanel;
	int altoPanel;
	int tileSize = 25;
	
	//SNAKE
	Tile snakeHead;
	ArrayList<Tile> snakeBody;
	
	//FOOD
	Tile food;
	Random random;
	
	//LOGICA
	Timer bucleJuego;
	int velocidadX;
	int velocidadY;
	
	boolean gameOver = false;
	
	panelSnake(int anchoPanel, int altoPanel) {
		this.anchoPanel = anchoPanel;
		this.altoPanel = altoPanel;
		setPreferredSize(new Dimension(this.anchoPanel, this.altoPanel));
		setBackground(Color.GREEN);
		addKeyListener(this);
		setFocusable(true);
		
		snakeHead = new Tile(5, 5);
		snakeBody = new ArrayList<Tile>();
		
		food = new Tile(23, 5);
		random = new Random();
		placeFood();
		
		velocidadX = 0;
		velocidadY = 0;
		
		bucleJuego = new Timer(100, this);
		bucleJuego.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		//GRID
		for (int i = 0; i < anchoPanel/tileSize; i++) {
			g.drawLine(i*tileSize, 0, i*tileSize, altoPanel);
			g.drawLine(0, i*tileSize, anchoPanel, i*tileSize);
		}
		
		//FOOD
		g.setColor(Color.RED);
		g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize, true);
		
		//SNAKE HEAD
		g.setColor(Color.BLUE);
		g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);
		
		//SNAKE BODY
		for (int i = 0; i < snakeBody.size(); i++) {
			Tile snakePart = snakeBody.get(i);
			g.fill3DRect(snakePart.x * tileSize, snakePart.y*tileSize, tileSize, tileSize, true);
		}
		
		//SCORE
		g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
	}
	
	public void placeFood() {
		food.x = random.nextInt(anchoPanel/tileSize); //600/25 = 24 //TENEMOS 24 X 24 PIXELES 
		food.y = random.nextInt(altoPanel/tileSize);
	}
	
	public boolean collision(Tile tile1, Tile tile2) {
		return tile1.x == tile2.x && tile1.y == tile2.y;
	}
	
	public void move() {
		//Eat food
		if (collision(snakeHead, food)) {
			snakeBody.add(new Tile(food.x, food.y));
			placeFood();
		}
		
		//CUERPO SNAKE
		for (int i = snakeBody.size()-1; i >= 0; i--) {
			Tile snakePart =snakeBody.get(i);
			if (i==0) {
				snakePart.x = snakeHead.x;
				snakePart.y = snakeHead.y;
			} else {
				Tile prevSnakePart = snakeBody.get(i-1);
				snakePart.x = prevSnakePart.x;
				snakePart.y = prevSnakePart.y;
			}
		}
		
		//CABEZA SNAKE
		snakeHead.x += velocidadX;
		snakeHead.y += velocidadY;
		
		for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);

            //collide with snake head
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
		}
		
		if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > anchoPanel - 25 || //passed left border or right border
	            snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > altoPanel - 25 ) { //passed top border or bottom border
	            gameOver = true;
	        }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		move();
		repaint();
		if (gameOver) {
			bucleJuego.stop();
        }
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_UP && velocidadY != 1) {
			velocidadX = 0;
			velocidadY = -1;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocidadY != -1) {
			velocidadX = 0;
			velocidadY = 1;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocidadX != -1) {
			velocidadX = 1;
			velocidadY = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocidadX != 1) {
			velocidadX = -1;
			velocidadY = 0;
		} 
	}

	
	
	
	
	
	
	//NO SON NECESARIOS
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
