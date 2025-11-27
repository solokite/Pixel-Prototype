package tile;

import java.awt.image.BufferedImage;

public class Tile {
	
	public BufferedImage image;
	public boolean collision = false;
	public boolean collisionOn;
	
	// Directional collision flags
	public boolean collisionTop = false;
	public boolean collisionBottom = false;
	public boolean collisionLeft = false;
	public boolean collisionRight = false;
	
	// Constructor for simple collision (all sides)
	public Tile() {
	}
	
	// Constructor for directional collision
	public Tile(boolean top, boolean bottom, boolean left, boolean right) {
		this.collisionTop = top;
		this.collisionBottom = bottom;
		this.collisionLeft = left;
		this.collisionRight = right;
	}
}
