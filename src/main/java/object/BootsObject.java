package object;

import entity.Entity;
import main.GamePanel;

public class BootsObject extends Entity {

    public BootsObject(GamePanel gp){
        super(gp);

        name = "Boots";
        down1 = setup("/objects/boots");
    }
}
