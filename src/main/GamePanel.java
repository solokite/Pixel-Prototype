package main;

import java.awt.*;
import javax.swing.*;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
// Original Tile Settings
public final int originalTileSize = 16;
public final int scale = 4;
public final int tileSize = originalTileSize * scale;


// Screen Settings
public final int maxScreenCol = 30;
public final int maxScreenRow = 20;
public int screenWidth = tileSize * maxScreenCol;
public int screenHeight = tileSize * maxScreenRow;

// World Settings
public final int maxWorldCol = 50;
public final int maxWorldRow = 50;
public final int worldWidth = tileSize * maxWorldCol;
public final int worldHeight = tileSize * maxWorldRow;

// FPS
private final int FPS = 60;

// Managers
TileManager tileM = new TileManager(this);
    public TileManager getTileManager() { return tileM; }
KeyHandler keyH = new KeyHandler(this);
public UI ui = new UI(this);
public CollisionChecker cChecker = new CollisionChecker(this);
public AssetSetter aSetter = new AssetSetter(this);

// Camera offset (world coordinates of top-left of screen)
public int cameraX = 0;
public int cameraY = 0;

private void updateCamera() {
    // Camera follows player, staying centered unless at world edge
    int playerScreenX = screenWidth / 2 - tileSize / 2;
    int playerScreenY = screenHeight / 2 - tileSize / 2;
    
    cameraX = (int) player.worldX - playerScreenX;
    cameraY = (int) player.worldY - playerScreenY;
    
    // Clamp camera to world bounds
    if (cameraX < 0) cameraX = 0;
    if (cameraY < 0) cameraY = 0;
    if (cameraX + screenWidth > worldWidth) cameraX = worldWidth - screenWidth;
    if (cameraY + screenHeight > worldHeight) cameraY = worldHeight - screenHeight;
}

// Entities
public Player player = new Player(this, keyH);
public SuperObject[] obj = new SuperObject[10];

// Game Thread
private Thread gameThread;

// Game States
public int gameState;
public final int titleState = 0;
public final int playState = 1;
public final int pauseState = 2;
public final int levelClearedState = 3;

// Level Management
public int currentLevel = 1;
private final String[] levelMaps = {
    "/res/maps/level1map.txt",
    "/res/maps/map01.txt",
    "/res/maps/map02.txt",
    "/res/maps/map05.csv",
    "/res/maps/world01.txt"
}; // Add more levels as needed

public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.BLACK);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyH);
    this.setFocusable(true);
}

public void setupGame() {
    aSetter.setObject();
    gameState = titleState;
}

public void resetGame() {
    loadLevel(1); // reset to level 1
}

public void loadLevel(int levelNumber) {
    if (levelNumber < 1 || levelNumber > levelMaps.length) {
        System.err.println("Level " + levelNumber + " does not exist.");
        return;
    }
    
    currentLevel = levelNumber;
    
    // Clear objects
    for (int i = 0; i < obj.length; i++) {
        obj[i] = null;
    }
    
    // Load the map for this level
    tileM.loadMap(levelMaps[levelNumber - 1]);
    
    // Reset player state
    player.hasKey = 0;
    player.worldX = tileSize * 4;
    player.worldY = tileSize * 23;
    
    // Place objects specific to this level
    aSetter.setObjectsForLevel(levelNumber);
    
    // Reset UI state
    ui.messageOn = false;
    ui.messageCounter = 0;
    gameState = playState;
    ui.gameFinished = false;
}

public void loadNextLevel() {
    loadLevel(currentLevel + 1);
}

public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
}

@Override
public void run() {
    double drawInterval = 1_000_000_000.0 / FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long timer = 0;
    int drawCount = 0;

    while (gameThread != null) {
        long currentTime = System.nanoTime();
        delta += (currentTime - lastTime) / drawInterval;
        timer += (currentTime - lastTime);
        lastTime = currentTime;

        if (delta >= 1) {
            update();
            updateCamera();
            repaint();
            delta--;
            drawCount++;
        }

        if (timer >= 1_000_000_000) {
            System.out.println("FPS: " + drawCount);
            drawCount = 0;
            timer = 0;
        }
    }
}

public void update() {
    if (gameState == playState) {
        player.update();
        for (SuperObject o : obj) {
            if (o != null) o.update();
        }
    }
}

@Override
public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    
    if (gameState == titleState) {
        ui.draw(g2);
    } else {
        tileM.draw(g2);

        for (SuperObject o : obj) {
            if (o != null) o.draw(g2, this);
        }

        player.draw(g2);

        ui.draw(g2);
    }

    g2.dispose();
}

// Optional: Fullscreen scaling

}



