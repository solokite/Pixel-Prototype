package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
  
  GamePanel gp;
  Font pixelFont, pixelFont80B;
  BufferedImage keyImage;
  public boolean messageOn = false;
  public String message = "";
  int messageCounter = 0;
  public boolean gameFinished = false;
 

  double playTime;
  DecimalFormat dFormat = new DecimalFormat("#0.00");

  

  public UI(GamePanel gp) {
    this.gp = gp;

    OBJ_Key key = new OBJ_Key();
    keyImage = key.image;
    try {
      InputStream is = getClass().getResourceAsStream("/assets/fonts/Font_Pixel.ttf");
      Font font = Font.createFont(Font.TRUETYPE_FONT, is);
      pixelFont = font.deriveFont(Font.PLAIN, 40);
      pixelFont80B = font.deriveFont(Font.BOLD, 80);
    } catch (Exception e) {
      e.printStackTrace();
      pixelFont = new Font("Arial", Font.PLAIN, 40);
    }
  }

  public void showMessage(String text) {
    message = text;
    messageOn = true;
  }

  public void draw(Graphics2D g2) {

    playTime += (double) 1.0 / 60;

    int minutes = (int) playTime / 60;
    int seconds = (int) playTime % 60;

    String timerText = String.format("%d:%02d", minutes, seconds);

    if (gameFinished == true) {

      g2.setFont(pixelFont80B);
      g2.setColor(Color.ORANGE);
      
      String text;
      String timeText;
      int textLength;
      int x;
      int y;
      int timeX;
      int timeY;

      text = "Level Cleared!";
      textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

      x = gp.screenWidth / 2 - textLength / 2;
      y = gp.screenHeight / 2 - (gp.finalSize * 4);

      timeX = gp.screenWidth / 2 - textLength / 2;
      timeY = gp.screenHeight / 2 - (gp.finalSize * 2);

      timeText = "Time: " + timerText;
      g2.drawString(timeText, timeX, timeY);
      g2.drawString(text, x, y);
      
      

      gp.gameThread = null;

    } else {
      g2.setFont(pixelFont);
      g2.setColor(Color.BLACK);
      g2.drawImage(keyImage, gp.finalSize / 2, gp.finalSize / 2, gp.finalSize, gp.finalSize, null);
      g2.drawString(" = " + gp.player.hasKey, 74, 80);


      // TIMER
      

      g2.drawString("Time: " + timerText, gp.finalSize * 26, 90);

      // Message Notification
      if (messageOn == true) {
        g2.drawString((message), gp.finalSize / 2, gp.tileSize * 10);

        messageCounter++;

      if (messageCounter > 120) {
        messageCounter = 0;
        messageOn = false;
      }
    }

    
    }

  }
}

