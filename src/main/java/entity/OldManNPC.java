package entity;

import main.GamePanel;

import java.util.Random;

public class OldManNPC extends Entity{

    public OldManNPC(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
        getOldManImage();
    }

    public void getOldManImage(){
        up1 = setup("/npc/oldman_up_1");
        up2 = setup("/npc/oldman_up_2");
        down1 = setup("/npc/oldman_down_1");
        down2 = setup("/npc/oldman_down_2");
        left1 = setup("/npc/oldman_left_1");
        left2 = setup("/npc/oldman_left_2");
        right1 = setup("/npc/oldman_right_1");
        right2 = setup("/npc/oldman_right_2");
    }

    public void setAction() {

        animationIntervalCounter++;

        if(animationIntervalCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1; // 1 to 100
            if(i <= 25) {
                direction = "up";
            } else if (i > 25 && i <= 50) {
                direction = "down";
            } else if(i > 50 && i < 75) {
                direction = "left";
            } else if(i > 75) {
                direction = "right";
            }
            animationIntervalCounter = 0;
        }

    }
}
