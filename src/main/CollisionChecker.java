package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        // Vertical collision: handles standing on blocks or hitting ceilings
        double leftX = entity.worldX + entity.solidArea.x;
        double rightX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        double topY = entity.worldY + entity.solidArea.y;
        double bottomY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int leftCol = (int)(leftX / gp.finalSize);
        int rightCol = (int)(rightX / gp.finalSize);

        // Check DOWN collision
        double futureBottomY = bottomY + entity.yVelocity;
        int bottomRow = (int)(futureBottomY / gp.finalSize);
        boolean landed = false;

        for (int col = leftCol; col <= rightCol; col++) {
            int tileNum = gp.tileM.mapTileNum[col][bottomRow];
            if (gp.tileM.tile[tileNum].collision) {
                entity.worldY = bottomRow * gp.finalSize - entity.solidArea.height - entity.solidArea.y;
                entity.yVelocity = 0;
                entity.jumping = false;
                landed = true;
                break;
            }
        }

        if (!landed) {
            entity.jumping = true;
        }

        // Check UP collision
        double futureTopY = topY + entity.yVelocity;
        int topRow = (int)(futureTopY / gp.finalSize);
        for (int col = leftCol; col <= rightCol; col++) {
            int tileNum = gp.tileM.mapTileNum[col][topRow];
            if (gp.tileM.tile[tileNum].collision) {
                entity.yVelocity = 0;
                break;
            }
        }
    }

    public int checkObject(Entity entity, boolean player) {
        
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {

                // get entity solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // get object solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }
}
