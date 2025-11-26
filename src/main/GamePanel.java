package main;
import java.awt.*;
import javax.swing.*;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	public final int tileSize = 16; // original tile size
	public final int scale = 4;
	
	public int finalSize = tileSize * scale;
	public int maxScreenCol = 30;
	public int maxScreenRow = 20;
	public int screenWidth = finalSize * maxScreenCol;
	public int screenHeight = finalSize * maxScreenRow;
	


	// World Settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = finalSize * maxWorldCol;
	public final int worldHeight = finalSize * maxWorldRow;
	

	
	// FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(this);
	public UI ui = new UI(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	


	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10];
	public AssetSetter aSetter = new AssetSetter(this);
	Thread gameThread;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}

	public void setupGame() {
		aSetter.setObject();
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		
		while (gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if (timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	public void update() {
		player.update();
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		// tile
		tileM.draw(g2);

		// object
		for (int i = 0; i < obj.length; i ++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}

		// player
		player.draw(g2);

		// UI
		ui.draw(g2);

		g2.dispose();
	}


}
