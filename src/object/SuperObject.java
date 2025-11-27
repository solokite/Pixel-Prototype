package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 64, 64);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    // Add this method
    public void update() {
        // For now, nothing happens
        // Later you can add animation or movement logic
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = (int)(worldX - gp.cameraX);
        int screenY = (int)(worldY - gp.cameraY);

        // Only draw if visible on screen
        if (screenX + gp.tileSize > 0 && screenX < gp.screenWidth &&
            screenY + gp.tileSize > 0 && screenY < gp.screenHeight) {

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
