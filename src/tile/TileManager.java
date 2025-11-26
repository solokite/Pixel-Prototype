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
	public Tile[] tile;
	public int mapTileNum[][];

	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		this.gp = gp;
		tile = new Tile[10];
		mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/res/maps/world01.txt");
	}
		// READ TILE DATA
		
		public void getTileImage() {
			try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/assets/tiles/white_background.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/assets/tiles/pixelGrass32x32.png"));
			tile[1].collision = true;

			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/assets/tiles/pixelDirt32x32.png"));
			tile[2].collision = true;
			
			//
			
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
			
			int worldX = worldCol * gp.finalSize;
			int worldY = worldRow * gp.finalSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int  screenY = worldY - gp.player.worldY + gp.player.screenY;


			// stopping from going out camera
			if(gp.player.screenX > gp.player.worldX) {
				screenX = worldX;
			}
			if (gp.player.screenY > gp.player.worldY) {
				screenY = worldY;
			}
			int rightOffset = gp.screenWidth - gp.player.screenX;
			if (rightOffset > gp.worldWidth - gp.player.worldX) {}
			
			if (worldX + gp.finalSize > gp.player.worldX - gp.player.screenX && worldX - gp.finalSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.finalSize > gp.player.worldY - gp.player.screenY && worldY - gp.finalSize < gp.player.worldY + gp.player.screenY) {
				
				g2.drawImage(tile[tileNum].image, (int)screenX, (int)screenY, gp.finalSize, gp.finalSize, null);
			}
			worldCol++; 
			
			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
	}
}
