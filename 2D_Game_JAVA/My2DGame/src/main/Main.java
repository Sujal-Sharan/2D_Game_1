package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Closes on clicking "x"
		window.setResizable(false);	//Cannot re-size
		window.setTitle("Game's Title");	//Window title
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack();	//causes window to fit in preferred size of components
		
		window.setLocationRelativeTo(null);	//Display at center of screen
		window.setVisible(true);	//Can be viewed
		
		gamePanel.setupGame(); 
		gamePanel.StartGameThread();
	}

}
