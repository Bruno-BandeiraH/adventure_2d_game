package object;

import entity.Projectile;
import main.GamePanel;

public class RockObject extends Projectile {
    GamePanel gp;

    public RockObject(GamePanel gp) {
        super(gp);

        this.gp = gp;
        name = "Rock";
        speed = 8;
        maxLife = 80;
        currentLife = maxLife;
        attack = 2;
        manaCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/fireball/rock_down_1");
        up2 = setup("/fireball/rock_down_1");
        down1 = setup("/fireball/rock_down_1");
        down2 = setup("/fireball/rock_down_1");
        left1 = setup("/fireball/rock_down_1");
        left2 = setup("/fireball/rock_down_1");
        right1 = setup("/fireball/rock_down_1");
        right2 = setup("/fireball/rock_down_1");
    }
}
