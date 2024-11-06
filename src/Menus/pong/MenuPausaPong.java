package Menus.pong;

import java.awt.*;
import javax.swing.*;

public class MenuPausaPong extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public MenuPausaPong() {
		
		setTitle("Pausado");
		setSize(360, 540);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBackground(Color.WHITE);
		
		JButton Reanudar = new JButton();
		
		JButton Restart = new JButton();
		
		JButton MainMenu = new JButton();
	}
}
