package main;

import object.BootsObject;
import object.ChestObject;
import object.DoorObject;
import object.KeyObject;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

        gp.objectSlots[0] = new KeyObject(gp);
        gp.objectSlots[0].worldX = 23 * gp.tileSize;
        gp.objectSlots[0].worldY = 7 * gp.tileSize;

        gp.objectSlots[1] = new KeyObject(gp);
        gp.objectSlots[1].worldX = 23 * gp.tileSize;
        gp.objectSlots[1].worldY = 40 * gp.tileSize;

        gp.objectSlots[2] = new KeyObject(gp);
        gp.objectSlots[2].worldX = 37 * gp.tileSize;
        gp.objectSlots[2].worldY = 7 * gp.tileSize;

        gp.objectSlots[3] = new DoorObject(gp);
        gp.objectSlots[3].worldX = 10 * gp.tileSize;
        gp.objectSlots[3].worldY = 11 * gp.tileSize;

        gp.objectSlots[4] = new DoorObject(gp);
        gp.objectSlots[4].worldX = 8 * gp.tileSize;
        gp.objectSlots[4].worldY = 28 * gp.tileSize;

        gp.objectSlots[5] = new DoorObject(gp);
        gp.objectSlots[5].worldX = 12 * gp.tileSize;
        gp.objectSlots[5].worldY = 22 * gp.tileSize;

        gp.objectSlots[6] = new ChestObject(gp);
        gp.objectSlots[6].worldX = 10 * gp.tileSize;
        gp.objectSlots[6].worldY = 7 * gp.tileSize;

        gp.objectSlots[7] = new BootsObject(gp);
        gp.objectSlots[7].worldX = 37 * gp.tileSize;
        gp.objectSlots[7].worldY = 42 * gp.tileSize;
    }
}
