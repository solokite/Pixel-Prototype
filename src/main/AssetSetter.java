package main;

import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Normal_Key;


public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        // deprecated: use setObjectsForLevel(levelNumber) instead
        setObjectsForLevel(1);
    }

    public void setObjectsForLevel(int levelNumber) {
        switch (levelNumber) {
            case 1:
            case 2: // default level 1/2 setup
                gp.obj[0] = new OBJ_Key();
                gp.obj[0].worldX = 31 * gp.tileSize;
                gp.obj[0].worldY = 8 * gp.tileSize;
                try {
                    object.OBJ_Key key = (object.OBJ_Key) gp.obj[0];
                    double pivotX = gp.obj[0].worldX + gp.tileSize / 2.0;
                    double pivotY = 0;
                    double length = (gp.obj[0].worldY + gp.tileSize / 2.0) - pivotY;
                    if (length < 1) length = gp.tileSize * 2.0;
                    key.initPendulum(gp, pivotX, pivotY, 15.0, length);
                } catch (Exception e) {
                    // ignore if cast fails
                }
                
                gp.obj[2] = new OBJ_Door();
                gp.obj[2].worldX = 43 * gp.tileSize;
                gp.obj[2].worldY = 21 * gp.tileSize;

                gp.obj[3] = new OBJ_Key();
                gp.obj[3].worldX = 37 * gp.tileSize;
                gp.obj[3].worldY = 7 * gp.tileSize;
                try {
                    object.OBJ_Key key = (object.OBJ_Key) gp.obj[3];
                    double pivotX = gp.obj[3].worldX + gp.tileSize / 2.0;
                    double pivotY = 0;
                    double length = (gp.obj[3].worldY + gp.tileSize / 2.0) - pivotY;
                    if (length < 1) length = gp.tileSize * 2.0;
                    key.initPendulum(gp, pivotX, pivotY, 15.0, length);
                } catch (Exception e) {
                    // ignore if cast fails
                }

                gp.obj[4] = new OBJ_Normal_Key();
                gp.obj[4].worldX = 24 * gp.tileSize;
                gp.obj[4].worldY = 21 * gp.tileSize;
                break;
            
            case 3:
                // Example: different object placement for level 3
                gp.obj[0] = new OBJ_Door();
                gp.obj[0].worldX = 20 * gp.tileSize;
                gp.obj[0].worldY = 15 * gp.tileSize;
                break;
            
            default:
                // fallback: minimal object setup
                break;
        }
    }
}
