package object;

import entity.Entity;
import main.GamePanel;

public class BronzeCoinObject extends Entity {

    GamePanel gp;
    int value = 1;

    public BronzeCoinObject(GamePanel gp) {
        super(gp);

        this.gp = gp;

        typeOfEntity = TYPE_PICKUP_ONLY;
        down1 = setup("/objects/coin_bronze");
    }

    public void use(Entity entity) {
        gp.ui.addMessage(value + " coin.");
        gp.player.coin += value;
    }
}
