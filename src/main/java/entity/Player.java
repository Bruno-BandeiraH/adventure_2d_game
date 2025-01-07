package entity;

import main.GamePanel;
import main.KeyHandler;
import object.ShieldObject;
import object.SwordObject;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity{

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCanceled = false;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp); // calling the constructo of the super class
        this.keyH = keyH;
        // these X and Y returns the halfway of the screen. It wont change throught the game
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        // this defines the area in the character's tile with we gonna add the colision logic
        solidArea = new Rectangle(8,16, (gp.tileSize/2),(gp.tileSize/2));

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = 36;
        attackArea.height = 36;


        setDefalutValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefalutValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        // PLAYER STATUS
        maxLife = 6;
        currentLife = maxLife;
        level = 1;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new SwordObject(gp);
        currentShield = new ShieldObject(gp);
        attack = getAttack();
        defense = getDefense();
    }

    public int getAttack() {
        return strength * currentWeapon.attackValue;
    }

    public int getDefense() {
        return dexterity * currentShield.defenseValue;
    }


    public void getPlayerImage(){
            up1 = setup("/player/boy_up_1");
            up2 = setup("/player/boy_up_2");
            down1 = setup("/player/boy_down_1");
            down2 = setup("/player/boy_down_2");
            left1 = setup("/player/boy_left_1");
            left2 = setup("/player/boy_left_2");
            right1 = setup("/player/boy_right_1");
            right2 = setup("/player/boy_right_2");
    }

    public void getPlayerAttackImage() {
        attackUp1 = setup("/player/boy_attack_up_1");
        attackUp2 = setup("/player/boy_attack_up_2");
        attackDown1 = setup("/player/boy_attack_down_1");
        attackDown2 = setup("/player/boy_attack_down_2");
        attackLeft1 = setup("/player/boy_attack_left_1");
        attackLeft2 = setup("/player/boy_attack_left_2");
        attackRight1 = setup("/player/boy_attack_right_1");
        attackRight2 = setup("/player/boy_attack_right_2");
    }

    public void update(){

        if(attacking) {
            attackAnimation();

        } else if(keyH.upPressed || keyH.downPressed || keyH.leftPressed
            || keyH.rightPressed || keyH.enterPressed) {

            if(keyH.upPressed){direction = "up";}
            else if (keyH.downPressed) {direction = "down";}
            else if (keyH.leftPressed) {direction = "left";}
            else if (keyH.rightPressed) { direction = "right";}

            collisionOn = false;
            gp.collisionChecker.checkTileCollision(this);

            int objIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.collisionChecker.checkEntity(this, gp.npc);
            interactNpc(npcIndex);

            // check monster collision
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monsters);
            contactMonster(monsterIndex);

            // CHECK EVENT
            gp.eventHandler.checkEvent();

            if(!collisionOn && !keyH.enterPressed){
                switch(direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            if
            (keyH.enterPressed && !attackCanceled) {
                gp.playSoundEffect(6);
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;

            // changes the image in every 10 frames
            spriteCounter++;
            if(spriteCounter > 14){
                if(spriteNumber == 1){
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        } else {
            standCounter++;
            if(standCounter == 20){
                spriteNumber = 1;
                standCounter = 0;
            }
        }

        if (invincible) {
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void pickUpObject(int index){
        if(index != 222){

        }
    }

    public void interactNpc(int npcIndex) {
        if(keyH.enterPressed){
            if(npcIndex != 222) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[npcIndex].speak();
            }
        }
    }

    public void contactMonster(int monsterIndex) {
        if(monsterIndex != 222) {
            if(!invincible) {
                gp.playSoundEffect(6);
                currentLife--;
                invincible = true;
            }

        }
    }

    public void damageMonster(int monsterIndex) {
        if(monsterIndex != 222) {
            if(!gp.monsters[monsterIndex].invincible) {
                gp.playSoundEffect(5);
                gp.monsters[monsterIndex].currentLife--;
                gp.monsters[monsterIndex].invincible = true;
                gp.monsters[monsterIndex].damageReaction();

                if(gp.monsters[monsterIndex].currentLife <= 0) {
                    gp.monsters[monsterIndex].dying = true;
                }
            }
        }
    }

    public void attackAnimation() {
        spriteCounter++;

        if(spriteCounter <= 5) {
            spriteNumber = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25) {
            spriteNumber = 2;

            // Save current worldX, worldY and solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int currentSolidAreaWidth = solidArea.width;
            int currentSolidAreaHeight = solidArea.height;

            // Adjust player's collision to the weapon (attackArea)
            switch (direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }

            // AttackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // Check monster collision with updated worldX, worldY and solidArea
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monsters);
            damageMonster(monsterIndex);

            // restoring the position and collision values
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.height = currentSolidAreaHeight;
            solidArea.width = currentSolidAreaWidth;
        }
        if(spriteCounter > 25) {
            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        // sprite changer
        switch (direction){
            case "up":
                if(!attacking) {
                if(spriteNumber == 1) {image = up1;} else {image = up2;}
                } else {
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNumber == 1) {image = attackUp1;} else {image = attackUp2;}
                }
                break;
            case "down":
                if (!attacking) {
                    if(spriteNumber == 1) {image = down1;} else {image = down2;}
                } else {
                    if(spriteNumber == 1) {image = attackDown1;} else {image = attackDown2;}
                }
                break;
            case "left":
                if (!attacking) {
                    if(spriteNumber == 1) {image = left1;} else {image = left2;}
                } else {
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNumber == 1) {image = attackLeft1;} else {image = attackLeft2;}
                }
                break;
            case "right":
                if(!attacking) {
                    if(spriteNumber == 1) {image = right1;} else {image = right2;}
                } else {
                    if(spriteNumber == 1) {image = attackRight1;} else {image = attackRight2;}
                }
                break;
        }
        if(invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f)); // set opacity level.
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
