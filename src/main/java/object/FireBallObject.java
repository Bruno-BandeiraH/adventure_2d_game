package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class FireBallObject extends Projectile {

    GamePanel gp;

    public FireBallObject(GamePanel gp) {
        super(gp);

        this.gp = gp;
        name = "FireBall";
        speed = 5;
        maxLife = 80;
        currentLife = maxLife;
        attack = 2;
        manaCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/fireball/fireball_up_1");
        up2 = setup("/fireball/fireball_up_2");
        down1 = setup("/fireball/fireball_down_1");
        down2 = setup("/fireball/fireball_down_2");
        left1 = setup("/fireball/fireball_left_1");
        left2 = setup("/fireball/fireball_left_2");
        right1 = setup("/fireball/fireball_right_1");
        right2 = setup("/fireball/fireball_right_2");
    }

    public boolean haveManaToCastFireBall(Entity user) {

        boolean haveResource = false;
        if(user.currentMana >= manaCost) {
            haveResource = true;
        }
        return haveResource;
    }

    public void subtractResource(Entity user) {
        user.currentMana -= manaCost;
    }
}
