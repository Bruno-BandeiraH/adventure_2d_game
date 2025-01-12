package main;

import entity.OldManNPC;
import monster.GreenSlime;
import object.AxeObject;
import object.BlueShieldObject;
import object.KeyObject;
import object.PotionObject;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject() {
        gp.objectSlots[0] = new KeyObject(gp);
        gp.objectSlots[0].worldX = gp.tileSize*25;
        gp.objectSlots[0].worldY = gp.tileSize*23;

        gp.objectSlots[1] = new KeyObject(gp);
        gp.objectSlots[1].worldX = gp.tileSize*21;
        gp.objectSlots[1].worldY = gp.tileSize*19;

        gp.objectSlots[2] = new KeyObject(gp);
        gp.objectSlots[2].worldX = gp.tileSize*26;
        gp.objectSlots[2].worldY = gp.tileSize*21;

        gp.objectSlots[3] = new AxeObject(gp);
        gp.objectSlots[3].worldX = gp.tileSize*33;
        gp.objectSlots[3].worldY = gp.tileSize*21;

        gp.objectSlots[4] = new BlueShieldObject(gp);
        gp.objectSlots[4].worldX = gp.tileSize*36;
        gp.objectSlots[4].worldY = gp.tileSize*21;

        gp.objectSlots[5] = new PotionObject(gp);
        gp.objectSlots[5].worldX = gp.tileSize*22;
        gp.objectSlots[5].worldY = gp.tileSize*27;

    }

    public void setNpc() {
        gp.npc[0] = new OldManNPC(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;

    }

    public void setMonsters() {
        gp.monsters[0] = new GreenSlime(gp);
        gp.monsters[0].worldX = gp.tileSize*23;
        gp.monsters[0].worldY = gp.tileSize*36;

        gp.monsters[1] = new GreenSlime(gp);
        gp.monsters[1].worldX = gp.tileSize*23;
        gp.monsters[1].worldY = gp.tileSize*38;
    }
}
