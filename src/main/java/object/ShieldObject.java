package object;

import entity.Entity;
import main.GamePanel;

public class ShieldObject extends Entity {

    public ShieldObject(GamePanel gp) {
        super(gp);

        name = "Wood Shield";
        down1 = setup("/objects/shield_wood");
        defenseValue = 1;
    }
}
