package object;

import entity.Entity;
import main.GamePanel;

public class AxeObject extends Entity {

    public AxeObject(GamePanel gp) {
        super(gp);
        name = "Woodcutter's Axe";
        down1 = setup("/objects/axe");
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        typeOfEntity = TYPE_AXE;
        description = "[" + name + "]" + "\nA bit rusty but still can\n cut some trees.";
    }
}
