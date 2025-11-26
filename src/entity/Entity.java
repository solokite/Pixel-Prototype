package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	
	public int worldX, worldY;
	public double speed, walkSpeed, runSpeed;
	public int runSpeedOriginal = 5;
	
	public boolean jumping = false;
	public double jumpSpeed = -20;
	public double yVelocity = 0;
	public double gravity = 0.9;
	public double groundY;
	

	public BufferedImage left1, left2, left3, left4, right1, right2, right3, right4, idle1, idle2, idle3, idle4, jump1, jump2, jump3, jump4, jump5, jump6;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;

	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY; 
	public boolean collisionOn = false;
  
}
