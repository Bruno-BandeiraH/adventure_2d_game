package monster;

import entity.Entity;
import main.GamePanel;
import object.BronzeCoinObject;
import object.RockObject;

import java.util.Random;

public class GreenSlime extends Entity {

    GamePanel gp;

    public GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        currentLife = maxLife;
        typeOfEntity = TYPE_MONSTER;
        attack = 5;
        defense = 0;
        exp = 3;
        projectile = new RockObject(gp);

        solidArea.x = 3;
        solidArea.y = 10;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/greenslime_down_1");
        up2 = setup("/monster/greenslime_down_2");
        down1 = setup("/monster/greenslime_down_1");
        down2 = setup("/monster/greenslime_down_2");
        left1 = setup("/monster/greenslime_down_1");
        left2 = setup("/monster/greenslime_down_2");
        right1 = setup("/monster/greenslime_down_1");
        right2 = setup("/monster/greenslime_down_2");
    }

    @Override
    public void setAction() {
        animationIntervalCounter++;

        if(animationIntervalCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1; // 1 to 100
            if(i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if(i > 50 && i <= 75) {
                direction = "left";
            }
            if(i > 75) {
                direction = "right";
            }
            animationIntervalCounter = 0;
        }

        int i = new Random().nextInt(100)+1;
        if(i > 99 && !projectile.alive && canShootCounter == 30) {
            projectile.set(worldX, worldY, direction, true, this);
            gp.projectiles.add(projectile);
            canShootCounter = 0;
        }
    }

    public void damageReaction() {
        animationIntervalCounter = 0;
        direction = gp.player.direction;
    }

    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;

        if(i < 70) {
            dropItem(new BronzeCoinObject(gp));
        }
    }
}
