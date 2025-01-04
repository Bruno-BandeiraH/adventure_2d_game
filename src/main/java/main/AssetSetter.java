package main;

import entity.Entity;
import entity.OldManNPC;
import monster.GreenSlime;
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

        gp.npc[0] = new OldManNPC(gp);
        gp.npc[0].worldX = gp.tileSize*9;
        gp.npc[0].worldY = gp.tileSize*10;
    }

    public void setMonsters() {
        gp.monsters[0] = new GreenSlime(gp);
        gp.monsters[0].worldX = gp.tileSize*23;
        gp.monsters[0].worldY = gp.tileSize*36;

        gp.monsters[1] = new GreenSlime(gp);
        gp.monsters[1].worldX = gp.tileSize*23;
        gp.monsters[1].worldY = gp.tileSize*38;

//        gp.monsters[0] = new GreenSlime(gp);
//        gp.monsters[0].worldX = gp.tileSize*11;
//        gp.monsters[0].worldY = gp.tileSize*10;
//
//        gp.monsters[1] = new GreenSlime(gp);
//        gp.monsters[1].worldX = gp.tileSize*11;
//        gp.monsters[1].worldY = gp.tileSize*11;


    }
}
