package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	ArrayList<String> fileNames = new ArrayList<>();
	ArrayList<String> collisionStatus = new ArrayList<>();

	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		


		tile = new Tile[20];
		mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/res/maps/level1map.txt");


	}
	
	public void getTileImage() {
		setup(0, "0", false);
		setup(1, "1", true);
		setup(2, "2", false);
		setup(3, "3", false);
		setup(4, "4", false);
		setup(5, "5", false);
		setup(6, "6", false);
		setup(7, "7", false);
		setup(8, "8", true);
		setup(9, "9", true);
		setup(10, "10", true);
		setup(11, "11", true);
		setup(12, "12", true);
		setup(13, "13", true);
		setup(14, "14", false);
		setup(15, "15", false);
	}
	
	public void setup(int index, String imageName, boolean collision) {
		UtilityTool uTool = new UtilityTool();

		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/assets/tilesMap/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
			// By default, if collision is true, all sides have collision
			if (collision) {
				tile[index].collisionTop = true;
				tile[index].collisionBottom = true;
				tile[index].collisionLeft = true;
				tile[index].collisionRight = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// New method for directional collision setup
	// Example: setDirectionalCollision(8, "8", true, true, false, false) = only top and bottom collision
	public void setDirectionalCollision(int index, String imageName, boolean top, boolean bottom, boolean left, boolean right) {
		UtilityTool uTool = new UtilityTool();

		try {
			tile[index] = new Tile(top, bottom, left, right);
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/assets/tilesMap/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = (top || bottom || left || right); // true if any side has collision
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filepath) {
		try {
			InputStream is = getClass().getResourceAsStream(filepath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); 
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine();
				
				while (col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = (int)(worldX - gp.cameraX);
			int screenY = (int)(worldY - gp.cameraY);
			
			// Only draw tiles that are visible on screen
			if (screenX + gp.tileSize > 0 && screenX < gp.screenWidth &&
				screenY + gp.tileSize > 0 && screenY < gp.screenHeight) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
			
			worldCol++; 
			
			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
	}
}