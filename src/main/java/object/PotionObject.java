package object;

import entity.Entity;
import main.GamePanel;

public class PotionObject extends Entity {

    int healingValue = 5;
    GamePanel gp;

    public PotionObject(GamePanel gp) {
        super(gp);
        this.gp = gp;

        typeOfEntity = TYPE_CONSUMABLE;
        name = "Life Potion";
        down1 = setup("/objects/potion_red");
        description = "[Life Potion]\nHeals your wounds.\nCures up to" + healingValue + "points";
    }

    public void use(Entity entity) {
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + ".\n" +
                                "Your life has been recovered by " + healingValue + ".";
        entity.currentLife += healingValue;
        if(gp.player.currentLife > gp.player.maxLife) {
            gp.player.currentLife = gp.player.maxLife;
        }
        gp.playSoundEffect(2);
    }
}
