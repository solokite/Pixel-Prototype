package object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Key extends SuperObject{

  // Pendulum state
  private double pivotX;
  private double pivotY;
  private double length = 0; // pixels
  private double angle = Math.toRadians(20); // initial angle
  private double angularVelocity = 0;
  private double angularAcceleration = 0;
  private double damping = 0.995; // damping factor
  private double gravity = 980; // pixels / s^2 (tweak for game feel)

  public OBJ_Key() {
    name = "Key";
    try {
      image = ImageIO.read(getClass().getResourceAsStream("/assets/objects/key_ui.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    collision = false;
  }

  // Initialize pendulum after placement. Pass game panel to compute sensible defaults.
  public void initPendulum(GamePanel gp, double pivotWorldX, double pivotWorldY, double startAngleDegrees, double len) {
    this.pivotX = pivotWorldX;
    this.pivotY = pivotWorldY;
    this.angle = Math.toRadians(startAngleDegrees);
    this.length = len;

    // Set initial bob position
    this.worldX = (int) Math.round(pivotX + length * Math.sin(angle)) - gp.tileSize/2;
    this.worldY = (int) Math.round(pivotY + length * Math.cos(angle)) - gp.tileSize/2;
  }

  @Override
  public void update() {
    // simple fixed-timestep integration (game runs ~60 FPS)
    double dt = 1.0 / 60.0;

    // angular acceleration for simple pendulum: -(g / L) * sin(theta)
    angularAcceleration = - (gravity / length) * Math.sin(angle);
    angularVelocity += angularAcceleration * dt;
    angularVelocity *= damping;
    angle += angularVelocity * dt;

    // update world position (center the image)
    double bobX = pivotX + length * Math.sin(angle);
    double bobY = pivotY + length * Math.cos(angle);
    worldX = (int) Math.round(bobX) - (image != null ? image.getWidth()/2 : 0);
    worldY = (int) Math.round(bobY) - (image != null ? image.getHeight()/2 : 0);
  }

  @Override
  public void draw(Graphics2D g2, GamePanel gp) {
    // draw rope
    int screenPivotX = (int) Math.round(pivotX - gp.cameraX);
    int screenPivotY = (int) Math.round(pivotY - gp.cameraY);
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
