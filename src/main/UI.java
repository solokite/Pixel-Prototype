package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import object.OBJ_Key;


public class UI {
  
  GamePanel gp;
  Graphics2D g2;
  Font pixelFont, pixelFont80B;
  Font fontTitle, menuFont;
  BufferedImage keyImage, emptyKey;
  public boolean messageOn = false;
  public String message = "";
  int messageCounter = 0;
  public boolean gameFinished = false;
  public int commandNum = 0;
  BufferedImage grayCat, orangeCat;

 

  

  double playTime;
  DecimalFormat dFormat = new DecimalFormat("#0.00");

  

  public void titleFont() {
    try {
        InputStream input = getClass().getResourceAsStream("/assets/fonts/PixelFont.otf");

        if (input == null) {
            System.out.println("Font file not found!");
            return;
        }

        Font fontFile = Font.createFont(Font.TRUETYPE_FONT, input);
        fontTitle = fontFile.deriveFont(Font.PLAIN, 120f);
        menuFont = fontFile.deriveFont(Font.PLAIN, 50f);

    } catch (FontFormatException | IOException e) {
        e.printStackTrace();
    }
}


  public UI(GamePanel gp) {
    this.gp = gp;
    titleFont();
    OBJ_Key key = new OBJ_Key();
    keyImage = key.image;
    emptyKey = key.emptyKeyImage;


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

  public void getIconImage() {
    try {
      grayCat = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/grayCat.png"));
      orangeCat = ImageIO.read(getClass().getResourceAsStream("/assets/sprites/player/orangeCat.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void showMessage(String text) {
    message = text;
    messageOn = true;
  }

  public void drawLevelClearedMenu(Graphics2D g2) {
    this.g2 = g2;
    String[] options = { "NEXT LEVEL", "MAIN MENU","EXIT" };
    int padding = 30; // 
    int spacing = 50; // 

    g2.setFont(pixelFont80B);
    int titleWidth = g2.getFontMetrics().stringWidth("LEVEL CLEARED!");
    int titleHeight = g2.getFontMetrics().getHeight();

    g2.setFont(pixelFont);
    int maxOptionWidth = 0;
    for (String option : options) {
        int w = g2.getFontMetrics().stringWidth(option);
        if (w > maxOptionWidth) maxOptionWidth = w;
    }
    int optionHeight = g2.getFontMetrics().getHeight();

    int rectWidth = Math.max(titleWidth, maxOptionWidth) + padding * 2;
    int rectHeight = titleHeight + options.length * (optionHeight + 20) + padding * 2 + spacing;

    int rectX = gp.screenWidth / 2 - rectWidth / 2;
    int rectY = gp.screenHeight / 2 - rectHeight / 2;

    // -----------------------
    // Draw the rounded rectangle background
    // -----------------------
    g2.setColor(new Color(0, 0, 0, 200)); // semi-transparent black
    g2.fillRoundRect(rectX, rectY, rectWidth, rectHeight, 30, 30);

    // -----------------------
    // Draw LEVEL CLEARED title
    // -----------------------
    g2.setFont(pixelFont80B);
    g2.setColor(Color.ORANGE);
    String title = "LEVEL CLEARED!";
    int titleX = gp.screenWidth / 2 - g2.getFontMetrics().stringWidth(title) / 2;
    int titleY = rectY + padding + titleHeight;
    g2.drawString(title, titleX, titleY);

    // -----------------------
    // Draw menu options
    g2.setFont(pixelFont);
    int optionY = titleY + spacing;
    for (int i = 0; i < options.length; i++) {
        String option = options[i];
        int optionX = gp.screenWidth / 2 - g2.getFontMetrics().stringWidth(option) / 2;

        // Draw selection background if selected
        if (commandNum == i) {
            int bgWidth = g2.getFontMetrics().stringWidth(option) + 20;
            int bgHeight = optionHeight;
            g2.setColor(new Color(255, 165, 0, 180)); // semi-transparent orange
            g2.fillRoundRect(optionX - 10, optionY - optionHeight + 5, bgWidth, bgHeight, 20, 20);
            g2.setColor(Color.BLACK);
        } else {
            g2.setColor(Color.WHITE);
        }

        g2.drawString(option, optionX, optionY);
        optionY += optionHeight + 20; // spacing between options
    }
  }
  
  
  public void draw(Graphics2D g2) {
    this.g2 = g2;
    g2.setFont(pixelFont80B);
    g2.setColor(Color.WHITE);

    // Title State
    if (gp.gameState == gp.titleState) {
      drawTitleScreen();
    }

    // Play State
    
    if (gp.gameState == gp.playState) {
      if (gameFinished) {
        // Show the level cleared menu
        gp.gameState = gp.levelClearedState;  
      } else {
          // Normal gameplay UI
          g2.setFont(pixelFont);
          g2.setColor(Color.BLACK);
          g2.drawImage(emptyKey, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize * 2, gp.tileSize * 2, null);
          g2.drawImage(emptyKey, gp.tileSize / 2 + 100, gp.tileSize / 2, gp.tileSize * 2, gp.tileSize * 2, null);
          g2.drawImage(emptyKey, gp.tileSize / 2 + 200, gp.tileSize / 2, gp.tileSize * 2, gp.tileSize * 2, null);
          //g2.drawString(" = " + gp.player.hasKey, 74, 80);
          
          if (gp.player.hasKey == 1) {
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize * 2, gp.tileSize * 2, null);
          } else if (gp.player.hasKey == 2) {
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize * 2, gp.tileSize * 2, null);
            g2.drawImage(keyImage, gp.tileSize / 2 + 100, gp.tileSize / 2, gp.tileSize * 2, gp.tileSize * 2, null);
          } else if (gp.player.hasKey == 3) {
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize * 2, gp.tileSize * 2, null);
            g2.drawImage(keyImage, gp.tileSize / 2 + 100, gp.tileSize / 2, gp.tileSize * 2, gp.tileSize * 2, null);
            g2.drawImage(keyImage, gp.tileSize / 2 + 200, gp.tileSize / 2, gp.tileSize * 2, gp.tileSize * 2, null);
          }

          // Message Notification
          
          if (messageOn) {
            g2.setFont(pixelFont);
            
            // Measure text width and height
            int textWidth = g2.getFontMetrics().stringWidth(message);
            int textHeight = g2.getFontMetrics().getHeight();
            
            int padding = 20; // space around the text
            
            // Center the rectangle above player
            int rectX = gp.screenWidth / 2 - (textWidth + padding * 2) / 2;
            int rectY = gp.tileSize; // above player; adjust as needed
            int rectWidth = textWidth + padding * 2;
            int rectHeight = textHeight + padding;
            
            // Draw rounded rectangle background
            g2.setColor(new Color(0, 0, 0, 200)); // semi-transparent black
            g2.fillRoundRect(rectX, rectY, rectWidth, rectHeight, 20, 20);
            
            // Draw the message text
            g2.setColor(Color.ORANGE);
            int textX = gp.screenWidth / 2 - textWidth / 2;
            int textY = rectY + textHeight;
            g2.drawString(message, textX, textY);
            
            // Update counter
            messageCounter++;
            if (messageCounter > 240) { // ~2 seconds at 60 FPS
                messageCounter = 0;
                messageOn = false;
            }
          }

        }
    }

    // LEVEL CLEARED STATE
    if (gp.gameState == gp.levelClearedState) {
        drawLevelClearedMenu(g2);  
    }


    if (gp. gameState == gp.pauseState) {
      drawPauseState();
    }

  }

  public void drawTitleScreen() {

    getIconImage();

    g2.setColor(Color.WHITE);
    g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

    // Title Name
    g2.setFont(fontTitle);
    
    String title = "Pixel Park";
    int x = getXforCenteredText(title);
    int y = gp.tileSize * 3;

    //shadow text
    g2.setColor(Color.RED);
    g2.drawString(title, x + 5, y + 5);

    g2.setColor(Color.ORANGE);
    g2.drawString(title, x, y);

    // ICON PLAYER ORANGE CAT
    x = 30;
    y = 650;
    g2.drawImage(orangeCat, x, y, 500, 500, null);

    // ICON GRAY CAT
    x = 1350;
    y = 650;
    g2.drawImage(grayCat, x, y, 500, 500, null);
    

    // MENU
    String text;
    g2.setFont(pixelFont80B); 
    text = "START";
    x = getXforCenteredText(title);
    y = 550;
    g2.drawString(text, x, y);
    if (commandNum == 0) {
      g2.drawString(">", x - gp.tileSize, y);
    }

    text = "SETTINGS";
    x = getXforCenteredText(title);
    y = 650;
    g2.drawString(text, x, y);
     if (commandNum == 1) {
      g2.drawString(">", x - gp.tileSize, y);
    }

    text = "QUIT";
    x = getXforCenteredText(title);
    y = 750;
    g2.drawString(text, x, y);
     if (commandNum == 2) {
      g2.drawString(">", x - gp.tileSize, y);
    }
  }

  public void drawPauseState() {
    g2.setFont(pixelFont80B);
    g2.setColor(Color.RED);
    String text = "PAUSED";

    int x = getXforCenteredText(text);
    int y = gp.screenHeight / 2 - (gp.tileSize * 4);
    
    g2.drawString(text, x, y);

    g2.setColor(Color.ORANGE);
    g2.setFont(pixelFont);

    text = "RESUME";
    x = getXforCenteredText(text);
    y = 500;
    g2.drawString(text, x, y);
    if (commandNum == 0) {
      g2.drawString("> ", x - gp.tileSize, y);
    }

    text = "RESTART";
    x = getXforCenteredText(text);
    y = 600;
    g2.drawString(text, x, y);
    if (commandNum == 1) {
      g2.drawString("> ", x - gp.tileSize, y);
    }

    text = "MAIN MENU";
    x = getXforCenteredText(text);
    y = 700;
    g2.drawString(text, x, y);
    if (commandNum == 2) {
      g2.drawString("> ", x - gp.tileSize, y);
    }

    text = "QUIT";
    x = getXforCenteredText(text);
    y = 800;
    g2.drawString(text, x, y);
    if (commandNum == 3) {
      g2.drawString("> ", x - gp.tileSize, y);
    }

  }

  public void loadNextLevel() {
    
  }

  public int getXforCenteredText(String text) {
    int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
    int x = gp.screenWidth / 2 - length / 2;
    return x;
  }
}

