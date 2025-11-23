 package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;	// Where player is drawn
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler keyH) {	//Constructor for 'Player' class
		
		this.gp = gp;	//initializing value for GamePanel
		this.keyH = keyH;	//initialize keyHandler
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);	// Player char is at center of screen
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		// Collision Barrier
		solidArea = new Rectangle(); //	(x, y, width, height) : (0, 0, gp.tileSize, gp.tileSize) [same size as tile]
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {	//sets default values for 'Player'
		
		worldX = gp.tileSize * 23;	 // Player position on world map
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {	//sets player character images
		
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/P_Walk_Up_Left.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/P_Walk_Up_Right.png"));
	
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/P_Walk_Left_Front.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/P_Walk_Right_Front.png"));
			
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/P_Idle_Left.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/P_Walk_Left.png"));
			
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/P_Idle_Right.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/P_Walk_Right.png"));
			
			idle = ImageIO.read(getClass().getResourceAsStream("/player/P_Idle_Base.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//called 60 times/second (FPS)
	public void update() {	//updates the player position
		
		//IDEA: maybe have the direction value as null or something as display the base image when not moving
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true)
		{
			if(keyH.upPressed == true) {
				direction = "up";
			}
			else if(keyH.downPressed == true) {
				direction = "down";
			}
			else if(keyH.leftPressed == true) {
				direction = "left";
			}
			else if(keyH.rightPressed == true) {
				direction = "right";
			}
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			
			// IF COLLISION FALSE, CAN MOVE
			if(collisionOn == false) {
				
				switch(direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
			
			spriteCounter++;	//counter to switch displayed image
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		else {
			direction = "idle";
		}
		

	}
	
	public void draw(Graphics2D g2) {
		
//		g2.setColor(Color.white);	//sets color of selected area
//		
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);	// (X co-ord, Y co-ord, Width, Height)

		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			break;
		case "idle":
			image = idle;
			break;
			
		}
		
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);	//draws an image on screen
		
		
	}
}
