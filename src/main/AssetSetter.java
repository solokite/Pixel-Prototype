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
        gp.obj[0].worldX = 30 * gp.finalSize;
        gp.obj[0].worldY = 20 * gp.finalSize;
        
        gp.obj[2] = new OBJ_Door();
        gp.obj[2].worldX = 30 * gp.finalSize;
        gp.obj[2].worldY = 17 * gp.finalSize;

    }
}
