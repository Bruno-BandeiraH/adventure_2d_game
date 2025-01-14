package object;

import entity.Entity;
import main.GamePanel;

public class ManaCrystalObject extends Entity {

    GamePanel gp;

    public ManaCrystalObject(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Mana Crystal";
        down1 = setup("/objects/manacrystal_full");
        down2 = setup("/objects/manacrystal_blank");

    }
}
