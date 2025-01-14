package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; // Describes an image with an accessible buffer of image data. used to store image files.
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int animationIntervalCounter = 0;
    String[] dialogues = new String[20];
    int dialogueIndex = 0;
    public String name;
    public boolean collision = false;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public int typeOfEntity; // 0 = player, 1 = NPC, 2 = monster
    public final int TYPE_PLAYER = 0;
    public final int TYPE_NPCR = 1;
    public final int TYPE_MONSTER = 2;
    public final int TYPE_SWORD = 3;
    public final int TYPE_AXE = 4;
    public final int TYPE_SHIELD = 5;
    public final int TYPE_CONSUMABLE = 6;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public boolean attacking = false;
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public boolean alive = true;
    public boolean dying = false;
    int dyingCounter = 0;
    boolean hpBarOn = false;
    int hpBarCounter = 0;
    public String description = "";
    public int canShootCounter = 0;


    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public int maxMana;
    public int currentMana;
    public Projectile projectile;
    public int manaCost;




    public int attackValue;
    public int defenseValue;



    public boolean isAlive() {
        return alive;
    }

    // CHARACTER STATUS
    public int maxLife;
    public int currentLife;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {}

    public void damageReaction() {

    }

    public void speak() {
        if(dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void update() {
        setAction();
        collisionOn = false;
        gp.collisionChecker.checkTileCollision(this);
        gp.collisionChecker.checkObject(this, false);
        gp.collisionChecker.checkEntity(this, gp.npc);
        gp.collisionChecker.checkEntity(this, gp.monsters);
        boolean contactPlayer = gp.collisionChecker.checkPlayer(this);

        if(this.typeOfEntity == TYPE_MONSTER && contactPlayer) {
            damagePlayer(attack);
        }

        if(!collisionOn){
            switch(direction){
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }
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

        if (invincible) {
            invincibleCounter++;
            if(invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(canShootCounter < 30) {
            canShootCounter++;
        }
    }

    public void damagePlayer(int attack) {
        if(!gp.player.invincible) {
            gp.playSoundEffect(6);

            int damage = attack - gp.player.defense;
            if(damage < 0) {
                damage = 0;
            }
            gp.player.currentLife -= damage;
            gp.player.invincible = true;
        }
    }

    public BufferedImage setup(String imagePath) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream( imagePath + ".png"));

            // CHECK IF WIDTH OR HEIGHT ITS EQUAL 16. IF NOT, IT MEANS IT IS 32
            if(image.getWidth() == gp.originalTileSize){
                if(image.getHeight() == gp.originalTileSize) {
                    image = utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
                }
                else {
                    image = utilityTool.scaleImage(image, gp.tileSize, gp.tileSize*2);
                }
            }
            else {
                image = utilityTool.scaleImage(image, gp.tileSize*2, gp.tileSize);
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void use(Entity entity){}

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if( worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if(spriteNumber == 1) {image = up1;} else {image = up2;}
                    break;
                case "down":
                    if(spriteNumber == 1) {image = down1;} else {image = down2;}
                    break;
                case "left":
                    if(spriteNumber == 1) {image = left1;} else {image = left2;}
                    break;
                case "right":
                    if(spriteNumber == 1) {image = right1;} else {image = right2;}
                    break;
            }

            // monster Hp bar
            if(typeOfEntity == 2 && hpBarOn) {

                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*currentLife;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY-6, gp.tileSize+2, 12);

                g2.setColor(new Color(255,0, 30));
                g2.fillRect(screenX, screenY - 5, (int)hpBarValue, 10);

                hpBarCounter++;

                if(hpBarCounter > 300) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }


            if(invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4f); // set opacity level.
            }
            if(dying) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            changeAlpha(g2, 1f);
        }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        if(dyingCounter <= 5) {
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > 5 && dyingCounter <= 10) {
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > 10 && dyingCounter <= 15) {
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > 15 && dyingCounter <= 20) {
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > 20 && dyingCounter <= 25) {
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > 25 && dyingCounter <= 30) {
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > 30 && dyingCounter <= 35) {
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > 35 && dyingCounter <= 40) {
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > 40) {
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

}
