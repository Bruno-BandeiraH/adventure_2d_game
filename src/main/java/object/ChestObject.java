package object;

import entity.Entity;
import main.GamePanel;

public class ChestObject extends Entity {

    public ChestObject(GamePanel gp){
        super(gp);

        name = "Chest";
        down1 = setup("/objects/chest");
    }
}
