package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Normal_Key extends SuperObject{
  public OBJ_Normal_Key() {
    name = "Key";
    try {
      image = ImageIO.read(getClass().getResourceAsStream("/assets/objects/key_ui.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    collision = false;
  }
}
