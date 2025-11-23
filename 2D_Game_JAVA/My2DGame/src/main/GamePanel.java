package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS
	final int originalTileSize = 16;	//16x16 pixels
	final int scale = 3;	//Scales up the characters
	
	public final int tileSize = originalTileSize * scale; //48x48 (Displayed size)
	public final int maxScreenCol = 16;	//Horizontal Size
	public final int maxScreenRow = 12;	//Vertical Size
	public final int screenWidth = tileSize * maxScreenCol;	//768 px
	public final int screenHeight = tileSize * maxScreenRow;	//576 px
	
	// WORLD SETTINGS
	public final int maxWorldCol = 51;
	public final int maxWorldRow = 51;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	//FPS: frame rate per second
	int FPS = 60; //FPS will determine how many times the program will run per second
	
	TileManager tileM = new TileManager(this);	//instance of 'TileManager' class
	KeyHandler keyH = new KeyHandler();	//instance of 'KeyHanlder' class
	Thread gameThread;	//Threading
	
	public Player player = new Player(this, keyH);	//instance of 'Player' class
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssestSetter aSetter = new AssestSetter(this);
	public SuperObject obj[] = new SuperObject[10];		// 10 different objects
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));	//sets size of game panel
		this.setBackground(Color.black);	//sets game panel background
		this.setDoubleBuffered(true); 	//drawing from this component will be done in an off-screen buffer
		this.addKeyListener(keyH);	//GamePanel can recognize key input
		this.setFocusable(true);	//GamePanel can be 'focused' to receive key input
		
	}
	
	public void setupGame() {
		
		aSetter.setObject();
	}

	public void StartGameThread() {
		
		gameThread = new Thread(this);	//instantiate a thread with "GamePanel" class as parameter
		gameThread.start(); 	//calls "run" method
	}
	
	//	Game_Loop_Method_1	[Sleep Method ] sleep for remaining time
	//	Game_Loop_Method_2	[Delta / Accumulator Method]
	@Override
	public void run() {	//It will hold the 'Game Loop'
		
		double drawInterval = 1000000000/FPS;	//refresh every 0.016 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
//			//[1] UPDTE: character info like position
//			update();	//calls 'update' method
//			
//			//[2] DRAW: draw new updated info 
//			repaint();	//to call 'paintComponent' method
//			
			
		}
	}
	
	public void update() {
		
		player.update();	//calls 'update' method from player class
		
	}

	//Graphics: Class with many functions to draw objects on screen
	public void paintComponent(Graphics g) {	//standard method to draw
		
		super.paintComponent(g);
		
		//Graphics2D: extends 'Graphics' class and provide finer control over drawing like geometry, colors, layout
		Graphics2D g2 = (Graphics2D)g;	//converted 'Graphics' to type 'Graphics2D'
		
		// TILE
		//called before drawing player, otherwise it will hide player
		tileM.draw(g2);		//calls 'draw' method from tile class to draw background
		
		// OBJECT
		// scanning obj array
		for(int i = 0; i < obj.length; i++) {
			 if(obj[i] != null) {
				 obj[i].draw(g2, this);
			 }
		}
		
		// PLAYER
		player.draw(g2);	//calls 'draw' method from player class
		
		g2.dispose();	//free memory after use
		
	}

}
