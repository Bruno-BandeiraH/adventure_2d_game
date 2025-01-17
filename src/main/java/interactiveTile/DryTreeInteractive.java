package interactiveTile;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class DryTreeInteractive extends  InteractiveTile{

    GamePanel gp;
    public DryTreeInteractive(GamePanel gp,  int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("/interactiveTiles/drytree");
        destructible = true;
        maxLife = 3;
        currentLife = maxLife;
    }

    public boolean isCorrectItem(Entity entity) {
        return entity.currentWeapon.typeOfEntity == TYPE_AXE;
    }

    public InteractiveTile getDestroyedForm() {
        return  new TrunkInteractive(gp, worldX/gp.tileSize, worldY/gp.tileSize);
    }

    public Color getParticleColor() {
        return new Color(65, 50, 30);
    }

    public int getParticleSize() {
        return 6; // pixels
    }

    public int getParticleSpeed() {
        return 1; // how fast can fly
    }

    public int getParticleMaxLife() {
        return 20;
    }
}
