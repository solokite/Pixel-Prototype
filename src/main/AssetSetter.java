package main;

import object.OBJ_Door;
import object.OBJ_Key;


public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 31 * gp.tileSize;
        gp.obj[0].worldY = 11 * gp.tileSize;
        // initialize pendulum for the key: pivot a bit above the tile, length = 2 tiles
        try {
            object.OBJ_Key key = (object.OBJ_Key) gp.obj[0];
            double pivotX = gp.obj[0].worldX + gp.tileSize / 2.0;
            double pivotY = gp.obj[0].worldY - gp.tileSize; // one tile above
            key.initPendulum(gp, pivotX, pivotY, 15.0, gp.tileSize * 2.0);
        } catch (Exception e) {
            // ignore if cast fails
        }
        
        gp.obj[2] = new OBJ_Door();
        gp.obj[2].worldX = 43 * gp.tileSize;
        gp.obj[2].worldY = 21* gp.tileSize;

    }
}
