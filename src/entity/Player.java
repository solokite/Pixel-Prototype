package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
GamePanel gp;
KeyHandler keyH;


public final int screenX;
public final int screenY;

public int hasKey = 0;
int spriteCounter = 0;

public Player(GamePanel gp, KeyHandler keyH) {
    this.gp = gp;
    this.keyH = keyH;

    screenX = gp.screenWidth / 2 - gp.tileSize / 2;
    screenY = gp.screenHeight / 2 - gp.tileSize / 2;

    setDefaultValues();
    getPlayerImage();

    // Collision box - scaled up player
        solidArea = new Rectangle(28, 2, 28, 64);
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
}


public void setDefaultValues() {
    worldX = gp.tileSize * 4;
    worldY = gp.tileSize * 23;
    walkSpeed = gp.worldWidth / 600;
    runSpeed = gp.worldWidth / 600;
    direction = "idle";

    groundY = worldY;
}

public void getPlayerImage() {
    try {
        // Jump sprites
        jump1 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat_jumping1.png"));
        jump2 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat_jumping2.png"));
        jump3 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat_jumping3.png"));
        jump4 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat_jumping4.png"));
        jump5 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat_jumping5.png"));
        jump6 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat_jumping6.png"));

        // LEFT
        left1 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat1idle1.png"));
        left2 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat1idle2.png"));
        left3 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat1idle3.png"));
        left4 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat1idle4.png"));

        // RIGHT
        right1 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat1idle1.png"));
        right2 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat1idle2.png"));
        right3 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat1idle3.png"));
        right4 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat1idle4.png"));

        // IDLE
        idle1 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat1idle1.png"));
        idle2 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat1idle2.png"));
        idle3 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat1idle3.png"));
        idle4 = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/cat1idle4.png"));
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public void update() {
    boolean moving = false;

    // Horizontal input
    if (keyH.leftPressed) {
        direction = "left";
        moving = true;
    } else if (keyH.rightPressed) {
        direction = "right";
        moving = true;
    } else {
        direction = "idle";
    }

    // Speed
    speed = keyH.shiftPressed ? runSpeed : walkSpeed;

    // Jump input
    if (keyH.spacePressed && !jumping) {
        jumping = true;
        yVelocity = jumpSpeed;
    }

    // Apply gravity
    yVelocity += gravity;
    if (yVelocity > 20) yVelocity = 20;

    // Move vertically
    worldY += yVelocity;

    // Check vertical collisions
    gp.cChecker.checkTile(this);

    // Check objects
    int objIndex = gp.cChecker.checkObject(this, true);
    pickUpObject(objIndex);

    // Move horizontally
    if (direction.equals("left")) {
        gp.cChecker.checkTileHorizontal(this, "left");
        if (!collisionOn) {
            worldX -= speed;
        }
    } else if (direction.equals("right")) {
        gp.cChecker.checkTileHorizontal(this, "right");
        if (!collisionOn) {
            worldX += speed;
        }
    }

    // Sprite animation
    spriteCounter++;
    int frameSpeed = keyH.shiftPressed ? 8 : 12;

    if (jumping) {
        if (spriteCounter > 12) {
            spriteNum++;
            if (spriteNum > 6) spriteNum = 1;
            spriteCounter = 0;
        }
    } else if (moving) {
        if (spriteCounter > frameSpeed) {
            spriteNum++;
            if (spriteNum > 4) spriteNum = 1;
            spriteCounter = 0;
        }
    } else {
        if (spriteCounter > 10) {
            spriteNum++;
            if (spriteNum > 4) spriteNum = 1;
            spriteCounter = 0;
        }
    }
}

public void pickUpObject(int i) {
    if (i != 999 && gp.obj[i] != null) {
        String objectName = gp.obj[i].name;
        switch (objectName) {
            case "Key":
                hasKey++;
                gp.obj[i] = null;
                gp.ui.showMessage("Player 1 got the Key");
                break;
            case "Door":
                if (hasKey > 0) {
                    gp.obj[i] = null;
                    hasKey--;
                    gp.ui.showMessage("Level 1: Completed!");
                    gp.ui.gameFinished = true;
                } else {
                    gp.ui.showMessage("You need a Key.");
                }
                break;
        }
    }
}

public void draw(Graphics2D g2) {
    BufferedImage image = null;

    if (jumping) {
        switch (spriteNum) {
            case 1: image = jump1; break;
            case 2: image = jump2; break;
            case 3: image = jump3; break;
            case 4: image = jump4; break;
            case 5: image = jump5; break;
            case 6: image = jump6; break;
        }
    } else {
        boolean moving = keyH.leftPressed || keyH.rightPressed;
        if (moving) {
            switch (direction) {
                case "left":
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                    if (spriteNum == 3) image = left3;
                    if (spriteNum == 4) image = left4;
                    break;
                case "right":
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                    if (spriteNum == 3) image = right3;
                    if (spriteNum == 4) image = right4;
                    break;
            }
        } else { // idle
            if (spriteNum == 1) image = idle1;
            if (spriteNum == 2) image = idle2;
            if (spriteNum == 3) image = idle3;
            if (spriteNum == 4) image = idle4;
        }
    }

    // Draw player using camera offset from GamePanel - scaled up size
    int screenDrawX = (int)(worldX - gp.cameraX);
    int screenDrawY = (int)(worldY - gp.cameraY);
    int playerDrawSize = (int)(gp.tileSize * 1.2); // 20% bigger

    g2.drawImage(image, screenDrawX, screenDrawY, playerDrawSize, playerDrawSize, null);

    // Optional: debug collision
    // g2.setColor(Color.RED);
    // g2.drawRect(screenDrawX + solidArea.x, screenDrawY + solidArea.y, solidArea.width, solidArea.height);
}


}
