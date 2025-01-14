package entity;

import main.GamePanel;

public class Projectile extends Entity {

    Entity user;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.currentLife = this.maxLife;
    }

    public void update() {

        if(user == gp.player) {
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monsters);
            if(monsterIndex != 222) {
                gp.player.damageMonster(monsterIndex, attack);
                alive = false; // if hit a monster, the projectile disappears
            }
        }
        if(user != gp.player) {
            boolean contactPlayer = gp.collisionChecker.checkPlayer(this);
            if(!gp.player.invincible && contactPlayer) {
                damagePlayer(attack);
                alive = false;
            }
        }
        switch (direction) {
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }

        currentLife--;
        if(currentLife <= 0) {
            alive = false;
        }

        spriteCounter++;
        if(spriteCounter > 14){
            if(spriteNumber == 1){
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }

    public boolean haveManaToCastFireBall(Entity user) {
        boolean haveResource = false;
        return haveResource;
    }

    public void subtractResource(Entity user) {

    }
}

