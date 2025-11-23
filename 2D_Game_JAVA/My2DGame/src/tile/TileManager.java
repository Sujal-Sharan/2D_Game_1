package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;	//Array of type 'Tile'
	public int mapTileNum[][];	//will store map file data
	
	//Constructor
	public TileManager(GamePanel gp) {	
		
		this.gp = gp;
		
		tile = new Tile[10];	//can create '10' different types of tiles, can be changed based on requirements
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/world_map_1.txt");
		
	}
	
	public void getTileImage() {
	
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Tile_Grass_1.png"));	
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Tile_Water_1.png"));	
			tile[1].collision = true;	// Collision detector
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Tile_Brick_1.png"));	
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Tile_Sand_1.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Tile_Earth_1.png"));
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Tile_Tree_1.png"));
			tile[5].collision = true;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) {	//'filePath' stores the path to map
		
		try {
			
			InputStream is = getClass().getResourceAsStream(filePath);	//used to import the map text file
			BufferedReader br = new BufferedReader(new InputStreamReader(is));	//reading the file
			
			int col = 0;
			int row = 0;
			
			while((col < gp.maxWorldCol) && (row < gp.maxWorldRow)) {
				
				String line = br.readLine();	//reads a single line and stores in variable 'Line'
				
				while(col < gp.maxWorldCol) {
					
					String numbers[] = line.split(" ");	//split in string and store values in number array
					
					int num = Integer.parseInt(numbers[col]);	//changing from 'String' to 'int'
					
					mapTileNum[col][row] = num;	//stores the extracted number at that position
					col++;
					
				}
				
				if(col == gp.maxWorldCol) {
					
					col = 0;
					row++;
				}
				
			}
			br.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {	//to draw the tiles
		
		int worldCol = 0;
		int worldRow = 0;
		
		while((worldCol < gp.maxWorldCol) && (worldRow < gp.maxWorldRow)) {
			
			int tileNum = mapTileNum[worldCol][worldRow];	//Extract number from array
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			
			//gp.player.worldX : tileCo-ord from world map
			//gp.player.screenX : offset value so player is in center
			int screenX = worldX - gp.player.worldX + gp.player.screenX; 
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			// Only draw if tile is in boundary
			if((worldX + gp.tileSize > gp.player.worldX - gp.player.screenX) &&
				(worldX - gp.tileSize < gp.player.worldX + gp.player.screenX) &&
				(worldY + gp.tileSize > gp.player.worldY - gp.player.screenY) &&
				(worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);	
			}

			worldCol++;
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}

		}
		
		
	}
	
}
