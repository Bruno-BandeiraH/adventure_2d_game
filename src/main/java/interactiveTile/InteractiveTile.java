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

    }

    public boolean isCorrectItem(Entity entity) {
        return false;
    }
}
