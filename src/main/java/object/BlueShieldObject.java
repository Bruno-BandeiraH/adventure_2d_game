package object;

import entity.Entity;
import main.GamePanel;

public class BlueShieldObject extends Entity {

    public BlueShieldObject(GamePanel gp) {
        super(gp);

        typeOfEntity = TYPE_SHIELD;
        name = "Blue Shield";
        down1 = setup("/objects/shield_blue");
        defenseValue = 2;
        description = "[" + name + "]\nA shiny blue shield.";
    }
}
