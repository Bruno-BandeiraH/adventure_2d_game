package object;

import entity.Entity;
import main.GamePanel;

public class SwordObject extends Entity {

    public SwordObject(GamePanel gp) {
        super(gp);
        name = "Regular Sword";
        down1 = setup("/objects/sword_normal");
        attackValue = 1;
        description = "[" + name + "]\n An commom sword";

    }
}
