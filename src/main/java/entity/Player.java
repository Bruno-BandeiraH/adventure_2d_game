package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;

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

        setDefalutValues();
        getPlayerImage();
    }

    public void setDefalutValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
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

    public void update(){

        // this if prevents the player from move if he stay still
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
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

            if(collisionOn == false){
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
        } else {
            standCounter++;
            if(standCounter == 20){
                spriteNumber = 1;
                standCounter = 0;
            }
        }
    }

    public void pickUpObject(int index){
        if(index != 222){

        }
    }

    public void interactNpc(int npcIndex) {
        if(npcIndex != 222) {
            if(keyH.enterPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[npcIndex].speak();
            }
        }
        gp.keyH.enterPressed = false;
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        // sprite changer
        switch (direction){
            case "up":
                if(spriteNumber == 1){
                    image = up1;
                } else{
                    image = up2;
                }
                break;
            case "down":
                if(spriteNumber == 1){
                    image = down1;
                } else{
                    image = down2;
                }
                break;
            case "left":
                if(spriteNumber == 1){
                    image = left1;
                } else{
                    image = left2;
                }
                break;
            case "right":
                if(spriteNumber == 1){
                    image = right1;
                } else{
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }
}
