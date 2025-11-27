package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        // Get solid area boundaries
        double leftX = entity.worldX + entity.solidArea.x;
        double rightX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        double topY = entity.worldY + entity.solidArea.y;
        double bottomY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int leftCol = (int)(leftX / gp.tileSize);
        int rightCol = (int)(rightX / gp.tileSize);
        

        // Check DOWN collision
        double futureBottomY = bottomY + entity.yVelocity;
        int futureBottomRow = (int)(futureBottomY / gp.tileSize);
        boolean landed = false;

        for (int col = leftCol; col <= rightCol; col++) {
            if (col >= 0 && col < gp.maxWorldCol && futureBottomRow >= 0 && futureBottomRow < gp.maxWorldRow) {
                int tileNum = gp.tileM.mapTileNum[col][futureBottomRow];
                if (tileNum >= 0 && tileNum < gp.tileM.tile.length && gp.tileM.tile[tileNum] != null && gp.tileM.tile[tileNum].collision) {
                    entity.worldY = futureBottomRow * gp.tileSize - entity.solidArea.height - entity.solidArea.y;
                    entity.yVelocity = 0;
                    entity.jumping = false;
                    landed = true;
                    break;
                }
            }
        }

        if (!landed) {
            entity.jumping = true;
        }

        // Check UP collision
        double futureTopY = topY + entity.yVelocity;
        int futureTopRow = (int)(futureTopY / gp.tileSize);
        for (int col = leftCol; col <= rightCol; col++) {
            if (col >= 0 && col < gp.maxWorldCol && futureTopRow >= 0 && futureTopRow < gp.maxWorldRow) {
                int tileNum = gp.tileM.mapTileNum[col][futureTopRow];
                if (tileNum >= 0 && tileNum < gp.tileM.tile.length && gp.tileM.tile[tileNum] != null && gp.tileM.tile[tileNum].collision) {
                    entity.yVelocity = 0;
                    break;
                }
            }
        }
    }

    public void checkTileHorizontal(Entity entity, String direction) {
        // Check map boundaries to prevent player from leaving the world
        if (direction.equals("left")) {
            double futureLeftX = entity.worldX + entity.solidArea.x - entity.speed;
            if (futureLeftX < 0) {
                entity.collisionOn = true;
            } else {
                entity.collisionOn = false;
            }
        } else if (direction.equals("right")) {
            double futureRightX = entity.worldX + entity.solidArea.x + entity.solidArea.width + entity.speed;
            if (futureRightX > gp.worldWidth) {
                entity.collisionOn = true;
            } else {
                entity.collisionOn = false;
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