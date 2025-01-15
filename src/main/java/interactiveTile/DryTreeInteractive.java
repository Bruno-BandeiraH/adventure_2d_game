package interactiveTile;

import entity.Entity;
import main.GamePanel;

public class DryTreeInteractive extends  InteractiveTile{

    GamePanel gp;
    public DryTreeInteractive(GamePanel gp,  int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("/interactiveTiles/drytree");
        destructible = true;
    }

    public boolean isCorrectItem(Entity entity) {
        return entity.currentWeapon.typeOfEntity == TYPE_AXE;
    }
}
