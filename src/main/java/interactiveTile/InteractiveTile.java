package interactiveTile;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {

    GamePanel gp;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }

    public void update() {
        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > 30) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public boolean isCorrectItem(Entity entity) {
        return false;
    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;
        return tile;
    }
}
