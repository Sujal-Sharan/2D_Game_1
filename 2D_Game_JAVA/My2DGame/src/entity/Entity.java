package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

//stores variables used in player, monster, NPC classes
public class Entity {

	public int worldX, worldY;
	public int speed;
	
	//Image with an accessible buffer for image data, used to store image file
	public BufferedImage idle, up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;	
	
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	
}
