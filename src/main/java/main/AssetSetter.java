package main;

import entity.OldManNPC;
import object.DoorObject;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject() {

    }

    public void setNpc() {
        gp.npc[0] = new OldManNPC(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;
    }
}
