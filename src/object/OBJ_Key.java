package object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Key extends SuperObject{
  private Pendulum pendulum;

  public OBJ_Key() {
    name = "Key";
    try {
      image = ImageIO.read(getClass().getResourceAsStream("/assets/objects/key_ui.png"));
      emptyKeyImage = ImageIO.read(getClass().getResourceAsStream("/assets/objects/emptyKey.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    collision = false;
  }

  // Initialize pendulum after placement. Pass game panel to compute sensible defaults.
  public void initPendulum(GamePanel gp, double pivotWorldX, double pivotWorldY, double startAngleDegrees, double len) {
    this.pendulum = new Pendulum(pivotWorldX, pivotWorldY, len, startAngleDegrees);
    // tune damping/gravity if desired
    this.pendulum.setDamping(1);
    this.pendulum.setGravity(980.0);

    // Set initial world position based on pendulum bob
    double bobX = pendulum.getBobX();
    double bobY = pendulum.getBobY();
    this.worldX = (int) Math.round(bobX) - (image != null ? image.getWidth()/2 : gp.tileSize/2);
    this.worldY = (int) Math.round(bobY) - (image != null ? image.getHeight()/2 : gp.tileSize/2);
  }

  @Override
  public void update() {
    // simple fixed-timestep integration (game runs ~60 FPS)
    if (pendulum == null) return;
    double dt = 1.0 / 60.0;
    pendulum.update(dt);
    double bobX = pendulum.getBobX();
    double bobY = pendulum.getBobY();
    worldX = (int) Math.round(bobX) - (image != null ? image.getWidth()/2 : 0);
    worldY = (int) Math.round(bobY) - (image != null ? image.getHeight()/2 : 0);
  }

  @Override
  public void draw(Graphics2D g2, GamePanel gp) {
    // draw rope
    int screenPivotX = (int) Math.round((pendulum != null ? pendulum.getPivotX() : 0) - gp.cameraX);
    int screenPivotY = (int) Math.round((pendulum != null ? pendulum.getPivotY() : 0) - gp.cameraY);
    int screenKeyX = (int) Math.round(worldX - gp.cameraX + (image != null ? image.getWidth()/2 : gp.tileSize/2));
    int screenKeyY = (int) Math.round(worldY - gp.cameraY + (image != null ? image.getHeight()/2 : gp.tileSize/2));

    g2.setColor(Color.DARK_GRAY);
    g2.setStroke(new BasicStroke(2));
    g2.drawLine(screenPivotX, screenPivotY, screenKeyX, screenKeyY);

    // draw key image (use tileSize for scaling to keep consistent with other objects)
    int drawX = (int) Math.round(worldX - gp.cameraX);
    int drawY = (int) Math.round(worldY - gp.cameraY);
    g2.drawImage(image, drawX, drawY, gp.tileSize, gp.tileSize, null);
  }
}
